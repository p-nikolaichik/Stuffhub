package io.stuffhub.functional.test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.$;

public class CandidateTest extends BaseTest{

    public void openCandidatePage() {
        driver.get("http://staffhub.pstlabs.by");
    }

    @BeforeClass
    public void loginTest() {
        openCandidatePage();
        findAvailableElement(loginButton).click();
        findAvailableElement(telophoneField).sendKeys("(+375) 29 333-95-06");
        findAvailableElement(clientPasswordField).sendKeys("pass");
        findAvailableElement(submitButton).submit();
        Assert.assertTrue(findAvailableElement(myCVMenu).isEnabled());
    }

    @Test(priority = 1)
    public void editProfile() throws AWTException, InterruptedException {
        findAvailableElement(editClientProfileButton).click();

        deleteAllCertificates();
        findAvailableElement(addCertificate).click();
        addPhoto(downloadPhotoButton);
        Assert.assertEquals(findAvailableElement(downloadPhotoText).getText(), "Adhoc.png");

        addPhoto(changeAvatarButton);
        Assert.assertEquals(findAvailableElement(changeAvatarText).getText(), "Adhoc.png");

        findAvailableElement(certificateName).sendKeys("TEST Certificate");
        findAvailableElement(saveChanges).click();

        waitForSpinnerToBeGone( 5);
        elementDisappearing(cancelEditButton);
        waitForSpinnerToBeGone( 5);
    }

    private void deleteAllCertificates() {
        List<WebElement> allDeleteButton = driver.findElements(deleteCertificateButton);
        for (int i = 0; i < allDeleteButton.size(); i++) {
            allDeleteButton.get(i).click();
        }
    }

    private void addPhoto(By by) throws AWTException{
//        findAvailableElement(downloadPhoto).click();
//        setPathInWindowPopup("src/test/resources/Adhoc.png");
        String absolutePath = new String(new File("src/test/resources/Adhoc.png").getAbsolutePath());
        findAvailableElement(by).sendKeys(absolutePath);
    }

//    private void setPathInWindowPopup(String path) throws AWTException {
//        Robot robot = new Robot();
//        robot.setAutoDelay(1000);
//        StringSelection stringSelection = new StringSelection(new File(path).getAbsolutePath());
//        Clipboard clbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
//        clbrd.setContents(stringSelection, null);
//
//        robot.keyPress(KeyEvent.VK_CONTROL);
//        robot.keyPress(KeyEvent.VK_V);
//
//        robot.keyRelease(KeyEvent.VK_CONTROL);
//        robot.keyRelease(KeyEvent.VK_V);
//
//        robot.keyPress(KeyEvent.VK_ENTER);
//        robot.keyRelease(KeyEvent.VK_ENTER);
//    }

    @Test()
    public void downloadCVPdf() {
        waitForSpinnerToBeGone(5);
        findAvailableElement(downloadPDFCV).click();
        waitForSpinnerToBeGone(5);
        checkDownloadedFileOnDisk("Резюме_Евгений_Асташевич");
    }

    @Test(dependsOnMethods = "editProfile")
    public void downloadCertificatePdf() {
        findAvailableElement(downloadPdf).click();
        waitForSpinnerToBeGone(5);
        checkDownloadedFileOnDisk("Adhoc.png");
    }

    public void checkDownloadedFileOnDisk(String name) {
        File[] list = newPathToDownload.listFiles();
        long startTime = System.currentTimeMillis();
        while (ArrayUtils.isEmpty(list)) {
            long currentTime = System.currentTimeMillis() - startTime;
            list = newPathToDownload.listFiles();
            if (currentTime >= 4000) {

                break;
            }
        }
        Assert.assertTrue(list[0].getName().contains(name));
        try {
            FileUtils.forceDelete(newPathToDownload);
        } catch (IOException e) {
            logger.error("File hasn't been removed");
            e.printStackTrace();
        }
    }

    @Override
    @AfterClass
    public void logout() {
        waitForSpinnerToBeGone(5);
        findAvailableElement(logoutButton).click();
    }
}
