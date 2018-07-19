package io.stuffhub.functional.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.util.List;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;

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

    @Test(priority = 2)
    public void downloadCVPdf() {
        findAvailableElement(downloadPDFCV).click();
        Assert.assertTrue(isDownloadedFileOnDisk("Резюме_Евгений_Асташевич"));
    }

    @Test(priority = 3, dependsOnMethods = "editProfile")
    public void downloadCertificatePdf() {
        findAvailableElement(downloadPdf).click();
        Assert.assertTrue(isDownloadedFileOnDisk("Adhoc.png"));
    }

    public boolean isDownloadedFileOnDisk(String name) {
        File file = new File("C:\\Users\\Admin2\\Downloads");
        File[] array;
        boolean isExistFile = false;

        long start = System.currentTimeMillis();
        long currentTime = 0;
        while (currentTime <= 10000) {
            array = file.listFiles();
            for (int i = 0; i < array.length; i++) {
                isExistFile = array[i].getName().contains(name);
                currentTime = System.currentTimeMillis() - start;
                if (isExistFile == true) {
                    break;
                }
            }
        }
        return isExistFile;
    }

    @Override
    @AfterClass
    public void logout() {
        findAvailableElement(logoutButton).click();
    }
}
