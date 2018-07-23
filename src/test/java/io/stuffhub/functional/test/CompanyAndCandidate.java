package io.stuffhub.functional.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.awt.*;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

import static io.stuffhub.functional.test.TestTask.getCountOfInvitations;
import static io.stuffhub.functional.test.TestTask.getCountOfPages;
import static io.stuffhub.functional.test.TestTask.getHTTPContext;

public class CompanyAndCandidate extends BaseTest {

    private CompanyTest company = new CompanyTest();
    private CandidateTest candidate = new CandidateTest();
    private String companyPage;
    private String candidatePage;
    private String nameOfCurrentVacancy;
    private String nameOfCandidate = "Евгений Асташевич";

    @BeforeMethod (groups = "Invitations")
    public void checkInvitations() throws Exception{
        sendInvitationToCandidate();
        driver.switchTo().window(candidatePage);

        findAvailableElement(improveCVItem).click();
        findAvailableElement(menuInvitations).click();
//        driver.navigate().refresh();

        waitForSpinnerToBeGone(5);
        findAvailableElement(menuInvitations).click();
        WebElement nameOfVacancy = findAvailableElement(By.xpath(getXpathByTextInSelector(nameOfCurrentVacancy)));
        Assert.assertEquals(nameOfVacancy.getText(), nameOfCurrentVacancy);
    }

    @Test (priority = 1, groups = "Invitations")
    public void acceptInvitation() {
        findAvailableElement(acceptInvitation).click();
        findAvailableElement(buttonYes).click();

        String searchText = MessageFormat.format(".//*[text()=\"{0}\"]/../div/p", nameOfCurrentVacancy);
        Assert.assertTrue(findAvailableElement(By.xpath(searchText)).getText().contains("В ближайшее время с Вами свяжется представитель компаниии"));

        driver.switchTo().window(companyPage);
        findAvailableElement(loginDropdown).click();
        findAvailableElement(myCandidatesItem).click();
        Assert.assertEquals(findAvailableElement(currentVacancyName).getText(), nameOfCurrentVacancy);
        Assert.assertEquals(findAvailableElement(candidateName).getText(), nameOfCandidate);
        Assert.assertEquals(findAvailableElement(candidateAcceptedInvitationText).getText(), "Кандидат принял приглашение");
        checkIndicatorsOfCandidateActions("1", "1", "0", "0");
    }

    @Test (priority = 2, groups = "Invitations")
    public void declineInvitation() throws Exception{
        getHTTPContext();
        int countOfInvitations = Integer.parseInt(getCountOfInvitations());

        Actions actions = new Actions(driver);
        List<WebElement> list = findElements(inviteContainer);

        while (list.size() != countOfInvitations) {
            list = findElements(inviteContainer);
            actions.sendKeys(Keys.chord(Keys.CONTROL, Keys.END)).perform();
        }
        Assert.assertEquals(list.size(), countOfInvitations);

        findAvailableElement(declineInvitation).click();
        findAvailableElement(buttonYes).click();

        getHTTPContext();
        countOfInvitations = Integer.parseInt(getCountOfInvitations());
        list = findElements(inviteContainer);

        Assert.assertEquals(list.size(), countOfInvitations);

//        driver.navigate().refresh();
//        waitForSpinnerToBeGone(5);
//        findAvailableElement(menuInvitations).click();
        driver.switchTo().window(companyPage);

        findAvailableElement(loginDropdown).click();
        findAvailableElement(myCandidatesItem).click();
        findAvailableElement(companyMenuContainer);
        Assert.assertEquals(findAvailableElement(currentVacancyName).getText(), nameOfCurrentVacancy);
        Assert.assertEquals(findAvailableElement(candidateName).getText(), "#1");
        Assert.assertEquals(findAvailableElement(candidateAcceptedInvitationText).getText(), "Кандидат отклонил приглашение");
        checkIndicatorsOfCandidateActions("1", "0", "0", "1");
    }

    @Test (priority = 3, groups = "Invitations")
    public void acceptClosedInvitation() {
        driver.switchTo().window(companyPage);
        findAvailableElement(loginDropdown).click();
        findAvailableElement(myVacanciesItem).click();
        findAvailableElement(checkboxOfFirstVacancy).click();
        driver.switchTo().window(candidatePage);
        findAvailableElement(acceptInvitation).click();
        findAvailableElement(buttonYes).click();

        String xPathOfButtonsAcceptAndDecline = getXpathByTextInSelector(nameOfCurrentVacancy) + "/../../div[3]/button";

        try {
            elementDisappearing(By.xpath(xPathOfButtonsAcceptAndDecline));
        } catch (Exception e) {
            Assert.fail("Кнопки <Интересует> и <Не интересует> были найдены");
        }

        String xPathOfTextNotActualVacancy = getXpathByTextInSelector(nameOfCurrentVacancy) + "/../p[3]";
        WebElement elementOfNotActualVacancy = findAvailableElement(By.xpath(xPathOfTextNotActualVacancy));
        String textNotActualVacancy = elementOfNotActualVacancy.getText();
        Assert.assertEquals(textNotActualVacancy, "Извините, данная вакансия больше не актуальна.");
        String color = elementOfNotActualVacancy.getCssValue("color").toString();
        Assert.assertEquals(color, "rgba(223, 127, 127, 1)");
        driver.switchTo().window(companyPage);
        checkIndicatorsOfCandidateActions("1", "0", "1", "0");
    }

    public void sendInvitationToCandidate() {
        nameOfCurrentVacancy = getRandomString();
        company.createNewVacancy(nameOfCurrentVacancy);
        company.sendInvitationToCandidate(nameOfCurrentVacancy);
    }

    @BeforeClass
    public void companyAndCandidateLogin() throws Exception{
        company.login();
        openNewTab();
        Set handles = driver.getWindowHandles();

        Object[] allHandles = handles.toArray(new Object[handles.size()]);
        companyPage = allHandles[0].toString();
        candidatePage = allHandles[1].toString();
        driver.switchTo().window(candidatePage);
        candidate.loginTest();
        driver.switchTo().window(companyPage);
    }

    public void openNewTab() throws AWTException{
        ((JavascriptExecutor)driver).executeScript("window.open()");
    }

//    public void openLinkInNewTab(By by) {
//        String selectLinkOpenInNewTab = Keys.chord(Keys.CONTROL,Keys.RETURN);
//        findAvailableElement(lookingForAJobButton).sendKeys(selectLinkOpenInNewTab);
//    }

    public void checkIndicatorsOfCandidateActions(String first, String second, String third, String forth) {
        findAvailableElement(companyMenuContainer);
        findAvailableElement(loginDropdown).click();
        findAvailableElement(myVacanciesItem).click();
        Assert.assertTrue(findAvailableElement(indicatorsOfCountInvited).getText().equals(first));
        Assert.assertTrue(findAvailableElement(indicatorsOfCountSuccess).getText().equals(second));
        Assert.assertTrue(findAvailableElement(indicatorsOfCountWaiting).getText().equals(third));
        Assert.assertTrue(findAvailableElement(indicatorsOfCountDeclined).getText().equals(forth));
    }
}
