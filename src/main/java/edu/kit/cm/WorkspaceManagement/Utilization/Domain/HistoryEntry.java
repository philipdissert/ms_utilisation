package edu.kit.cm.WorkspaceManagement.Utilization.Domain;

import lombok.Getter;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@ToString
@Getter
public class HistoryEntry {

    private LocalDateTime date;
    private int freeSeats;

    public HistoryEntry(int freeSeats, LocalDateTime date){
        this.freeSeats = freeSeats;
        this.date = date;

    }
}
