package edu.kit.cm.PoolManagement;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import edu.kit.cm.WorkspaceManagement.WorkspaceManagementApplication;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/features",glue="edu/kit/cm/PoolManagement/stepDefinitions")
public class StepDefinitonRunner {
	
}
