package edu.kit.cm.WorkspaceManagement;

import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.RepositoryThread;
import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.persistence.HistoryCrudRepository;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.HistoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThreadManager {

    @Autowired
    HistoryCrudRepository historyCrudRepository;

    @Autowired
    public ThreadManager(HistoryCrudRepository historyCrudRepository) {
        this.historyCrudRepository = historyCrudRepository;

        RepositoryThread rt = new RepositoryThread(historyCrudRepository);
        rt.start();
    }

}
