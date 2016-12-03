package com.roc.helloapp.util;

import java.io.File;
import java.io.FileInputStream;

/**
 * IO读写Util类
 */
public class IoUtil {
	
	@SuppressWarnings("resource")
	public static long getFileSizes(File f) throws Exception {
		long s = 0;
		if (f.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(f);
			s = fis.available();
		} else {
		}
		return s;
	}
}