package com.sager;

public class Utils {

	public static java.lang.Object DevolveDownload(java.lang.Object download) {
		return download.toString().equals("0")?"FileSystem":"Download";
	}

}
