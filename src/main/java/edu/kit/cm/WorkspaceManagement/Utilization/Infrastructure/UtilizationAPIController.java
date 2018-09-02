package edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.kit.cm.WorkspaceManagement.Utilization.Service.UtilizationAdapter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
public class UtilizationAPIController {
	
	UtilizationAdapter utilizationAdapter = UtilizationAdapter.getInstance();

	@PutMapping("/update")
	public void update(@RequestBody Map updateMap) {
		utilizationAdapter.createPoolElementHashMap(new JSONArray(updateMap.get("poolElements").toString()));
		utilizationAdapter.updateSeats();
		utilizationAdapter.updateStates(new JSONObject(updateMap.get("computersWithState").toString()));
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
