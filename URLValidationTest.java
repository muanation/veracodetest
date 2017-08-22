package com.veracode;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class URLValidationTest {

	@Test
	public void testValidateURL() {
		String url = "";

		URLValidation val = new URLValidationImpl();

		FileReader fileReader;
		String fileContents = "";

		try {
			fileReader = new FileReader(
					"/Users/muanation/workspace-test/veracodetest/src/com/veracode/veracodeUrlTest.txt");

			int i;

			while ((i = fileReader.read()) != -1) {
				char ch = (char) i;

				fileContents = fileContents + ch;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		url = fileContents;
		List<String> list = val.getURLList(url);
		boolean testSuccess = list != null && list.size() > 0 ? true : false;

		fail("Test succeeded? " + testSuccess + " for url: " + url);

	}

}// end test class
