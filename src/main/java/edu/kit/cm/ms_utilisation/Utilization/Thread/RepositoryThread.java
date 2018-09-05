package edu.kit.cm.ms_utilisation.Utilization.Thread;

import edu.kit.cm.ms_utilisation.Utilization.Infrastructure.persistence.HistoryCrudRepository;
import edu.kit.cm.ms_utilisation.Utilization.Service.FreeSeatManagement;
import edu.kit.cm.ms_utilisation.Utilization.Service.Prediction.PredictionAlgorithmAvg;
import edu.kit.cm.ms_utilisation.Utilization.Service.HistoryRepositoryService;
import edu.kit.cm.ms_utilisation.Utilization.Service.Prediction.Prediction;

public class RepositoryThread extends Thread{

    private HistoryRepositoryService historyRepositoryService;

    public RepositoryThread(HistoryCrudRepository historyCrudRepository) {
        super();
        this.historyRepositoryService = new HistoryRepositoryService(historyCrudRepository);
    }

    @Override
    public void run() {
        Prediction prediction = PredictionAlgorithmAvg.getInstance();
        prediction.setHistoryRepositoryService(historyRepositoryService);
        FreeSeatManagement freeSeatManagement = FreeSeatManagement.getInstance();
        freeSeatManagement.setHistoryRepositoryService(historyRepositoryService);

        while(true) {
            prediction.update();
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
