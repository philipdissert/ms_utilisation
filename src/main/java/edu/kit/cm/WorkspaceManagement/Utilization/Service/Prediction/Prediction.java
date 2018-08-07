package edu.kit.cm.WorkspaceManagement.Utilization.Service.Prediction;

import edu.kit.cm.WorkspaceManagement.Utilization.Service.HistoryRepositoryService;

import java.time.DayOfWeek;

public interface Prediction {


    public void update();

    public void updateToday();

    public int[] getPrediction(DayOfWeek dayOfWeek);

    public void setHistoryRepositoryService(HistoryRepositoryService historyRepositoryService);

}
