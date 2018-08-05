package edu.kit.cm.PoolManagement;

import edu.kit.cm.WorkspaceManagement.Utilization.Infrastructure.FreeSeats;
import edu.kit.cm.WorkspaceManagement.Utilization.Service.UtilizationAdapter;
import org.json.JSONException;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertTrue;

public class InfrastructureTest {
    @Test
    public void utilizationTest() throws ParseException, JSONException {
        FreeSeats computerStateATISAdapter = new FreeSeats();
        try {
        	/*
            computerStateATISAdapter.generatePoolElementsFromWorkspace();
            computerStateATISAdapter.updateComputersWithStatesFromATIS();
            computerStateATISAdapter.updateFreeSeatsFromATIS();
            UtilizationAdapter utilizationAdapter = UtilizationAdapter.getInstance();
            assertTrue(utilizationAdapter.getPercentageFree()>=0.0);
            assertTrue(utilizationAdapter.getPercentageFree()<=100.0);
            assertTrue(utilizationAdapter.getPercentageOccupied()>=0.0);
            assertTrue(utilizationAdapter.getPercentageOccupied()<=100.0);
            */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
