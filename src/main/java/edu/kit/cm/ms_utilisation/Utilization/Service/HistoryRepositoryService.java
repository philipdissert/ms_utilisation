package edu.kit.cm.ms_utilisation.Utilization.Service;

import edu.kit.cm.ms_utilisation.Utilization.Domain.HistoryEntry;
import edu.kit.cm.ms_utilisation.Utilization.Infrastructure.persistence.HistoryEntryJPA;
import edu.kit.cm.ms_utilisation.Utilization.Infrastructure.persistence.HistoryEntryMapper;
import edu.kit.cm.ms_utilisation.Utilization.Infrastructure.persistence.HistoryCrudRepository;
//import edu.kit.cm.ms_utilisation.Utilization.Infrastructure.persistence.HistoryRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryRepositoryService {

    private HistoryCrudRepository historyCrudRepository;
    private HistoryEntryMapper historyEntryMapper;

    public HistoryRepositoryService(HistoryCrudRepository historyCrudRepository) {
        this.historyCrudRepository = historyCrudRepository;
        this.historyEntryMapper = new HistoryEntryMapper();
    }


    public HistoryEntry createEntity(HistoryEntry entity) {
        HistoryEntryJPA entry = historyCrudRepository.save(historyEntryMapper.map(entity));
        return historyEntryMapper.map(entry);
    }

    public Iterable<HistoryEntryJPA> createAllEntitys(List<HistoryEntry> entries) {
        List<HistoryEntryJPA> hpa = new ArrayList<>();
        for(HistoryEntry x: entries){
            hpa.add(historyEntryMapper.map(x));
        }
        return historyCrudRepository.saveAll(hpa);
    }

    public List<HistoryEntry> findAllEntitys() {
        List<HistoryEntry> list = new ArrayList<HistoryEntry>();
        for(HistoryEntryJPA historyEntryJpa: historyCrudRepository.findAll()){
            list.add(historyEntryMapper.map(historyEntryJpa));
        }
        return list;
    }

    public List<HistoryEntry> findAllEntitysDesc() {
        List<HistoryEntry> list = new ArrayList<>();
        for (HistoryEntryJPA historyEntryJpa: historyCrudRepository.findAllByDateIsNotNullOrderByDateDesc()) {
            list.add(historyEntryMapper.map(historyEntryJpa));
        }
        return list;
    }

    public LocalDateTime getLatestDate() {

        HistoryEntry historyEntry = historyEntryMapper.map(historyCrudRepository.findFirstByDateIsNotNullOrderByDateDesc());
        if(historyEntry == null) {
            return null;
        }
        return historyEntry.getDate();
    }

    public List<HistoryEntry> findAllBetween(LocalDateTime from, LocalDateTime to) {
        List<HistoryEntry> list = new ArrayList<HistoryEntry>();
        historyCrudRepository.findByDateIsBetween(from, to).forEach(x-> {
            list.add(historyEntryMapper.map(x));
        });
        return list;
    }


    /**
     * Finds all Entries that have a specified Weekday
     * @param dayOfWeek
     * @return
     */
    public List<HistoryEntry> findAllOnDayOfWeek(DayOfWeek dayOfWeek) {
        long newDay=dayOfWeek.getValue()-getLatestDate().getDayOfWeek().getValue();
        if(newDay>0) {
            newDay=newDay-7;
        }

        LocalDate localDate = getLatestDate().toLocalDate();
        List<HistoryEntry> entriesOnWeekDay = new ArrayList<>();

        for (HistoryEntry historyEntry :findAllEntitysDesc()) {
            if(historyEntry.getDate().toLocalDate().isEqual(localDate.plusDays(newDay))) {
                entriesOnWeekDay.add(historyEntry);
            } else if(historyEntry.getDate().toLocalDate().isAfter(localDate.plusDays(newDay))) {

            } else if(historyEntry.getDate().toLocalDate().isBefore(localDate.plusDays(newDay))) {
                newDay-=7;
            }
        }
        return entriesOnWeekDay;
    }

}
