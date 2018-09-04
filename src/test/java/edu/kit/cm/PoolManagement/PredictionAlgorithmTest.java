package edu.kit.cm.PoolManagement;

import edu.kit.cm.ms_utilisation.Utilization.Domain.HistoryEntry;
import edu.kit.cm.ms_utilisation.Utilization.Infrastructure.persistence.HistoryCrudRepository;
import edu.kit.cm.ms_utilisation.Utilization.Infrastructure.persistence.HistoryEntryJPA;
import edu.kit.cm.ms_utilisation.Utilization.Service.Prediction.PredictionAlgorithm;
import edu.kit.cm.ms_utilisation.Utilization.Service.Prediction.PredictionAlgorithmAvg;
import edu.kit.cm.ms_utilisation.Utilization.Thread.RepositoryThread;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PredictionAlgorithmTest {

    @Before
    public void start() {

    }

    /**
     * Expects a NullpointerException
     */
    @Test(expected = NullPointerException.class)
    public void DataBaseDoNotRun () {
        PredictionAlgorithm predictionAlgorithm = new PredictionAlgorithm() {
            @Override
            protected int[] getArrayToday(List<List<HistoryEntry>> list) {
                return new int[0];
            }

            @Override
            protected int[][] getArrayPast(List<List<List<HistoryEntry>>> listPast) {
                return new int[0][];
            }
        };
        predictionAlgorithm.update();
    }


    /**
     * Tests the Prediction on a Date with a Mocked HistoryCrudRepository
     */
    @Test
    public void testPredictionOnDate () {
        HistoryCrudRepository historyCrudRepository = mock(HistoryCrudRepository.class);
        ArrayList<HistoryEntryJPA> mockedList = new ArrayList<>();

        HistoryEntryJPA lastEntry = new HistoryEntryJPA(10, LocalDateTime.of(2013,5,6+21,12,30,10));
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

        when(historyCrudRepository.findFirstByDateIsNotNullOrderByDateDesc()).thenReturn(lastEntry);
        when(historyCrudRepository.findAllByDateIsNotNullOrderByDateDesc()).thenReturn(mockedList);

        RepositoryThread rt = new RepositoryThread(historyCrudRepository);
        rt.start();
        PredictionAlgorithmAvg predictionAlgorithmAvg = PredictionAlgorithmAvg.getInstance();
        predictionAlgorithmAvg.update();
        assertEquals(predictionAlgorithmAvg.getPrediction(LocalDate.of(2018,8,27))[0],0);
        assertEquals(predictionAlgorithmAvg.getPrediction(LocalDate.of(2018,8,27))[2],25);
    }


}
