package edu.kit.cm.WorkspaceManagement.Utilization.Service;

import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.CurrentUtilization;
import edu.kit.cm.WorkspaceManagement.Utilization.Domain.History;
import edu.kit.cm.WorkspaceManagement.Utilization.Domain.PoolElementState;

public class UtilizationAdapter {

    private static UtilizationAdapter utilizationAdapter = new UtilizationAdapter();

    private HashMap<Integer, PoolElementState> poolElementHashMap;
    private History history;
    private CurrentUtilization currentUtilization;

    private UtilizationAdapter() {
        history = new History();
    }

    public static UtilizationAdapter getInstance() {
        return utilizationAdapter;
    }

    public void createPoolElementHashMap(JSONObject poolElementList) {
        poolElementHashMap = new HashMap<Integer, PoolElementState>();
        try {
            JSONArray jsonArray = poolElementList.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                int id = jsonArray.getJSONObject(i).getInt("id");
                String type = jsonArray.getJSONObject(i).getString("type");
                poolElementHashMap.put(id, new PoolElementState(id, type));
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

    public void updateSeats(Date date, int freeSeats, int maxATISPcs) {
        history.getUtilizationList().put(date, currentUtilization);
        currentUtilization = new CurrentUtilization(freeSeats, maxATISPcs - freeSeats, maxATISPcs);
    }

    public JSONObject getCurrentUtilization() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("free", currentUtilization.getFreeWorkspaces());
            jsonObject.put("occupied", currentUtilization.getOccupiedWorkspaces());
            jsonObject.put("percentageFree", currentUtilization.getPercentageFree());
            jsonObject.put("percentageOccupied", currentUtilization.getPercentageOccupied());
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

}
