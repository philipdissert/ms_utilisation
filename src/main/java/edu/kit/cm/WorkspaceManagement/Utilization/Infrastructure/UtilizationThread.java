package edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure;

import edu.kit.cm.WorkspaceManagement.Utilization.Service.UtilizationAdapter;

public class UtilizationThread extends Thread {

	@Override
	public void run() {
		UtilizationAdapter utilizationAdapter = UtilizationAdapter.getInstance();
		while(true){
			ComputerStateATISAdapter csaa = new ComputerStateATISAdapter();
			try {
				//csaa.updateFreeSeatsFromATIS();
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
