package edu.kit.cm.WorkspaceManagement.RESTMANAGER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class RestManager {
	
	public static JSONObject sendGetRequest(String subPath) {
		return jsonRequest(subPath, "GET");
	}
	public static JSONObject sendGetRequest(String subPath, String param) {
		return jsonRequest(subPath+"/"+param, "GET");
	}
	
	private static JSONObject jsonRequest(String subPath,String requestMethod) {
		try {
			String url = "https://workspace.cm.tm.kit.edu/"+subPath;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod(requestMethod);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			 String jsonText = readAll(in);
		     return new JSONObject(jsonText);	
		} catch(Exception e) {
			e.printStackTrace();
			return new JSONObject();
		}
	}
	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
}
