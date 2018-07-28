package edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class HistoryEntryMapper {
    
    public HistoryEntryMapper(){
    }

    public HistoryEntry map(HistoryEntryJPA entry) {
        return new HistoryEntry(entry.getFreeSeats(), entry.getDate(), entry.getType());
    }

    public HistoryEntryJPA map(HistoryEntry entry) {
        return new HistoryEntryJPA(entry.getFreeSeats(), entry.getDate(), entry.getType());
    }
}
