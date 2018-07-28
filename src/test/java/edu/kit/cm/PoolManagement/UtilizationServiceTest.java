package edu.kit.cm.PoolManagement;

import edu.kit.cm.WorkspaceManagement.Utilization.Service.UtilizationAdapter;

import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


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
        //utilizationAdapter.updateStates();
    }
}
