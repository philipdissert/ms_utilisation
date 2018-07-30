package edu.kit.cm.WorkspaceManagement.Utilization.Service;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class PredictionAlgorithm {
    protected HashMap<DayOfWeek,int[]> prediction;
    protected HistoryRepositoryService historyRepositoryService;
    protected int iterations = 3;


    protected PredictionAlgorithm(){
        prediction = new HashMap<>();
    }


    public void update(DayOfWeek dayOfWeek) {
        List<List<List<HistoryEntry>>> listOfBlocks = new ArrayList<List<List<HistoryEntry>>>();
        LocalTime[] time = getBlocks(dayOfWeek);
        int[] predictionDay = new int[time.length - 1];
        LocalDate date = LocalDate.now().with(dayOfWeek);
        for(int i = 1; i < time.length; i++) {
            LocalDateTime from = LocalDateTime.of(date, time[i - 1]);
            LocalDateTime to = LocalDateTime.of(date, time[i]);
            List<List<HistoryEntry>> listOfDays = new ArrayList<List<HistoryEntry>>();
            for (int k = 0; k < iterations; k++) {
                List<HistoryEntry> historyEntries = historyRepositoryService.findAllBetween(from.minusDays(7*k),to.minusDays(7*k));
                listOfDays.add(historyEntries);
            }
            listOfBlocks.add(listOfDays);
        }
        for (int i = 0; i < listOfBlocks.size(); i++) {
            predictionDay[i] = doSomeMagic(listOfBlocks.get(i));
        }
        prediction.put(dayOfWeek,predictionDay);

    }

    public void update() {
        for(DayOfWeek dayOfWeek: DayOfWeek.values()) {
            this.update(dayOfWeek);
        }
    }

    public int[] getPrediction(DayOfWeek dayOfWeek) {
        return prediction.get(dayOfWeek);
    }

    protected abstract int doSomeMagic(List<List<HistoryEntry>> listOfDays);

    private LocalTime[] getBlocks(DayOfWeek dayOfWeek) {
        return new LocalTime[]{ LocalTime.of(8,0),
                                LocalTime.of(9,45),
                                LocalTime.of(11,30),
                                LocalTime.of(14,0),
                                LocalTime.of(15,45),
                                LocalTime.of(17,30),
                                LocalTime.of(19,0),
                                LocalTime.of(22,0)};
    }

    public void setHistoryRepositoryService(HistoryRepositoryService historyRepositoryService) {
        this.historyRepositoryService = historyRepositoryService;
    }
}
