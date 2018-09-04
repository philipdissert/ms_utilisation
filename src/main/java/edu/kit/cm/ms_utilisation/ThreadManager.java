package edu.kit.cm.ms_utilisation;

import edu.kit.cm.ms_utilisation.Utilization.Thread.RepositoryThread;
import edu.kit.cm.ms_utilisation.Utilization.Infrastructure.persistence.HistoryCrudRepository;
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
