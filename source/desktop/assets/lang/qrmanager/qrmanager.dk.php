<?php
//DANISH LANGUAGE FILE, STRINGS SHOULD BE LOCATED IN $QRMANAGER_STRINGS["nameOfString"] = "STRING";

		$QRMANAGER_STRINGS = array();

		/* GLOBAL STRINGS */
		$QRMANAGER_STRINGS["Children"] = 'Børn';
		$QRMANAGER_STRINGS["Guardians"] = 'Pædagoger';
		$QRMANAGER_STRINGS["Parents"] = 'Forældre';

		/*MAIN PAGE */
		$QRMANAGER_STRINGS["headerTitle"] = "QR Manager &middot; GIRAF";
		$QRMANAGER_STRINGS["breadCrumpMain"] = "QR Manager";
		$QRMANAGER_STRINGS["mainButtonEditQr"] = "Ret QR for bruger";
		$QRMANAGER_STRINGS["mainButtonChooseQr"] = "Vælg QR at printe";
		$QRMANAGER_STRINGS["mainButtonPrintAllQr"] = "Print alle QR";

		/* EDIT QR */
		$QRMANAGER_STRINGS["breadCrumpEdit"] = '<a href="index.php#qrManager/action=main">QR Manager</a> &rarr; Ret QR';
		$QRMANAGER_STRINGS["editQrButtonEdit"] = 'Ret';

		/* EDIT SUBMIT */
		$QRMANAGER_STRINGS["breadCrumpEditSubmit"] = '<a href="index.php#qrManager/action=main">QR Manager</a> &rarr; <a href="index.php#qrManager/action=edit">Ret QR</a> &rarr; Resultat';
		$QRMANAGER_STRINGS["editSubmitLeadInfoMessage"] = '%N har fået en ny QR kode, en mail er sendt til %N om dette'; //%N needs to be replaced with the appropriate name

		/* CHOOSE QR */
		$QRMANAGER_STRINGS["breadCrumpChoosePrint"] = '<a href="index.php#qrManager/action=main">QR Manager</a> &rarr; Vælg QR at printe';
		$QRMANAGER_STRINGS["choosePrintLeadInfoMessage"] = 'Vælg QR at printe';
		$QRMANAGER_STRINGS["choosePrintSubmitText"] = 'Bekræft';

		/* CHOOSE QR SUBMIT */
		$QRMANAGER_STRINGS["breadCrumpChoosePrintSubmit"] = '<a href="index.php#qrManager/action=main">QR Manager</a> &rarr; <a href="index.php#qrManager/action=choosePrint">Vælg QR at printe</a> &rarr; Resultat';
		$QRMANAGER_STRINGS["choosePrintSubmitLeadInfoMessage"] = ' brugere er markeret til print';
		$QRMANAGER_STRINGS["choosePrintSubmitButtonSubmit"] = 'Print';
?>