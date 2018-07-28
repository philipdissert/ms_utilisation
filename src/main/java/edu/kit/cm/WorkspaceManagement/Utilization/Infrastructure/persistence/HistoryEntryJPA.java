package edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence;


import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Entity
public class HistoryEntryJPA {
    @Id
    @GeneratedValue
    private int id;
    private LocalDateTime date;
    private int freeSeats;

    public HistoryEntryJPA() {

    }

    public HistoryEntryJPA(int freeSeats, LocalDateTime date) {
        this.freeSeats = freeSeats;
        this.date = date;
    }
}
