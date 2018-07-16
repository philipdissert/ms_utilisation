package edu.kit.cm.WorkspaceManagement;

import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.UtilizationThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.ComputerStateATISAdapter;

@SpringBootApplication
public class WorkspaceManagementApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(WorkspaceManagementApplication.class, args);

		ComputerStateATISAdapter csaa = new ComputerStateATISAdapter();
		try {
			csaa.generatePoolElementsFromWorkspace();
			csaa.updateComputersWithStatesFromATIS();
			csaa.updateFreeSeatsFromATIS();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UtilizationThread ut = new UtilizationThread();
		ut.start();
		
		//ThreadManager tm = new ThreadManager(csaa);
		//tm.exec();
	}
}