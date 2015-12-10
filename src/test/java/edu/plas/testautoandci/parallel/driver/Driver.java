package edu.plas.testautoandci.parallel.driver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Driver extends EventFiringWebDriver {
    private static final WebDriver DRIVER;
    private static boolean imagesCleaned = false;

    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            try {
                DRIVER.quit();
            } catch (UnreachableBrowserException ube) {
                // do nothing
            }
        }
    };

    static {
        DRIVER = new FirefoxDriver();
        DRIVER.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    public Driver() {
        super(DRIVER);
    }

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }

    @Before
    public void deleteAllCookies() {
        manage().deleteAllCookies();

        // Delete all screen shots from previous execution
        // THIS SHOULD BE EXECUTED ONLY ONCE
        if (!imagesCleaned) {
            File reportsDirectory = new File("reports/");
            final File[] files = reportsDirectory.listFiles((dir, name) -> {
                return name.matches(".*.png");
            });
            for (final File file : files) {
                file.delete();
            }
            imagesCleaned = true;
        }
    }

    @After
    public void embedScreenShot(Scenario scenario) {
        scenario.write("Current Page URL is " + getCurrentUrl());
        try {
            byte[] screenShot = getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenShot, "image/png");
        } catch (WebDriverException wde) {
            System.err.println(wde.getMessage());
        }
    }
}