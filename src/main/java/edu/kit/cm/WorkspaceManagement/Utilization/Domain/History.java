package edu.kit.cm.WorkspaceManagement.Utilization.Domain;

import java.util.Date;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class History {
	private final HashMap<Date, CurrentUtilization> utilizationList = new HashMap();
}
