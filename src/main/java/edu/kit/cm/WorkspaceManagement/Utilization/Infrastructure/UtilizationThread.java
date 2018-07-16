package edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure;

import edu.kit.cm.WorkspaceManagement.Utilization.Service.UtilizationAdapter;

public class UtilizationThread extends Thread {

	@Override
	public void run() {
		while(true){
			ComputerStateATISAdapter csaa = new ComputerStateATISAdapter();
			try {
				csaa.updateFreeSeatsFromATIS();
				csaa.updateComputersWithStatesFromATIS();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(UtilizationAdapter.getInstance().getCurrentState());
		}


	}
}
