<?php
//ENGLISH LANGUAGE FILE, STRINGS SHOULD BE LOCATED IN $QRMANAGER_STRINGS["nameOfString"] = "STRING";

		$QRMANAGER_STRINGS = array();

		/* GLOBAL STRINGS */
		$QRMANAGER_STRINGS["Children"] = 'Children';
		$QRMANAGER_STRINGS["Guardians"] = 'Guardians';
		$QRMANAGER_STRINGS["Parents"] = 'Parents';

		/*MAIN PAGE */
		$QRMANAGER_STRINGS["headerTitle"] = "QR Manager &middot; GIRAF";
		$QRMANAGER_STRINGS["breadCrumpMain"] = "QR Manager";
		$QRMANAGER_STRINGS["mainButtonEditQr"] = "Edit QR for user";
		$QRMANAGER_STRINGS["mainButtonChooseQr"] = "Choose QR to print";
		$QRMANAGER_STRINGS["mainButtonPrintAllQr"] = "Print all QR";

		/* EDIT QR */
		$QRMANAGER_STRINGS["breadCrumpEdit"] = '<a href="index.php#qrManager/action=main">QR Manager</a> &rarr; Edit QR';
		$QRMANAGER_STRINGS["editQrButtonEdit"] = 'Edit';

		/* EDIT SUBMIT */
		$QRMANAGER_STRINGS["breadCrumpEditSubmit"] = '<a href="index.php#qrManager/action=main">QR Manager</a> &rarr; <a href="index.php#qrManager/action=edit">Edit QR</a> &rarr; Result';
		$QRMANAGER_STRINGS["editSubmitLeadInfoMessage"] = '%N has gotten a new QR code, a mail has been sent to %N about this'; //%N needs to be replaced with the appropriate name

		/* CHOOSE QR */
		$QRMANAGER_STRINGS["breadCrumpChoosePrint"] = '<a href="index.php#qrManager/action=main">QR Manager</a> &rarr; Choose QR to print';
		$QRMANAGER_STRINGS["choosePrintLeadInfoMessage"] = 'Choose QR codes to print';
		$QRMANAGER_STRINGS["choosePrintSubmitText"] = 'Confirm';

		/* CHOOSE QR SUBMIT */
		$QRMANAGER_STRINGS["breadCrumpChoosePrintSubmit"] = '<a href="index.php#qrManager/action=main">QR Manager</a> &rarr; <a href="index.php#qrManager/action=choosePrint">Choose QR to print</a> &rarr; Result';
		$QRMANAGER_STRINGS["choosePrintSubmitLeadInfoMessage"] = ' users have been marked to print';
		$QRMANAGER_STRINGS["choosePrintSubmitButtonSubmit"] = 'Print';
?>