package edu.kit.cm.ms_utlilsation;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/features",glue= "edu/kit/cm/ms_utlilsation/stepDefinitions")
public class StepDefinitonRunner {
	
}
