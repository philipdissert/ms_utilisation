package edu.kit.cm.ms_utlilsation;

import edu.kit.cm.ms_utilisation.Utilization.Domain.PoolElementState;
import edu.kit.cm.ms_utilisation.Utilization.Service.UtilizationAdapter;

import io.swagger.models.auth.In;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;


public class UtilizationServiceTest {


    @Test
    public void utilizationTest() throws JSONException {
        UtilizationAdapter utilizationAdapter = UtilizationAdapter.getInstance();
        String jsonString = "[{'id':0,'type':'PC'}]";
        String jsonStringUpdate = "{'data':[{'id':0,'state':1,'type':'PC'}]}";
        utilizationAdapter.createPoolElementHashMap(new JSONArray(jsonString));
        assertEquals(utilizationAdapter.getCurrentState().getString("data"),new JSONObject("{'data':[{'id':0,'state':0,'type':'PC'}]}").getString("data"));
        utilizationAdapter.updateStates(new JSONObject(jsonStringUpdate));
        assertEquals(utilizationAdapter.getCurrentState().getString("data"),new JSONObject(jsonStringUpdate).getString("data"));
        utilizationAdapter.updateSeats();
        assertEquals(utilizationAdapter.getMaxWorkspace(),1);
        assertEquals(utilizationAdapter.getFreeWorkspaces(),1);
        assertEquals(utilizationAdapter.getOccupiedWorkspaces(),0);
        assertEquals(utilizationAdapter.getPercentageFree(),100.0,0.01);
        assertEquals(utilizationAdapter.getPercentageOccupied(),0.0,0.01);
        HashMap<Integer, PoolElementState> pes = new HashMap<>();
        PoolElementState poolElementState = new PoolElementState(0,"PC");
        poolElementState.setState(1);
        pes.put(0,poolElementState);
        assertEquals(pes.toString(), utilizationAdapter.getPoolElementHashMap().toString());
    }
}
