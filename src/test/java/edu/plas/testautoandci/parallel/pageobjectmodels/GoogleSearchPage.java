package edu.plas.testautoandci.parallel.pageobjectmodels;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GoogleSearchPage {
    private static WebDriver driver;

    public GoogleSearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void search(String searchText) {
        driver.findElement(By.id("lst-ib")).sendKeys(searchText);
        driver.findElement(By.name("btnG")).click();
    }

    public String getStatsText() {
        return driver.findElement(By.id("resultStats")).getText();
    }

    public int getNumberOfSearchResults() {
        return driver.findElements(By.cssSelector("#search .g")).size();
    }

    private WebElement getFlag() {
        return driver.findElement(By.cssSelector("#media_result_group .bicc img"));
    }

    public boolean isFlagDisplayed() {
        return getFlag().isDisplayed();
    }

    public String getFlagTitleAttribute() {
        return getFlag().getAttribute("title");
    }

    public int getNumberOfImageSearchResults() {
        return driver.findElements(By.cssSelector("#rg_s .rg_di.rg_el.ivg-i")).size();
    }
}
