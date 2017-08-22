package com.veracode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class URLValidationImpl implements URLValidation {

	@Override
	public List<String> getURLList(String url) {
		System.out
				.println("URLValidationImpl::validateURL() called with passed url: "
						+ url);
		URL objUrl;
		List<String> urlList = new ArrayList<String>();

		if (isValidURL(url)) {
			try {
				// get URL content
				objUrl = new URL(url);
				URLConnection conn = objUrl.openConnection();

				// open the stream and put it into BufferedReader
				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));

				String inputLine;
				while ((inputLine = br.readLine()) != null) {
					int beginIndex = inputLine.indexOf("href=");
					int endIndex = inputLine.indexOf(">");
					boolean isIndexValid = beginIndex != -1 && endIndex != -1
							&& (beginIndex + 6 < endIndex - 3);
					if (isIndexValid && urlList.size() < 50) {
						String innerUrl = "";
						try {
							innerUrl = inputLine.substring(beginIndex + 6,
									endIndex - 3);
							if (!urlList.contains(innerUrl)) {
								urlList.add(innerUrl);
							}
						} catch (StringIndexOutOfBoundsException e) {
							e.printStackTrace();
						}

					}
				}
				br.close();

			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		System.out.println("List of URL's: " + urlList);

		return urlList;
	}

	private boolean isValidURL(String url) {

		try {
			new URI(url).parseServerAuthority();
			return true;
		} catch (URISyntaxException e) {
			return false;
		}

	}
}// end class
