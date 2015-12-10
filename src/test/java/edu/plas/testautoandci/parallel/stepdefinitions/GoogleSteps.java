package edu.plas.testautoandci.parallel.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.plas.testautoandci.parallel.driver.Driver;
import edu.plas.testautoandci.parallel.pageobjectmodels.GoogleSearchPage;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class GoogleSteps {
    private GoogleSearchPage googleSearchPage;
    private WebDriver driver;

    public GoogleSteps(Driver driver) {
        this.driver = driver;
        googleSearchPage = new GoogleSearchPage(driver);
    }

    @Given("^I navigate to (.*)$")
    public void iNavigateTo(String site) {
        driver.get(site);
    }

    @When("^I search for '(.*)' on Google Search$")
    public void iSearchForSearchText(String searchText) {
        googleSearchPage.search(searchText);
    }

    @Then("^the Google stats tab is displayed$")
    public void theGoogleStatsTabIsDisplated() {
        String statsText = googleSearchPage.getStatsText();
        assertTrue(statsText.contains("About"));
        assertTrue(statsText.contains("results"));
        assertTrue(statsText.contains("seconds"));
    }

    @Then("^the Google search results are displayed$")
    public void theGoogleSearchResultsAreDisplayed() {
        assertTrue("Search results should have been shown", googleSearchPage.getNumberOfSearchResults() > 1);
    }

    @Then("^the flag of '(.*)' is displayed$")
    public void theFlagOfCountryIsDisplayed(String country) {
        assertTrue("Flag is not displayed", googleSearchPage.isFlagDisplayed());
        assertTrue("Flag is not displayed", googleSearchPage.getFlagTitleAttribute().toLowerCase().contains(country.toLowerCase()));
    }

    @When("^I search for '(.*)' on Google Images$")
    public void iSearchForTextOnGoogleImages(String searchText) {
        googleSearchPage.search(searchText);
    }

    @Then("^there are '(.*)' images$")
    public void thereAreImages(String occurences) {
        int numberOfImageSearchResults = googleSearchPage.getNumberOfImageSearchResults();
        if ("10 or more".equals(occurences)) {
            assertTrue("There should have been 10 or more images, but there where " + numberOfImageSearchResults, numberOfImageSearchResults >= 10);
        }
    }
}
