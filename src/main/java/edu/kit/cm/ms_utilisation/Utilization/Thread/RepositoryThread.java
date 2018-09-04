package edu.kit.cm.ms_utilisation.Utilization.Thread;

import edu.kit.cm.ms_utilisation.Utilization.Infrastructure.FreeSeats;
import edu.kit.cm.ms_utilisation.Utilization.Infrastructure.persistence.HistoryCrudRepository;
import edu.kit.cm.ms_utilisation.Utilization.Service.Prediction.PredictionAlgorithmAvg;
import edu.kit.cm.ms_utilisation.Utilization.Service.HistoryRepositoryService;
import edu.kit.cm.ms_utilisation.Utilization.Service.Prediction.Prediction;

import java.time.LocalDateTime;

public class RepositoryThread extends Thread{

    private HistoryRepositoryService historyRepositoryService;
    private FreeSeats freeSeats;

    public RepositoryThread(HistoryCrudRepository historyCrudRepository) {
        super();
        this.historyRepositoryService = new HistoryRepositoryService(historyCrudRepository);
        freeSeats = new FreeSeats();
    }

    @Override
    public void run() {
       Prediction prediction = PredictionAlgorithmAvg.getInstance();
       prediction.setHistoryRepositoryService(historyRepositoryService);

        while(true) {
            update();
            prediction.update();
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        LocalDateTime date = historyRepositoryService.getLatestDate();
        if (date == null) {
            date = LocalDateTime.of(0,1,1,0,0,0);
        }
        freeSeats.getSeats(date).forEach(x -> {
            historyRepositoryService.createEntity(x);
        });
    }
}
