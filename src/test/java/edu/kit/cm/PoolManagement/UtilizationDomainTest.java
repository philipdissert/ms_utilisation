package edu.kit.cm.PoolManagement;

import edu.kit.cm.WorkspaceManagement.Utilization.Domain.CurrentUtilization;
import edu.kit.cm.WorkspaceManagement.Utilization.Domain.History;
import edu.kit.cm.WorkspaceManagement.Utilization.Domain.PoolElementState;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UtilizationDomainTest {
    @Test
    public void currentUtilizationDomainTest() throws ParseException {
        CurrentUtilization currentUtilization = new CurrentUtilization(80, 20, 100);
        assertEquals(currentUtilization.getFreeWorkspaces(), 80);
        assertEquals(currentUtilization.getOccupiedWorkspaces(), 20);
        assertEquals(currentUtilization.getPercentageFree(), 80, 0.01);
        assertEquals(currentUtilization.getPercentageOccupied(), 20, 0.01);

        History history = new History();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date d = simpleDateFormat.parse("11.11.2010");
        history.getUtilizationList().put(d, currentUtilization);
        assertEquals(history.getUtilizationList().get(d).getFreeWorkspaces(),80);
        assertNotEquals(history.getUtilizationList().get(d).getFreeWorkspaces(), 10);

        PoolElementState poolElementState1 = new PoolElementState(1,"PC");

        assertEquals(poolElementState1.getState(),0);
        poolElementState1.setState(2);
        assertEquals(poolElementState1.getState(),2);
        assertEquals(poolElementState1.getType(), "PC");
        assertEquals(poolElementState1.getId(),1);
    }
}
