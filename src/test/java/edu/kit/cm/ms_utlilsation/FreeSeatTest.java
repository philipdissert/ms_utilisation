package edu.kit.cm.ms_utlilsation;

import edu.kit.cm.ms_utilisation.Utilization.Domain.HistoryEntry;
import edu.kit.cm.ms_utilisation.Utilization.Infrastructure.persistence.HistoryCrudRepository;
import edu.kit.cm.ms_utilisation.Utilization.Infrastructure.persistence.HistoryEntryJPA;
import edu.kit.cm.ms_utilisation.Utilization.Service.FreeSeatManagement;
import edu.kit.cm.ms_utilisation.Utilization.Service.HistoryRepositoryService;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FreeSeatTest {

    private List<HistoryEntry> mockedList;

    @Before
    public void before() {
        HistoryEntry lastEntry = new HistoryEntry(10, LocalDateTime.of(2013,5,6+21,12,30,10));
        mockedList = new ArrayList();
        mockedList.add(lastEntry);
        mockedList.add(new HistoryEntry(20, LocalDateTime.of(2013,5,6+14,12,30,10)));
        mockedList.add(new HistoryEntry(30, LocalDateTime.of(2013,5,6+7,12,30,10)));

        mockedList.add(new HistoryEntry(70, LocalDateTime.of(2013,5,7,12,30,10)));
        mockedList.add(new HistoryEntry(40, LocalDateTime.of(2013,5,6,12,30,10)));

        mockedList.add(new HistoryEntry(30, LocalDateTime.of(2013,5,5,12,30,10)));
        mockedList.add(new HistoryEntry(40, LocalDateTime.of(2013,5,4,12,30,10)));
        mockedList.add(new HistoryEntry(50, LocalDateTime.of(2013,5,3,12,30,10)));
        mockedList.add(new HistoryEntry(60, LocalDateTime.of(2013,5,2,12,30,10)));
        mockedList.add(new HistoryEntry(10, LocalDateTime.of(2013,5,1,12,30,10)));
    }

    @Test
    public void testGetLastHistoryEntryDate() {
        HistoryEntryJPA historyEntryJPA = new HistoryEntryJPA(10, LocalDateTime.of(2013,5,6+21,12,30,10));
        HistoryCrudRepository historyCrudRepository = mock(HistoryCrudRepository.class);
        when(historyCrudRepository.saveAll(any())).thenReturn(null);
        when(historyCrudRepository.findFirstByDateIsNotNullOrderByDateDesc()).thenReturn(historyEntryJPA);
        HistoryRepositoryService historyRepositoryService = new HistoryRepositoryService(historyCrudRepository);


        FreeSeatManagement freeSeatManagement = FreeSeatManagement.getInstance();
        freeSeatManagement.setHistoryRepositoryService(historyRepositoryService);
        freeSeatManagement.addHistoryEntryList(mockedList);
        assertEquals(freeSeatManagement.getLastHistoryEntryDate(), historyEntryJPA.getDate());
    }
}
