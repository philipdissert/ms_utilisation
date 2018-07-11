package edu.kit.cm.WorkspaceManagement;

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
		
		
		//ThreadManager tm = new ThreadManager(csaa);
		//tm.exec();
	}
}