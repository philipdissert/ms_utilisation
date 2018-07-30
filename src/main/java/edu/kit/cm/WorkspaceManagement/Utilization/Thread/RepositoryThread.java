package edu.kit.cm.WorkspaceManagement.Utilization.Thread;

import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.ComputerStateATISAdapter;
import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence.HistoryCrudRepository;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.DoSomeMagicAvg;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.HistoryRepositoryService;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.PredictionAlgorithm;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class RepositoryThread extends Thread{

    private HistoryRepositoryService historyRepositoryService;

    public RepositoryThread(HistoryCrudRepository historyCrudRepository) {
        super();
        this.historyRepositoryService = new HistoryRepositoryService(historyCrudRepository);
    }

    @Override
    public void run() {
       DoSomeMagicAvg doSomeMagicAvg = DoSomeMagicAvg.getInstance();
       doSomeMagicAvg.setHistoryRepositoryService(historyRepositoryService);

        while(true) {
            update();
            doSomeMagicAvg.update();
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        ComputerStateATISAdapter csaa = new ComputerStateATISAdapter();
        LocalDateTime date = historyRepositoryService.getLatestDate();
        if (date == null) {
            date = LocalDateTime.of(0,1,1,0,0,0);
        }
        csaa.getSeats(date).forEach(x -> {
            historyRepositoryService.createEntity(x);
        });
    }
}
