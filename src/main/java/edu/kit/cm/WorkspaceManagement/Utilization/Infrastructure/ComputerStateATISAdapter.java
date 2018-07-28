package edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;
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
		JSONArray jsonArray = new JSONArray(rt.getForEntity("https://workspace.cm.tm.kit.edu/layout/poolElements",String.class).getBody());

		UtilizationAdapter.getInstance().createPoolElementHashMap(jsonArray);
	}
	
	public void updateFreeSeatsFromATIS() throws Exception {
		String[] array = getSeatsStringArray();
		String[] string = array[array.length-1].split(" ");
        int id = Integer.parseInt(string[1]);
		System.out.println(id);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd_HH:mm");
        String sdate = string[0];
        Date date = formatter.parse(sdate);
        UtilizationAdapter.getInstance().updateSeats(date, id, MAX_ATIS_PCS);
	}

	public List<HistoryEntry> getSeats(LocalDateTime localDateTime) {
		List<HistoryEntry> list = new ArrayList<HistoryEntry>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_HH:mm");
		String[] array = getSeatsStringArray();
		for(int i = array.length-1; i >= 0; i--) {
			String[] string = array[i].split(" ");
			int seats = Integer.parseInt(string[1]);
			LocalDateTime date = LocalDateTime.parse(string[0], formatter);
			if(date.isBefore(localDateTime)){
				break;
			}
			list.add(new HistoryEntry(seats, date));
		}
		return list;
	}

	private String[] getSeatsStringArray() {
		RestTemplate rt = new RestTemplate();
		String in = rt.getForEntity("https://webadmin.informatik.kit.edu/pool/html/freeseats.txt",String.class).getBody();
		String[] stringArray = in.split("\\r?\\n");
		return  stringArray;
	}
}
