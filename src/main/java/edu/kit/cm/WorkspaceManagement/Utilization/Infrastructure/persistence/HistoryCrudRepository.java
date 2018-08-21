package edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.History;
import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface HistoryCrudRepository extends CrudRepository<HistoryEntryJPA, Long> {

//    @Query("SELECT max(HISTORY_ENTRYJPA.date) From HISTORY_ENTRYJPA")
//    public Long countById();

    public HistoryEntryJPA findFirstByDateIsNotNullOrderByDateDesc();

    public List<HistoryEntryJPA> findByDateIsBetween(LocalDateTime from, LocalDateTime to);

    public List<HistoryEntryJPA> findAllByDateIsNotNullOrderByDateDesc();

}
