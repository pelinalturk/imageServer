package com.imageserver.helpers;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import com.imageserver.exceptions.FileNameException;


public class FileNameHelper {
	
	public String generateUniqueNumber() {
		int min = 10000;
		int max = 99999;
		int random_int = (int) (Math.random() * (max - min + 1) + min);
		return "" + random_int;
	}
	
	public String generateFileName(String fileName) {

		String shortRandomAlphabet = generateUniqueNumber();

		String dateStrFormat = DateTime.now().toString("HHmmss_ddMMyyyy");

		int indexOfExtension = fileName.indexOf(".");
		String extensionName = fileName.substring(indexOfExtension);

		return dateStrFormat + "_" + shortRandomAlphabet + extensionName;

	}

	public String generateDisplayName(String orgFileName) {
		String orgCleanPath = StringUtils.cleanPath(orgFileName);

		if (orgCleanPath.contains(".."))
			throw new FileNameException("Sorry! Filename contains invalid path sequence " + orgCleanPath);

		return generateFileName(orgCleanPath);
	}
}
