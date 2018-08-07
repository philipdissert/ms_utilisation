package edu.kit.cm.WorkspaceManagement.Utilization.Service.Prediction;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;

import java.util.List;

public class PredictionAlgorithmAvg extends PredictionAlgorithm {
    private static PredictionAlgorithmAvg predictionAlgorithmAvg = new PredictionAlgorithmAvg();

    private PredictionAlgorithmAvg() {
        super();
    }

    public static PredictionAlgorithmAvg getInstance() {
       return predictionAlgorithmAvg;
    }

    protected int[][] getArrayPast(List<List<List<HistoryEntry>>> listPast) {
        int[][] array = new int[listPast.size()][listPast.get(0).size()];

        for(int i = 0; i < listPast.size() ; i++) {
            for(int k = 0; k < listPast.get(i).size(); k++) {
                int sum = 0;
                for(HistoryEntry historyEntry: listPast.get(i).get(k)) {
                    sum+=historyEntry.getFreeSeats();
                }
                if(listPast.get(i).get(k).size() != 0) {
                    sum /= listPast.get(i).get(k).size();
                }

                array[i][k] = sum;
            }
        }
        return array;
    }

    protected int[] getArrayToday(List<List<HistoryEntry>> listToday) {
        int[] array  = new int[listToday.size()];

        for(int k = 0; k < listToday.size(); k++) {
            int sum = 0;
            for(HistoryEntry historyEntry: listToday.get(k)) {
                sum+=historyEntry.getFreeSeats();
            }
            if(listToday.get(k).size() != 0) {
                sum /= listToday.get(k).size();
            }
            array[k] = sum;
        }

        return array;
    }

}
