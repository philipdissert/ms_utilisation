package edu.kit.cm.WorkspaceManagement.Utilization.Service;

import java.time.LocalDateTime;
import java.util.List;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;

public interface IFreeSeats {
	public List<HistoryEntry> getSeats(LocalDateTime localDateTime);
}
