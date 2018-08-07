package edu.kit.cm.WorkspaceManagement.Utilization.Service.Prediction;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DoSomeMagicAvg extends PredictionAlgorithm {
    private static DoSomeMagicAvg doSomeMagicAvg = new DoSomeMagicAvg();

    private DoSomeMagicAvg() {
        super();
    }

    public static DoSomeMagicAvg getInstance() {
       return doSomeMagicAvg;
    }

    protected int doSomeMagic(List<List<HistoryEntry>> listOfDays) {
       int count = 0;
       int sum = 0;
       for (List<HistoryEntry> list: listOfDays) {
           for(HistoryEntry historyEntry: list) {
               sum += historyEntry.getFreeSeats();
           }
           count += list.size();
       }
       if (count == 0) {
           return -1;
       }
       return sum / count;
    }

    protected int[] doSomeMagicToday(List<List<List<HistoryEntry>>> listPast, List<List<HistoryEntry>> listToday) {
        int[] prediction = new int[listPast.size()];
        int[][] array = new int[listPast.size() + 1][listPast.get(0).size()];

        for(int k = 0; k < listToday.size(); k++) {
            int sum = 0;
            for(HistoryEntry historyEntry: listToday.get(k)) {
                sum+=historyEntry.getFreeSeats();
            }
            if(listToday.get(k).size() != 0) {
                sum /= listPast.get(k).size();
            }

            array[0][k] = sum;
        }

        for(int i = 1; i < listPast.size() + 1; i++) {
            for(int k = 0; k < listPast.get(i-1).size(); k++) {
                int sum = 0;
                for(HistoryEntry historyEntry: listPast.get(i-1).get(k)) {
                    sum+=historyEntry.getFreeSeats();
                }
                if(listPast.get(i-1).get(k).size() != 0) {
                    sum /= listPast.get(i-1).get(k).size();
                }

                array[i][k] = sum;
            }
        }




        return prediction;
    }

}
