package edu.kit.cm.ms_utilisation.Utilization.Service;

import java.time.LocalDateTime;
import java.util.List;

import edu.kit.cm.ms_utilisation.Utilization.Domain.HistoryEntry;

public class FreeSeatManagement{

	private static FreeSeatManagement freeSeatManagement;
	private HistoryRepositoryService historyRepositoryService;

	private FreeSeatManagement() {}

	public static FreeSeatManagement getInstance() {
		if(freeSeatManagement!=null){
			return freeSeatManagement;
		} return freeSeatManagement = new FreeSeatManagement();
	}

	public void setHistoryRepositoryService(HistoryRepositoryService historyRepositoryService) {
		this.historyRepositoryService = historyRepositoryService;
	}

	public void addHistoryEntryList(List<HistoryEntry> historyEntryList) {
		historyRepositoryService.createAllEntitys(historyEntryList);
	}

	public LocalDateTime getLastHistoryEntryDate(){
		LocalDateTime date = historyRepositoryService.getLatestDate();
		if (date == null) {
			return LocalDateTime.of(0,1,1,0,0,0);
		}
		return date;
	}


	/*
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
	}*/
}
