package de.fun2code.android.pawruntime;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;
import de.fun2code.android.pawserver.PawServerActivity;
import de.fun2code.android.pawserver.PawServerService;

/**
 * Android PAW Server Runtime Main Activity
 * 
 * Fun2Code.de
 * @author joschi
 * 
 */
public class PawRuntimeActivity extends PawServerActivity {
	
	/*
	 * Configuration parameters
	 */
	private static final String NAME = "PawRuntime";
	private final String INSTALL_DIR = "/sdcard/paw-runtime";
	private static String SCREENSHOT_OUTPUT_DIR = "/sdcard/";
	private static String SCREENSHOT_OUTPUT_NAME = "screenshot";

	
	
	private WebView webview;
	private static final int MENU_HOME = Menu.FIRST + 1;
	private static final int MENU_SCREENSHOT = Menu.FIRST + 2;
	private String serverPort = null;
	private String menuURL = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		PawServerActivity.calledFromRuntime = true;

		super.onCreate(savedInstanceState);
		
		// If not a tablet hide title
		if (!isTablet() || Integer.parseInt(android.os.Build.VERSION.SDK) < 11) {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		}
		
		setContentView(R.layout.main);

		// Set volume control to music stream
		setVolumeControlStream(AudioManager.STREAM_MUSIC);

		checkInstallation();

		// Register handler
		messageHandler = new MessageHandler();

		PawServerService.setActivityHandler(messageHandler);
		PawServerService.setActivity(this);

		if (PawServerService.isRunning() && serverPort == null) {
			serverPort = PawServerService.getServerPort();
		}

		if (serverPort == null) {
			startService();
		} else {
			initWebView();
		}
	}

	/**
	 * Restart Activity
	 */
	public void restart() {
		Log.d(NAME, "Restarting...");

		Intent intent = new android.content.Intent();
		intent.setClass(this, this.getClass());
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startActivity(intent);
		finish();
	}

	@Override
	protected void onDestroy() {
		stopService();
		serverPort = null;

		if (webview != null) {
			webview.clearCache(true);
			webview.clearHistory();
			webview.destroy();
		}

		super.onDestroy();
	}

	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem menuHelp = menu.add(0, MENU_HOME, 0, R.string.menu_home);
		menuHelp.setIcon(android.R.drawable.ic_menu_view);
		menuHelp.setAlphabeticShortcut('h');

		MenuItem menuScreenshot = menu.add(0, MENU_SCREENSHOT, 0,
				R.string.menu_screenshot);
		menuScreenshot.setIcon(android.R.drawable.ic_menu_camera);
		menuScreenshot.setAlphabeticShortcut('s');

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case MENU_HOME:
				if (webview != null) {
					webview.loadUrl(menuURL);
					webview.clearHistory();
				} else {
					// The service might have crashed, restart service
					startService();
				}
				break;
			case MENU_SCREENSHOT:
				saveScreenshot();
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview != null
				&& webview.canGoBack()) {
			String url = webview.getUrl();
			if (!url.equals(menuURL)) {
				webview.goBack();
				return true;
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	public void startService() {
		
		new Thread() {
			public void run() {
				Intent serviceIntent = new Intent(PawRuntimeActivity.this,
						PawRuntimeService.class);
				serviceIntent.putExtra("logTag", getText(R.string.app_name)
						.toString());
				serviceIntent.putExtra("isRuntime", true);
				serviceIntent.putExtra("serverConfig", INSTALL_DIR
						+ "/conf/server.xml");
				serviceIntent.putExtra("pawHome", INSTALL_DIR + "/");

				PawRuntimeService.setUpLatch();
				startService(serviceIntent);

				if (PawRuntimeService.waitForService(10L)) {
					serverPort = PawRuntimeService.getServerPort();
					menuURL = "http://localhost:" + serverPort + "/";
					initWebView();
				} else {
					serverPort = null;
					// Maybe not a good idea, might result in endless loop :(
					restart();
				}
			}
		}.start();
	}

	public void stopService() {
		Intent serviceIntent = new Intent(this.getApplicationContext(),
				PawRuntimeService.class);
		Log.d(NAME, "Stop service");
		if (stopService(serviceIntent)) {
			Log.d(NAME, "Service Stopped!");
		}
	}

	private void initWebView() {
		// WebView webview = new WebView(this);
		// setContentView(webview);
		webview = (WebView) findViewById(R.id.webview);

		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new PreWebViewClient());

		webview.setFocusable(true);
		webview.setFocusableInTouchMode(true);

		final Context ctx = this;
		/* Handle JavaScript alert() */
		webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					final android.webkit.JsResult result) {
				new AlertDialog.Builder(ctx).setTitle(
						getText(R.string.javascript_dialog_title)).setMessage(
						message).setPositiveButton(android.R.string.ok,
						new AlertDialog.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.confirm();
							}
						}).setCancelable(false).create().show();

				return true;
			};

			@Override
			public boolean onJsConfirm(WebView view, String url,
					String message, final android.webkit.JsResult result) {
				new AlertDialog.Builder(ctx).setTitle(
						getText(R.string.javascript_dialog_title)).setMessage(
						message).setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.confirm();
							}
						}).setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								result.cancel();
							}
						}).create().show();

				return true;
			};

			@Override
			public boolean onJsPrompt(WebView view, String url, String message,
					String defaultValue,
					final android.webkit.JsPromptResult result) {
				final EditText editText = new EditText(ctx);

				new AlertDialog.Builder(ctx).setTitle(
						getText(R.string.javascript_dialog_title)).setView(
						editText).setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								String value = editText.getText().toString();
								result.confirm(value);
							}
						}).setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								result.cancel();
							}
						}).setOnCancelListener(
						new DialogInterface.OnCancelListener() {
							public void onCancel(DialogInterface dialog) {
								result.cancel();
							}
						}).show();

				return true;
			};

		});

		webview.loadUrl(menuURL);
	}

	/**
	 * Extracts a ZIP file
	 * 
	 * @param zipIs		ZIP Input Stream
	 * @param dest		Destination directory
	 * @param keepFiles	Files to not overwrite
	 */
	public static void extractZip(InputStream zipIs, String dest,
			HashMap<String, Integer> keepFiles) {
		try {
			if (!dest.endsWith("/")) {
				dest += "/";
			}

			byte[] buf = new byte[1024];
			ZipInputStream zipinputstream = null;
			ZipEntry zipentry;
			zipinputstream = new ZipInputStream(zipIs);

			zipentry = zipinputstream.getNextEntry();
			while (zipentry != null) {
				// for each entry to be extracted
				String entryName = dest + zipentry.getName();
				entryName = entryName.replace('/', File.separatorChar);
				entryName = entryName.replace('\\', File.separatorChar);

				int n;
				FileOutputStream fileoutputstream;
				File newFile = new File(entryName);

				// Check if file should not be overwritten
				if (keepFiles.get(zipentry.getName()) != null
						&& newFile.exists()) {
					Log.d(NAME, "File not overwritten: " + zipentry.getName());
					zipentry = zipinputstream.getNextEntry();
					continue;
				}

				if (zipentry.isDirectory()) {
					newFile.mkdirs();
					zipentry = zipinputstream.getNextEntry();
					continue;
				}

				// Create parent directories, if they do not exist
				if (!new File(newFile.getParent()).exists()) {
					new File(newFile.getParent()).mkdirs();
				}

				fileoutputstream = new FileOutputStream(entryName);

				while ((n = zipinputstream.read(buf, 0, 1024)) > -1) {
					fileoutputstream.write(buf, 0, n);
				}

				fileoutputstream.close();
				zipinputstream.closeEntry();
				zipentry = zipinputstream.getNextEntry();

			}// while

			zipinputstream.close();
		} catch (Exception e) {
			Log.e(NAME, e.getMessage());
		}
	}

	/**
	 * Deletes a directory
	 * 
	 * @param path Directory to delete
	 * @return
	 */
	private boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

	/**
	 * Checks the installation and unzips the content on first start
	 */
	private void checkInstallation() {
		if (!new File(INSTALL_DIR).exists()) {
			Log.d(NAME, "Extracting files...");
			// textUrl.setText(R.string.extracting);
			final ProgressDialog pd = ProgressDialog.show(this,
					getText(R.string.dialog_info),
					getText(R.string.extracting), true, false);

			// new Thread() {
			// public void run() {
			try {
				// Create directories
				new File(INSTALL_DIR).mkdirs();

				// Extract ZIP file
				HashMap<String, Integer> keepFiles = new HashMap<String, Integer>();
				keepFiles.put("conf/server.xml", 0);
				keepFiles.put("conf/handler.xml", 0);

				extractZip(this.getClass().getResourceAsStream(
						"/de/fun2code/android/pawruntime/appContent.zip"),
						INSTALL_DIR, keepFiles);
			} catch (Exception e) {
				Log.e(NAME, e.getMessage());
			} finally {
				pd.dismiss();
			}
			// }
			// }.start();

		}

	}

	/**
	 * Saves a screenshot
	 */
	public void saveScreenshot() {
		try {
			// Search free file

			String filename = null;
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				String checkfile = SCREENSHOT_OUTPUT_DIR
						+ SCREENSHOT_OUTPUT_NAME + "_" + i + ".png";
				if (!new File(checkfile).exists()) {
					filename = checkfile;
					break;
				}
			}

			if (filename != null) {
				webview.buildDrawingCache();
				Bitmap bitmap = webview.getDrawingCache();

				FileOutputStream fos = new FileOutputStream(new File(filename));
				bitmap.compress(CompressFormat.PNG, 0 /* ignored for PNG */, fos);
				fos.close();

				Toast.makeText(getApplicationContext(), R.string.screenshot_ok,
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						R.string.screenshot_error, Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), R.string.screenshot_error,
					Toast.LENGTH_SHORT).show();
		}

	}
	
	private boolean isTablet() {
		final int SCREENLAYOUT_SIZE_XLARGE = 4;
		
		return (getResources().getConfiguration().screenLayout & SCREENLAYOUT_SIZE_XLARGE) == SCREENLAYOUT_SIZE_XLARGE;
	}

}

/**
 * WebView Client class
 *
 */
class PreWebViewClient extends WebViewClient {
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return true;
	}

}