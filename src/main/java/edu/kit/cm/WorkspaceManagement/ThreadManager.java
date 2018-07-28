package edu.kit.cm.WorkspaceManagement;

import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence.HistoryEntryJPA;
import edu.kit.cm.WorkspaceManagement.Utilization.Thread.RepositoryThread;
import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence.HistoryCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ThreadManager {

    @Autowired
    HistoryCrudRepository historyCrudRepository;

    @Autowired
    public ThreadManager(HistoryCrudRepository historyCrudRepository) {
        this.historyCrudRepository = historyCrudRepository;
        System.out.println(historyCrudRepository);
        RepositoryThread rt = new RepositoryThread(historyCrudRepository);
        rt.start();

    }

}
