package edu.kit.cm.WorkspaceManagement.Utilization.Thread;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;
import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.ComputerStateATISAdapter;
import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence.HistoryCrudRepository;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.HistoryRepositoryService;

import java.time.LocalDateTime;

public class RepositoryThread extends Thread{

    private HistoryRepositoryService historyRepositoryService;

    public RepositoryThread(HistoryCrudRepository historyCrudRepository) {
        super();
        this.historyRepositoryService = new HistoryRepositoryService(historyCrudRepository);
    }

    @Override
    public void run() {
        while(true) {
            update();
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


//
//        historyRepositoryService.createEntity(new HistoryEntry(3,LocalDateTime.of(2018,9,6,20,34,54),"PC"));
//        historyRepositoryService.createEntity(new HistoryEntry(3,LocalDateTime.of(2018,10,5,5,3,5),"PC"));
//        historyRepositoryService.createEntity(new HistoryEntry(3,LocalDateTime.of(2017,9,6,20,34,54),"PC"));
//        historyRepositoryService.findAllEntitys().forEach(x->{
//            System.out.println(x.toString());
//        });
//        System.out.println(historyRepositoryService.getMaxDate());
    }

    private void update() {
        ComputerStateATISAdapter csaa = new ComputerStateATISAdapter();
        csaa.getSeats(historyRepositoryService.getMaxDate()).forEach(x -> {
            historyRepositoryService.createEntity(x);
        });
    }
}
