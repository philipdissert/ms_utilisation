package edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

public class ThreadManager {
	
	@Autowired
	private TaskExecutor taskExecutor;
	private ComputerStateATISAdapter csaa;
	
	public ThreadManager(ComputerStateATISAdapter csaa) {
		this.csaa = csaa;
	}
	
	public void exec() {
		taskExecutor.execute(new Runnable() {
	        @Override
	        public void run() {
	        	while(true) {
	        		try {
						csaa.updateComputersWithStatesFromATIS();
						csaa.updateFreeSeatsFromATIS();
						System.out.println("test");
						Thread.sleep(10000);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}            		
	        	}            	   			
	        }
	    });	
	}
	
}
