package edu.kit.cm.WorkspaceManagement;

import edu.kit.cm.WorkspaceManagement.Utilization.Thread.UtilizationThread;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.UtilizationAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.FreeSeats;

//@EnableJpaRepositories
//@ComponentScan
@SpringBootApplication
public class WorkspaceManagementApplication {


	public static void main(String[] args) {
		SpringApplication.run(WorkspaceManagementApplication.class, args);

		UtilizationAdapter utilizationAdapter = UtilizationAdapter.getInstance();
		FreeSeats csaa = new FreeSeats();
		RestTemplate rt = new RestTemplate();
		try {
			//csaa.generatePoolElementsFromWorkspace();
			//csaa.updateComputersWithStatesFromATIS();
			utilizationAdapter.createPoolElementHashMap(rt.getForEntity("https://workspace.cm.tm.kit.edu/layout/poolElementsJSONArray", JSONArray.class).getBody());			
			utilizationAdapter.updateStates(rt.getForEntity("https://workspace.cm.tm.kit.edu/getComputersWithState", JSONObject.class).getBody());
			utilizationAdapter.updateSeats();
			//csaa.updateFreeSeatsFromATIS();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UtilizationThread ut = new UtilizationThread();
		ut.start();


		//new HistoryAdapter().createEntity(new HistoryEntry(2,LocalDateTime.of(1,1,1,1,1,1),"PC"));
		//upa.createEntity(new HistoryEntry(2,LocalDateTime.of(1,1,1,1,1,1),"PC"));


	}
}