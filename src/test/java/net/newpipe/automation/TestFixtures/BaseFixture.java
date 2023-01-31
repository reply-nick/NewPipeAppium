package net.newpipe.automation.TestFixtures;


import io.qameta.allure.Step;
import net.newpipe.automation.Screens.TrendingScreen;

public class BaseFixture extends BaseTest{

    @Step("Login with existing account")
    public TrendingScreen LaunchAppAndVerify(){
        return new TrendingScreen(driver).waitForScreenToLoad();
    }
}
