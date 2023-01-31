package net.newpipe.automation.Screens;

import net.newpipe.automation.TestHelpers.DeviceInteractions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Step;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import javax.swing.text.TabExpander;

public class TrendingScreen {
    private AppiumDriver driver;;

    public TrendingScreen (AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this.getClass());
    }

    @AndroidFindBy(id = "tba")
    public MobileElement videoThumbnail;

    @Step("Wait for 'Splash' screen to load")
    public TrendingScreen waitForScreenToLoad() {
        Assert.assertTrue(DeviceInteractions.waitForElementToBeDisplayed(videoThumbnail),
                "SplashScreen is not loaded.");
        return new TrendingScreen(driver);
    }

}
