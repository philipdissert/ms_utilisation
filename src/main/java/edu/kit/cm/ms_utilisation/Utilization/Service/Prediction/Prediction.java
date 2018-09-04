package edu.kit.cm.ms_utilisation.Utilization.Service.Prediction;

import edu.kit.cm.ms_utilisation.Utilization.Service.HistoryRepositoryService;

import java.time.DayOfWeek;
import java.time.LocalDate;

public interface Prediction {


    public void update();

    public int[] getPrediction(DayOfWeek dayOfWeek);
    public int[] getPrediction(LocalDate localDate);

    public void setHistoryRepositoryService(HistoryRepositoryService historyRepositoryService);

}
