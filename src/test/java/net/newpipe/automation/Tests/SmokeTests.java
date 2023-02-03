package net.newpipe.automation.Tests;


import net.newpipe.automation.TestFixtures.BaseFixture;
import org.testng.annotations.Test;
import static net.newpipe.automation.TestFixtures.BaseTest.driver;

public class SmokeTests extends BaseFixture {

    @Test(description = "Launch App")
    public void launchApp() {
        launchAppAndVerify();
    }

}
