package edu.kit.cm.ms_utilisation.Utilization.Infrastructure;


import edu.kit.cm.ms_utilisation.Utilization.Domain.HistoryEntry;
import edu.kit.cm.ms_utilisation.Utilization.Service.FreeSeatManagement;
import gherkin.lexer.Fr;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.kit.cm.ms_utilisation.Utilization.Service.UtilizationAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
public class UtilizationAPIController {
	
	UtilizationAdapter utilizationAdapter = UtilizationAdapter.getInstance();

	@PutMapping("/update")
	public void update(@RequestBody Map updateMap) {
		utilizationAdapter.createPoolElementHashMap(new JSONArray(updateMap.get("poolElements").toString()));
		utilizationAdapter.updateStates(new JSONObject(updateMap.get("computersWithState").toString()));
		utilizationAdapter.updateSeats();
	}

	@GetMapping("/lastHistoryEntryDate")
	public ResponseEntity<LocalDateTime> getLastHistoryEntryDate() {
		return new ResponseEntity<>(FreeSeatManagement.getInstance().getLastHistoryEntryDate(),HttpStatus.OK);
	}

	@PutMapping("/addHistoryEntryList")
	public void addHistoryEntryList(@RequestBody Map<String,Integer> historyEntryMap) {
		List<HistoryEntry> historyEntryList = new ArrayList<>();
		for(String x :historyEntryMap.keySet()){
			historyEntryList.add(new HistoryEntry(historyEntryMap.get(x),LocalDateTime.parse(x)));
		}
		FreeSeatManagement.getInstance().addHistoryEntryList(historyEntryList);
	}

	@GetMapping("/currentState")
	public String getCurrentState() {		
		return utilizationAdapter.getCurrentState().toString();
	}
	
	@GetMapping("/currentUtilization")
	public String getCurrentUtilization() {
		return utilizationAdapter.getCurrentUtilization().toString();
	}
	
	@GetMapping("/currentUtilization/free")
	public int getCurrentUtilizationFreeWorksaces() {
		return utilizationAdapter.getFreeWorkspaces();
	}
	
	@GetMapping("/currentUtilization/occupied")
	public int getCurrentUtilizationOccupiedWorksaces() {
		return utilizationAdapter.getOccupiedWorkspaces();
	}
	
	@GetMapping("/currentUtilization/percentageFree")
	public double getCurrentUtilizationFreePercentage() {
		return utilizationAdapter.getPercentageFree();
	}
	
	@GetMapping("/currentUtilization/percentageOccupied")
	public double getCurrentUtilizationOccupiedPercentage() {
		return utilizationAdapter.getPercentageOccupied();
	}

	@GetMapping("/prediction/day/{day}")
	public int[] getPredictionAtWeekday(@PathVariable("day") String date) {return utilizationAdapter.getPredictionAtWeekDay(date);}

	@GetMapping("/prediction/date/{day}")
	public int [] getPredictionAtDate(@PathVariable("day") String date) {
		return utilizationAdapter.getPredictionAtDate(date);
	}
}
