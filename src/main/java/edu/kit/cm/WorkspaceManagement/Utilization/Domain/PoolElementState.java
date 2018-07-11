package edu.kit.cm.WorkspaceManagement.Utilization.Domain;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class PoolElementState {
	private int id;
	private String type;
	private int state;
	
	public PoolElementState(int id, String type) {
		this.id = id;
		this.type = type;
		this.state = 0;
	}
}
