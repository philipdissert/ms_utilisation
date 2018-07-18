package edu.kit.cm.WorkspaceManagement.Utilization.Domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class CurrentUtilization {
	private int freeWorkspaces;
	private int occupiedWorkspaces;
	private int printerUtilization;
	private double percentageFree;
	private double percentageOccupied;
	
	public CurrentUtilization(int freeWorkspaces, int occupiedWorkspaces, int maxworkspaces) {
		this.freeWorkspaces = freeWorkspaces;
		this.occupiedWorkspaces = occupiedWorkspaces;
		this.percentageFree =     Math.round(((double) freeWorkspaces/(double)maxworkspaces)*100.0);//100d/(double)Maxworkspaces*(double)freeWorkspaces);
		this.percentageOccupied = Math.round(((double) occupiedWorkspaces/(double)maxworkspaces)*100.0);
	}
}
