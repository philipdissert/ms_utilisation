package edu.kit.cm.PoolManagement.stepDefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.kit.cm.ms_utilisation.Utilization.Domain.PoolElementState;
import edu.kit.cm.ms_utilisation.Utilization.Service.UtilizationAdapter;


import static org.junit.Assert.assertEquals;

import java.util.HashMap;

public class FeatureTest {

	private int startOccupied;


	@Given("^(\\d+) (.*) are/is currently (\\d+) Linux occupied$")
	public void computer_is_currently_state(int pcCount, String type, int state) throws Throwable {
		startOccupied = pcCount;
		UtilizationAdapter utilizationAdapter = UtilizationAdapter.getInstance();
		HashMap<Integer,PoolElementState> poolElementStateHashMap = new HashMap();
		for(int i = 0; i<pcCount; i++) {
			PoolElementState poolElementState = new PoolElementState(i, type);
			poolElementState.setState(state);
			poolElementStateHashMap.put(i,poolElementState);
		}
		utilizationAdapter.setPoolElementHashMap(poolElementStateHashMap);
	}

	@When("^(\\d+) PC change to (\\d+) Linux free$")
	public void the_PC_change_to_Linux_free(int changes, int state) throws Throwable {
		UtilizationAdapter utilizationAdapter = UtilizationAdapter.getInstance();
		for(int i=0;i<changes;i++) {
			utilizationAdapter.getPoolElementHashMap().get(i).setState(state);
		}
	}

	@Then("^(\\d+) (.*) are/is currently (\\d+) Linux free$")
	public void pc_are_is_currently_Linux_free(int nr, String type, int state) throws Throwable {
		UtilizationAdapter utilizationAdapter = UtilizationAdapter.getInstance();
		int count = 0;
		for(PoolElementState poolElementState : utilizationAdapter.getPoolElementHashMap().values()) {
			if(poolElementState.getState() == state) {
				count++;
			}
			assertEquals(poolElementState.getType(), type);

		}
		assertEquals(nr,count);
	}

	@Then("^(\\d+) (.*) less are/is (\\d+) occupied$")
	public void pc_less_are_is_occupied(int less, String type, int state) throws Throwable {
		UtilizationAdapter utilizationAdapter = UtilizationAdapter.getInstance();
		int count =0;
		for(PoolElementState poolElementState : utilizationAdapter.getPoolElementHashMap().values()) {
			if(poolElementState.getState()==state
					&& poolElementState.getType().equals(type)) {
				count++;
			}
		}
		assertEquals(count,startOccupied-less);
	}
}
