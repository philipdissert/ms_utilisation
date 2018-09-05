package edu.kit.cm.ms_utilisation.Utilization.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

import edu.kit.cm.ms_utilisation.Utilization.Service.Prediction.PredictionAlgorithmAvg;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.cm.ms_utilisation.Utilization.Domain.CurrentUtilization;
import edu.kit.cm.ms_utilisation.Utilization.Domain.PoolElementState;

public class UtilizationAdapter {

    private static UtilizationAdapter utilizationAdapter = new UtilizationAdapter();

    @Getter@Setter
    private HashMap<Integer, PoolElementState> poolElementHashMap;
    @Getter@Setter
    private CurrentUtilization currentUtilization;

    private UtilizationAdapter() {
    }

    public static UtilizationAdapter getInstance() {
        return utilizationAdapter;
    }

    public void createPoolElementHashMap(JSONArray jsonArray) {
        poolElementHashMap = new HashMap<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                int id = jsonArray.getJSONObject(i).getInt("id");
                String type = jsonArray.getJSONObject(i).getString("type");
                if("PC".equals(type)) {
                    poolElementHashMap.put(id, new PoolElementState(id, type));
                }
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException();
        }
    }

    public void updateStates(JSONObject poolData) {
        try {
            JSONArray jsonArray = poolData.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                String idString = jsonArray.getJSONObject(i).getString("id");
                int id = -1;
                try {
                    id = Integer.parseInt(idString);
                } catch (NumberFormatException e) {
                }

                int state = jsonArray.getJSONObject(i).getInt("state");
                if (poolElementHashMap.get(id) != null) {
                    poolElementHashMap.get(id).setState(state);
                }
            }

        } catch (JSONException e) {
            throw new IllegalArgumentException();
        }
    }

    public void updateSeats() {
        int freeCount = 0;
        int occupiedCount = 0;

        for(PoolElementState y : poolElementHashMap.values()) {
            if(y.getState()==1 || y.getState()==2) {
                freeCount++;
            } else if(y.getState()==3 || y.getState()==4) {
                occupiedCount++;
            }
        }
        currentUtilization = new CurrentUtilization(freeCount,occupiedCount,poolElementHashMap.size());
    }

    public JSONObject getCurrentUtilization() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("free", currentUtilization.getFreeWorkspaces());
            jsonObject.put("occupied", currentUtilization.getOccupiedWorkspaces());
            jsonObject.put("percentageFree", currentUtilization.getPercentageFree());
            jsonObject.put("percentageOccupied", currentUtilization.getPercentageOccupied());
            jsonObject.put("max", currentUtilization.getMaxWorkspaces());
            return jsonObject;
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    public int getFreeWorkspaces() {
        return currentUtilization.getFreeWorkspaces();
    }

    public int getOccupiedWorkspaces() {
        return currentUtilization.getOccupiedWorkspaces();
    }

    public double getPercentageFree() {
        return currentUtilization.getPercentageFree();
    }

    public double getPercentageOccupied() {
        return currentUtilization.getPercentageOccupied();
    }

    public int getMaxWorkspace() {
        return currentUtilization.getMaxWorkspaces();
    }

    public JSONObject getCurrentState() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        poolElementHashMap.forEach((id, element) -> {
            JSONObject jsonObjectEntry = new JSONObject();
            try {
                jsonObjectEntry.put("id", id);
                jsonObjectEntry.put("type", element.getType());
                jsonObjectEntry.put("state", element.getState());
            } catch (JSONException e) {
            }
            jsonArray.put(jsonObjectEntry);
        });
        try {
            jsonObject.put("data", jsonArray);
        } catch (JSONException e) {

        }
        return jsonObject;
    }

    public int[] getPredictionAtWeekDay(String dayOfWeek) {
        return PredictionAlgorithmAvg.getInstance().getPrediction(DayOfWeek.valueOf(dayOfWeek));
    }

    public int[] getPredictionAtDate(String date) {
        return PredictionAlgorithmAvg.getInstance().getPrediction(parseStringDateOnControllerToDate(date));

    }

    /**
     * Parse 20050131 to LocalDate(2005-01-32)
     * @param date
     * @return
     */
    private LocalDate parseStringDateOnControllerToDate(String date) {
        return LocalDate.of(
                Integer.valueOf(date.substring(0,4)),
                Integer.valueOf(date.substring(4,6)),
                Integer.valueOf(date.substring(6,8)));
    }
}
