package net.newpipe.automation.Screens;

import io.appium.java_client.android.AndroidElement;
import net.newpipe.automation.TestHelpers.DeviceInteractions;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Step;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


public class TrendingScreen {
    private AppiumDriver<AndroidElement> driver;;

    public TrendingScreen (AppiumDriver<AndroidElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy( id = "itemThumbnailView")
    public AndroidElement videoThumbnail;

    @Step("Wait for 'Splash' screen to load")
    public TrendingScreen waitForScreenToLoad() {
        Assert.assertTrue(DeviceInteractions.waitForElementToBeDisplayed(videoThumbnail, 2),
                "Trending is not loaded.");
        return new TrendingScreen(driver);
    }

}
