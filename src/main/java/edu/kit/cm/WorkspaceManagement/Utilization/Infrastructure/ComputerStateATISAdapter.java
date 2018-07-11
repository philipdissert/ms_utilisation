package edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.kit.cm.WorkspaceManagement.RESTMANAGER.RestManager;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.UtilizationAdapter;

public class ComputerStateATISAdapter {
	
	private static final int MAX_ATIS_PCS = 72;
	
	public void updateComputersWithStatesFromATIS() throws Exception {		
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();		
		
		
		BufferedReader in = getBufferedReaderByAdress("https://webadmin.informatik.kit.edu/pool/html/snmp_out.txt");	
		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			if(!inputLine.startsWith("Stand:")
					&& !inputLine.startsWith(".")) {
				String[] s = inputLine.split(" ");
				JSONObject jsonObjectEntry = new JSONObject();
				jsonObjectEntry.put("id", s[0]);
				jsonObjectEntry.put("state", s[1]);
				
				jsonArray.put(jsonObjectEntry);
			}			
		}
		in.close();
		jsonObject.put("data", jsonArray);
		UtilizationAdapter.getInstance().updateStates(jsonObject);
	}
	
	public void generatePoolElementsFromWorkspace() {
		JSONObject jsonObject = RestManager.sendGetRequest("/learningDesks");
		UtilizationAdapter.getInstance().createPoolElementHashMap(jsonObject);
	}
	
	public void updateFreeSeatsFromATIS() throws Exception {
		BufferedReader in = getBufferedReaderByAdress("https://webadmin.informatik.kit.edu/pool/html/freeseats.txt");
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			String[] s = inputLine.split(" ");			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
			String sdate = s[0].replaceAll("[.]", "-");
			Date date = formatter.parse(sdate);
			UtilizationAdapter.getInstance().updateSeats(date, Integer.valueOf(s[1]), MAX_ATIS_PCS);
		}
		in.close();
	}
//	
//	public int getLastFreeSeatsFromATIS() throws Exception {
//		BufferedReader in = getBufferedReaderByAdress("https://webadmin.informatik.kit.edu/pool/html/freeseats.txt");
//		String inputLine;
//		String erg="";
//		while ((inputLine = in.readLine()) != null) erg=inputLine;
//		return Integer.valueOf(erg.split(" ")[1]);
//	}
//	
	private BufferedReader getBufferedReaderByAdress(String addr) throws Exception {
		String url = addr;		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		return in;
	}
}
