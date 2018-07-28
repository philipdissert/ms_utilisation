package edu.kit.cm.WorkspaceManagement;

import edu.kit.cm.WorkspaceManagement.Utilization.Thread.UtilizationThread;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.UtilizationAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.ComputerStateATISAdapter;

//@EnableJpaRepositories
//@ComponentScan
@SpringBootApplication
public class WorkspaceManagementApplication {


	public static void main(String[] args) {
		SpringApplication.run(WorkspaceManagementApplication.class, args);

		UtilizationAdapter utilizationAdapter = UtilizationAdapter.getInstance();
		ComputerStateATISAdapter csaa = new ComputerStateATISAdapter();
		try {
			csaa.generatePoolElementsFromWorkspace();
			csaa.updateComputersWithStatesFromATIS();
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