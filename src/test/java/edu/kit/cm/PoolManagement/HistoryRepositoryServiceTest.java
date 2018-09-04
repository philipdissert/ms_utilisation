package edu.kit.cm.PoolManagement;

import edu.kit.cm.ms_utilisation.Utilization.Infrastructure.persistence.HistoryCrudRepository;
import edu.kit.cm.ms_utilisation.Utilization.Infrastructure.persistence.HistoryEntryJPA;
import edu.kit.cm.ms_utilisation.Utilization.Service.HistoryRepositoryService;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HistoryRepositoryServiceTest {

    ArrayList<HistoryEntryJPA> mockedList;

    @Before
    public void before() {
        HistoryEntryJPA lastEntry = new HistoryEntryJPA(10, LocalDateTime.of(2013,5,6+21,12,30,10));
        mockedList = new ArrayList();
        mockedList.add(lastEntry);
        mockedList.add(new HistoryEntryJPA(20, LocalDateTime.of(2013,5,6+14,12,30,10)));
        mockedList.add(new HistoryEntryJPA(30, LocalDateTime.of(2013,5,6+7,12,30,10)));

        mockedList.add(new HistoryEntryJPA(70, LocalDateTime.of(2013,5,7,12,30,10)));
        mockedList.add(new HistoryEntryJPA(40, LocalDateTime.of(2013,5,6,12,30,10)));

        mockedList.add(new HistoryEntryJPA(30, LocalDateTime.of(2013,5,5,12,30,10)));
        mockedList.add(new HistoryEntryJPA(40, LocalDateTime.of(2013,5,4,12,30,10)));
        mockedList.add(new HistoryEntryJPA(50, LocalDateTime.of(2013,5,3,12,30,10)));
        mockedList.add(new HistoryEntryJPA(60, LocalDateTime.of(2013,5,2,12,30,10)));
        mockedList.add(new HistoryEntryJPA(10, LocalDateTime.of(2013,5,1,12,30,10)));
    }


    //test
    @Test
    public void test1(){
        HistoryCrudRepository historyCrudRepositoryMock = mock(HistoryCrudRepository.class);

        when(historyCrudRepositoryMock.findFirstByDateIsNotNullOrderByDateDesc()).thenReturn(mockedList.get(0));
        when(historyCrudRepositoryMock.findByDateIsBetween(any(),any())).thenReturn(mockedList);
        when(historyCrudRepositoryMock.findAllByDateIsNotNullOrderByDateDesc()).thenReturn(mockedList);
        when(historyCrudRepositoryMock.findAll()).thenReturn(mockedList);

        HistoryRepositoryService historyRepositoryService = new HistoryRepositoryService(historyCrudRepositoryMock);
        assertEquals(historyRepositoryService.getLatestDate(),historyCrudRepositoryMock.findFirstByDateIsNotNullOrderByDateDesc().getDate());
        historyRepositoryService.findAllOnDayOfWeek(DayOfWeek.MONDAY).forEach((x)-> {
            assertEquals(x.getDate().getDayOfWeek(),DayOfWeek.MONDAY);
        });
        historyRepositoryService.findAllOnDayOfWeek(DayOfWeek.WEDNESDAY).forEach((x)-> {
            assertEquals(x.getDate().getDayOfWeek(),DayOfWeek.WEDNESDAY);
        });
        historyRepositoryService.findAllOnDayOfWeek(DayOfWeek.SUNDAY).forEach((x)-> {
            assertEquals(x.getDate().getDayOfWeek(),DayOfWeek.SUNDAY);
        });

        assertEquals(historyRepositoryService.findAllBetween(any(),any()).get(0).getDate(), mockedList.get(0).getDate());
        assertEquals(historyRepositoryService.findAllEntitys().get(3).getDate(), mockedList.get(3).getDate());



    }
}
