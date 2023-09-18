package com.amazonaws.lambda.demo;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//GETでAPIを叩いて、String形式のjsonを返すクラス
public class HttpRequest {

	//URLを引数で渡す
	public static String runSample(String strUrl) {

		HttpURLConnection  urlConn = null;
		InputStream in = null;
		BufferedReader reader = null;
		String strOutput = "";

		try {
			//接続するURLを指定する
			URL url = new URL(strUrl);
			
			//コネクションを取得する
			urlConn = (HttpURLConnection) url.openConnection();
			
			urlConn.setRequestMethod("GET");

			urlConn.connect();
			
			int status = urlConn.getResponseCode();
			
			System.out.println("HTTPステータス:" + status);
			
		    if (status == HttpURLConnection.HTTP_OK) {

				in = urlConn.getInputStream();
				
		    	reader = new BufferedReader(new InputStreamReader(in));
		    	StringBuilder output = new StringBuilder("");
		    	
				String line;

				while ((line = reader.readLine()) != null) {
					output.append(line);
				}
				strOutput = output.toString();
				System.out.println(strOutput);
		      }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (urlConn != null) {
					urlConn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return strOutput;
	}
}
