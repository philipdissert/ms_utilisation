package edu.kit.cm.WorkspaceManagement.Utilization.Service;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DoSomeMagicAvg extends PredictionAlgorithm{
    private static DoSomeMagicAvg doSomeMagicAvg = new DoSomeMagicAvg();

    @Autowired
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

}
