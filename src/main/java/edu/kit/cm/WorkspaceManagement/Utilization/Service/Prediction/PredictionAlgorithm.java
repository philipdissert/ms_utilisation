package edu.kit.cm.WorkspaceManagement.Utilization.Service.Prediction;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.HistoryRepositoryService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class PredictionAlgorithm implements Prediction{
    protected HashMap<DayOfWeek,int[]> prediction;
    protected HistoryRepositoryService historyRepositoryService;
    protected int iterations = 3;


    protected PredictionAlgorithm(){
        prediction = new HashMap<>();
    }

    protected abstract int doSomeMagic(List<List<HistoryEntry>> listDays);
    protected abstract int[] doSomeMagicToday(List<List<List<HistoryEntry>>> listPast, List<List<HistoryEntry>> listToday);

    @Override
    public void update() {
        for(DayOfWeek dayOfWeek: DayOfWeek.values()) {
            if (dayOfWeek.equals(LocalDate.now().getDayOfWeek())) {
                this.updateToday();
                continue;
            }
            this.update(dayOfWeek);
        }
    }

    @Override
    public void updateToday() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        List<List<List<HistoryEntry>>> listOfBlocks = getHistoryfromPastWeeks(today);
        List<List<HistoryEntry>> listToday = getHistoryFromToday();
        int[] predictionDay = doSomeMagicToday(listOfBlocks, listToday);
        prediction.put(today, predictionDay);
    }

    private void update(DayOfWeek dayOfWeek) {
        int[] predictionDay = new int[getBlocks(dayOfWeek).length - 1];
        List<List<List<HistoryEntry>>> listOfBlocks = getHistoryfromPastWeeks(dayOfWeek);

        for (int i = 0; i < listOfBlocks.size(); i++) {
            predictionDay[i] = doSomeMagic(listOfBlocks.get(i));
        }
        prediction.put(dayOfWeek,predictionDay);
    }


    @Override
    public int[] getPrediction(DayOfWeek dayOfWeek) {
        return prediction.get(dayOfWeek);
    }


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

    @Override
    public void setHistoryRepositoryService(HistoryRepositoryService historyRepositoryService) {
        this.historyRepositoryService = historyRepositoryService;
    }

    private List<List<List<HistoryEntry>>> getHistoryfromPastWeeks(DayOfWeek dayOfWeek) {
        List<List<List<HistoryEntry>>> listOfBlocks = new ArrayList<List<List<HistoryEntry>>>();

        LocalTime[] time = getBlocks(dayOfWeek);
        LocalDate date = LocalDate.now().with(dayOfWeek);
        if(date.isAfter(LocalDate.now())) {
            date.minusDays(7);
        }

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
        return listOfBlocks;
    }

    private List<List<HistoryEntry>> getHistoryFromToday() {
        List<List<HistoryEntry>> listOfBlocks = new ArrayList<>();
        LocalDate date = LocalDate.now();
        LocalTime[] time = getBlocks(date.getDayOfWeek());
        for(int i = 1; i < time.length; i++) {
            LocalDateTime from = LocalDateTime.of(date, time[i - 1]);
            LocalDateTime to = LocalDateTime.of(date, time[i]);

            if (to.isBefore(LocalDateTime.now())) {
                List<HistoryEntry> historyEntries = historyRepositoryService.findAllBetween(from,to);
                listOfBlocks.add(historyEntries);
            }
        }
        return listOfBlocks;
    }
}
