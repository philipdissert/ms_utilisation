package edu.kit.cm.WorkspaceManagement.Utilization.Domain;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
public class HistoryEntry {

    private LocalDateTime date;
    private int freeSeats;
    private String type;

    public HistoryEntry(int freeSeats, LocalDateTime date, String type){
        this.freeSeats = freeSeats;
        this.date = date;
        this.type = type;
    }
}
