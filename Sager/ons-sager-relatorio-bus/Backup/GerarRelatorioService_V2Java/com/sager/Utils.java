package com.sager;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import sun.misc.BASE64Encoder;

public class Utils {

	public static String DevolveLabel(String tipoRelat, String tipoArquivo) {
		return (tipoRelat + "_" + tipoArquivo);
	}

	public static java.lang.Object DevolveDownload(String download, String tipoRelat) {
		return download.toString().equals("0")?tipoRelat + "_FileSystem":tipoRelat + "_Download";
	}

	public static java.lang.Object DevolveTipoArquivo(java.lang.Object tipoArquivo) {
		return tipoArquivo.toString().equals("xml")?"XML":"Dat";
	}

}
