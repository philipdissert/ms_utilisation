package edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class HistoryEntryMapper {
    
    public HistoryEntryMapper(){
    }

    public HistoryEntry map(HistoryEntryJPA entry) {
        if (entry == null) {
            return null;
        }
        return new HistoryEntry(entry.getFreeSeats(), entry.getDate());
    }

    public HistoryEntryJPA map(HistoryEntry entry) {
        if (entry == null) {
            return null;
        }
        return new HistoryEntryJPA(entry.getFreeSeats(), entry.getDate());
    }
}
