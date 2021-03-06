package edu.kit.cm.ms_utilisation.Utilization.Domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
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
