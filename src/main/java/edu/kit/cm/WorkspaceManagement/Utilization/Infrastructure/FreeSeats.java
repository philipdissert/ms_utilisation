package edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure;

import java.time.LocalDateTime;
import java.util.List;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.ATISSeates;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.IFreeSeats;

public class FreeSeats {
	
	IFreeSeats seats = new ATISSeates();
	
	public List<HistoryEntry> getSeats(LocalDateTime localDateTime) {
		return seats.getSeats(localDateTime);
	}
	/*
	
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
	
	public void generatePoolElementsFromWorkspace () throws RestClientException, JSONException {
		RestTemplate rt = new RestTemplate();
		JSONArray jsonArray = new JSONArray(rt.getForEntity("https://workspace.cm.tm.kit.edu/layout/poolElements",String.class).getBody());

		UtilizationAdapter.getInstance().createPoolElementHashMap(jsonArray);
	}
	
	public void updateFreeSeatsFromATIS() throws Exception {
		String[] array = getSeatsStringArray();
		String[] string = array[array.length-1].split(" ");
        int id = Integer.parseInt(string[1]);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd_HH:mm");
        String sdate = string[0];
        Date date = formatter.parse(sdate);
        UtilizationAdapter.getInstance().updateSeats(date, id, MAX_ATIS_PCS);
	}
		*/

}
