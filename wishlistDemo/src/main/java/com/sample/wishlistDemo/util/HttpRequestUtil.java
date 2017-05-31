package com.sample.wishlistDemo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpRequestUtil {

	public static String sendGet(String url, String param, Map<String, String> headers) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url;
			if (param != null) {
				urlNameString = url + "?" + param;
			}
			URL realUrl = new URL(urlNameString);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			Set<String> headersKeys = headers.keySet();
			for (String key : headersKeys) {
				connection.setRequestProperty(key, headers.get(key));
			}
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("GET Error" + e);
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	public static String sendPost(String url, String param, String body, Map<String, String> headers) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			if (headers != null) {
				Set<String> headersKeys = headers.keySet();
				for (String key : headersKeys) {
					conn.setRequestProperty(key, headers.get(key));
				}
			}
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			if (param != null) {
				out.print(param);
			}

			if (body != null) {
				out.write(body);
			}
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("POST Error: " + e);
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
	      //String s=sendGet("https://gs-qa.playcln.com/cxf/spin/rest/getPlayer", "token=6a666c365f2d40e8a80bc1e4bf9108ea");
	      //System.out.println(s);
	     // String s = "kkfkkkkdkd?fkkkd";
	      //System.out.println(s.split("\\?")[0]);
	      //System.out.println(s.split("\\?")[1]);
	      //String sr=("https://gs-qa.playcln.com/cxf/spin/rest/resolveWager", "win=100&sessionid=6a666c365f2d40e8a80bc1e4bf9108ea&playerid=6a666c365f2d40e8a80bc1e4bf9108ea&cycleid=126&actionid=135");
	      //System.out.println(sr);
	      
	      
	      String url = "https://api.beta.yaas.io/hybris/oauth2/v1/token";
	      String param = "client_id=R64UWNPnzFwkg74XAYiVc6mWpB0Qtu1R&client_secret=EEmhYGZXGXPkjBpH&scope=hybris.document_view&grant_type=client_credentials";
	      System.out.println(sendPost(url, param, null,null));
	      
	      String addUrl = "https://api.beta.yaas.io/hybris/document/v1/caashiring/caashiring.test/data/e123";
	      String authorizationForAdd = "Bearer 021-4eab2dfa-0f80-47be-b883-f508df4046b8";
	      String contentTypeForAdd = "application/json";
	      Map<String,String> headersForMap = new HashMap<String, String>();
	      headersForMap.put("Authorization", authorizationForAdd);
	      headersForMap.put("Content-Type", contentTypeForAdd);
	      
	      //String body = "{\"key\":\"testkey"+new Date()+" \"}";
	      //System.out.println(body);
	      //System.out.println(new HttpRequest().sendPost(addUrl, null, body,headersForMap));
	      
	      System.out.println(sendGet(addUrl, null, headersForMap));
	      
	      
	      
	      
	  }
}
