package edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.kit.cm.WorkspaceManagement.Utilization.Service.UtilizationAdapter;
import org.springframework.web.client.RestTemplate;

public class ComputerStateATISAdapter {
	
	private static final int MAX_ATIS_PCS = 72;
	
	public void updateComputersWithStatesFromATIS() throws Exception {		
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();

		RestTemplate rt = new RestTemplate();
		String in = rt.getForEntity("https://webadmin.informatik.kit.edu/pool/html/snmp_out.txt",String.class).getBody();
		String[] inputLineArray = in.split("\\n");

		for (String inputLine: inputLineArray) {
			JSONObject jsonObjectEntry = new JSONObject();
			if(inputLine.startsWith("Stand:") || inputLine.startsWith(".")) {
				continue;
			}
			String[] s = inputLine.split(" ");

			jsonObjectEntry.put("id", s[0]);
			jsonObjectEntry.put("state", s[1]);
			jsonArray.put(jsonObjectEntry);
		}
		jsonObject.put("data", jsonArray);
		UtilizationAdapter.getInstance().updateStates(jsonObject);
	}
	
	public void generatePoolElementsFromWorkspace() {
		RestTemplate rt = new RestTemplate();
		JSONObject jsonObject = new JSONObject(rt.getForEntity("https://workspace.cm.tm.kit.edu/learningDesks",String.class).getBody());
		UtilizationAdapter.getInstance().createPoolElementHashMap(jsonObject);
	}
	
	public void updateFreeSeatsFromATIS() throws Exception {
        RestTemplate rt = new RestTemplate();
        String in = rt.getForEntity("https://webadmin.informatik.kit.edu/pool/html/freeseats.txt",String.class).getBody();
        String[] inputLineArray = in.split(" ");
        int id = Integer.parseInt(inputLineArray[inputLineArray.length-1].replaceAll("\\D+",""));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd_HH:mm");
        String[] sdate = inputLineArray[inputLineArray.length-2].split("\\n");
        String stdate = sdate[sdate.length-1].replaceAll("[^\\_:.0123456789]","");
        Date date = formatter.parse(stdate);
        UtilizationAdapter.getInstance().updateSeats(date, id, MAX_ATIS_PCS);
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
}
