package edu.kit.cm.WorkspaceManagement.Utilization.Service.Prediction;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.HistoryRepositoryService;
import org.apache.commons.math3.stat.regression.SimpleRegression;

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

    protected abstract int[] getArrayToday(List<List<HistoryEntry>> list);
    protected abstract int[][] getArrayPast(List<List<List<HistoryEntry>>> listPast);

    @Override
    public void update() {
        for(DayOfWeek dayOfWeek: DayOfWeek.values()) {
            if (dayOfWeek.equals(LocalDate.now().getDayOfWeek())) {
                prediction.put(dayOfWeek,updateToday(dayOfWeek));
                continue;
            }
            prediction.put(dayOfWeek,update(dayOfWeek));
        }
    }

    public int[] updateToday(DayOfWeek dayOfWeek) {
        LocalTime[] blocks = getBlocks(dayOfWeek);
        LocalTime time = LocalTime.now();
        int[] predictionDay = new int[blocks.length - 1];
        int[] simplePredictionDay = update(dayOfWeek);
        int[] today = getArrayToday(getHistoryFromToday());

        double c = 0;

        for(int i = 0; i < predictionDay.length; i++) {

            if (time.isBefore(blocks[0])){
                predictionDay = update(dayOfWeek);
                break;
            }
            if(time.isBefore(blocks[i + 1])) {
                if(time.isAfter(blocks[i])) c = (double)predictionDay[i-1]/simplePredictionDay[i-1];
                predictionDay[i] = (int) ((double)simplePredictionDay[i] + (double)simplePredictionDay[i] * c)/2;
            } else {
                predictionDay[i] = today[i];
            }



        }
        return predictionDay;
    }

    private int[] update(DayOfWeek dayOfWeek) {
        int[] predictionDay = new int[getBlocks(dayOfWeek).length - 1];
        int[][] past = getArrayPast(getHistoryfromPastWeeks(dayOfWeek));

        for (int i = 0; i < past.length; i++) {
            SimpleRegression simpleRegression = new SimpleRegression();
            for(int k = 0; k < past[i].length; k++) {
                simpleRegression.addData(k, past[i][k]);
            }
            predictionDay[i] = (int) simpleRegression.predict(past[i].length);
        }
        return predictionDay;
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
        LocalDate date = LocalDate.now().with(dayOfWeek).minusDays(60);
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
        LocalDate date = LocalDate.now().minusDays(60);
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
