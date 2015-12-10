package edu.plas.testautoandci.parallel.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/edu/plas/testautoandci/parallel/features",
        glue = "edu.plas.testautoandci",
        tags = {"@imageSearch"},
        plugin = {"html:reports/imageSearch"})
public class GoogleImageSearchRunner {
}