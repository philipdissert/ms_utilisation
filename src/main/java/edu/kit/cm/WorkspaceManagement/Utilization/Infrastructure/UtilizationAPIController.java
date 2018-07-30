package edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.kit.cm.WorkspaceManagement.Utilization.Service.UtilizationAdapter;


@CrossOrigin
@RestController
public class UtilizationAPIController {
	
	UtilizationAdapter utilizationAdapter = UtilizationAdapter.getInstance();

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
	public int[] getPredictionAtDate(@PathVariable("day") String date) {return utilizationAdapter.getPredictionAtDate(date);}
}
