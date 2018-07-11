package edu.kit.cm.WorkspaceManagement.Utilization.Domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class Prediction {
	private final History history;
	
	public Prediction(History history) {
		this.history = history;		
	}
}
