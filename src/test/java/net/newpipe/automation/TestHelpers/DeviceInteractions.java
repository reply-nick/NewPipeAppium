package net.newpipe.automation.TestHelpers;

import io.appium.java_client.MobileElement;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.qameta.allure.Allure;
import net.newpipe.automation.TestFixtures.BaseTest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Date;

public class DeviceInteractions extends BaseTest {
    public static File takeScreenshot(String prefix) throws IOException {
        // Method to take a screenshot
        File classpathRoot = new File(System.getProperty("user.dir"));
        File scrFile = (driver).getScreenshotAs(OutputType.FILE);
        File screenshot = FileUtils.getFile(scrFile);                    // file extension
        FileUtils.copyFile(screenshot, new File(classpathRoot.getAbsolutePath()
                + "/screenshots/"                // path where it will be saved
                + driver.getPlatformName() + " " // adding the Platform in the name
                + prefix + " "                   // manually defined for nothing use ""
                + new Date()
                + ".png"));

        //File name example: "ios FAIL on loginWithExistingAccount2 Sat Oct 17 22:31:29 PDT 2020.png"
        return screenshot;
    }

    public static void allureAttachScreenshot(File screenshot) throws IOException {
        ByteArrayInputStream screenshotInBytes = new ByteArrayInputStream(
                Files.readAllBytes(Paths.get(screenshot.getAbsolutePath()))
        );
        Allure.addAttachment("Screenshot", "image/png", screenshotInBytes,
                ".png");
    }

    public static File grabScreenRecording(String prefix) throws IOException {
        File classpathRoot = new File(System.getProperty("user.dir"));
        String rawScreenRec = ((CanRecordScreen) driver).stopRecordingScreen();
        byte[] screenRecord = Base64.decodeBase64(rawScreenRec);
        String destinationPath = classpathRoot.getAbsolutePath()
                + "/screenRecordings/"
                + driver.getPlatformName() + " Video "
                + prefix + " "
                + new Date()
                + ".mp4";
        Path filePath = Paths.get(destinationPath);
        Files.write(filePath, screenRecord);
        File recordedFile = FileUtils.getFile(String.valueOf(filePath));
        return recordedFile;
    }

    public static void allureAttachScreenRecording(File recording) throws IOException {
        ByteArrayInputStream recordingInBytes = new ByteArrayInputStream(
                Files.readAllBytes(Paths.get(recording.getAbsolutePath()))
        );
        Allure.addAttachment("Screen Recording", "video/mp4", recordingInBytes,
                ".mp4");
    }

    public static boolean waitForElementToBeDisplayed(MobileElement element, Integer... timeout) {
        int duration = 1;
        if (timeout.length > 0) {
            duration = timeout[0];
        }
        try {
            new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(duration))
                    .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
