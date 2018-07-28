package edu.kit.cm.WorkspaceManagement.Utilization.Thread;

import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.ComputerStateATISAdapter;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.UtilizationAdapter;

import java.time.LocalDateTime;

public class UtilizationThread extends Thread {

	@Override
	public void run() {
		UtilizationAdapter utilizationAdapter = UtilizationAdapter.getInstance();
		while(true){
			ComputerStateATISAdapter csaa = new ComputerStateATISAdapter();
			try {
				utilizationAdapter.updateSeats();
				csaa.updateComputersWithStatesFromATIS();
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
