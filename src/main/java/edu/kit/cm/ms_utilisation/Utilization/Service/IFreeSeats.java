package edu.kit.cm.ms_utilisation.Utilization.Service;

import java.time.LocalDateTime;
import java.util.List;

import edu.kit.cm.ms_utilisation.Utilization.Domain.HistoryEntry;

public interface IFreeSeats {
	public List<HistoryEntry> getSeats(LocalDateTime localDateTime);
}
