package net.newpipe.automation.TestFixtures;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.qameta.allure.Allure;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import io.qameta.allure.Step;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

import static net.newpipe.automation.TestHelpers.DeviceInteractions.*;

public class BaseTest {

    public static AppiumDriverLocalService service;
    public static AppiumDriver driver;
    public static Properties prop = new Properties();
    @BeforeSuite(alwaysRun = true)
    @Step("Starting Appium server")
    public void globalSetup(ITestContext context) {
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                .withArgument(GeneralServerFlag.RELAXED_SECURITY)
                .withLogFile(new File(System.getProperty("user.dir") + "/logs/appium.log"))
                .usingAnyFreePort();
        service = builder.build();
        service.start();
    }

    @BeforeMethod
    public void setUp() throws IOException {
        File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "/app");
        File app;

        loadProperties("appium.properties");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        app = new File(appDir.getCanonicalPath(), "NewPipe.apk");
        capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, prop.getProperty("NoReset"));
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, prop.getProperty("FullReset"));
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, prop.getProperty("NewCommandTimeout"));
        capabilities.setCapability(AndroidMobileCapabilityType.APPLICATION_NAME, "UiAutomator2");
        capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, prop.getProperty("AppPackage"));
        capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, prop.getProperty("AppActivity"));
        capabilities.setCapability(AndroidMobileCapabilityType.NO_SIGN, "true");
        capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, prop.getProperty("AutoGrantPermissions"));
        System.out.println("INFO: Starting Android driver with the following capabilities: \n" + capabilities);
        driver = new AndroidDriver(service.getUrl(), capabilities);

        driver.manage().logs().get("logcat");

    }

    @AfterMethod
    @Step("Stopping the driver")
    public void tearDown(ITestResult result) throws Exception {

        Path screenshotFilePath = null;
        Path recordedFilePath = null;
        String screenshotName = result.getName();

        if (ITestResult.FAILURE == result.getStatus()) {

            screenshotName = "FAIL on " + result.getName();

            String source = driver.getPageSource();
            Allure.addAttachment("ScreenSource", source);

            // Taking a Screenshot & ScreenRecording on fail with the prefix -> "FAIL on " + name of the running test
            File screenshot = takeScreenshot(screenshotName);
            allureAttachScreenshot(screenshot);
            screenshotFilePath = screenshot.getAbsoluteFile().toPath();

            File recordedFile = grabScreenRecording(screenshotName);
            allureAttachScreenRecording(recordedFile);
            recordedFilePath = recordedFile.getAbsoluteFile().toPath();

            System.out.println(source);
        }
    }

    @AfterSuite
    @Step("Stopping Appium server")
    public void globalTearDown() {
        service.stop();
        service = null;
    }

    public static void loadProperties(String fileName) {
        try {
            prop.load( new FileInputStream("./" + fileName) );
        } catch (IOException e) {
            System.out.println("ERROR: Can not load Properties");
            e.printStackTrace();
        }
    }
}
