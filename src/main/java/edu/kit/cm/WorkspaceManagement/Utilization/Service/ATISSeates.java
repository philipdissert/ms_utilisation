package edu.kit.cm.WorkspaceManagement.Utilization.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;

public class ATISSeates implements IFreeSeats {
	private List<HistoryEntry> getHistoryEntryList(LocalDateTime localDateTime) {
		List<HistoryEntry> list = new ArrayList<HistoryEntry>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd_HH:mm");
		String[] array = getSeatsStringArray();
		for(int i = array.length-1; i >= 0; i--) {
			String[] string = array[i].split(" ");
			int seats = Integer.parseInt(string[1]);
			LocalDateTime date = LocalDateTime.parse(string[0], formatter);
			//System.out.println(date.toString()+ localDateTime.toString());
			if(!date.isAfter(localDateTime)){
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


	@Override
	public List<HistoryEntry> getSeats(LocalDateTime localDateTime) {
		return getHistoryEntryList(localDateTime);
	}
}
