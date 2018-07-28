package edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.HistoryEntry;
import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence.HistoryCrudRepository;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.HistoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class RepositoryThread extends Thread{

    private HistoryAdapter historyAdapter;

    public RepositoryThread(HistoryCrudRepository historyCrudRepository) {
        super();
        this.historyAdapter = new HistoryAdapter(historyCrudRepository);
    }

    @Override
    public void run() {
        System.out.println(historyAdapter.createEntity(new HistoryEntry(3,LocalDateTime.of(1,1,1,1,1,1),"PC")));
        historyAdapter.findAllEntitys().forEach(x->{
            System.out.println(x.toString());
        });
    }
}
