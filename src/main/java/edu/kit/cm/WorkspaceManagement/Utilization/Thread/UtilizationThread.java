package edu.kit.cm.WorkspaceManagement.Utilization.Thread;

import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.FreeSeats;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.UtilizationAdapter;

import java.time.LocalDateTime;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

public class UtilizationThread extends Thread {

	@Override
	public void run() {
		UtilizationAdapter utilizationAdapter = UtilizationAdapter.getInstance();
		RestTemplate rt = new RestTemplate();
		while(true){
			//ComputerStateATISAdapter csaa = new ComputerStateATISAdapter();
			try {
				//
				utilizationAdapter.createPoolElementHashMap(new JSONArray(rt.getForEntity("https://workspace.cm.tm.kit.edu/layout/poolElements", String.class).getBody()));
				utilizationAdapter.updateSeats();
				utilizationAdapter.updateStates(new JSONObject(rt.getForEntity("https://workspace.cm.tm.kit.edu/getComputersWithState", String.class).getBody()));
				//csaa.updateComputersWithStatesFromATIS();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
