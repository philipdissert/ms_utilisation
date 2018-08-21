package edu.kit.cm.WorkspaceManagement.Utilization.Service.Prediction;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PredictionWithTimeDateStamp {

    public PredictionWithTimeDateStamp(int[] prediction, LocalDate dateStamp) {
        this.prediction = prediction;
        this.dateStamp = dateStamp;
    }

    private int [] prediction;
    LocalDate dateStamp;
}
