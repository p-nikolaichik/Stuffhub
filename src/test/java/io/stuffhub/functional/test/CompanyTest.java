package io.stuffhub.functional.test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CompanyTest extends BaseTest{

    private String nameOfNewVacancy;

    public void openCompanyPage() {
        driver.get("http://staffhub.pstlabs.by/company");
    }

    @BeforeClass
    public void login() {
        openCompanyPage();
        String title = driver.getTitle();
        Assert.assertEquals(title, "StaffhubApp");
        findAvailableElement(enterButton).click();
        findAvailableElement(emailField).clear();
        findAvailableElement(emailField).sendKeys("eugene.astashevich@gmail.com");
        findAvailableElement(passwordField).clear();
        findAvailableElement(passwordField).sendKeys("pass");
        findAvailableElement(submit).submit();
        String logoButton = findAvailableElement(loginDropdown).getText();
        Assert.assertEquals("eugene.astashevich@gmail.com", logoButton);
    }

    @DataProvider(name = "searchTests")
    public Object[][] createDataForSearch() {
        return new Object[][] {
                { "js"},
                { " js "},
                { "Js"},
                { "js"},
        };
    }


    @Test(dataProvider = "searchTests")
    public void searchCandidateProfile(String input) {
        waitForSpinnerToBeGone(5);
        findAvailableElement(loginDropdown).click();
        findAvailableElement(searchMenuItem).click();
        findAvailableElement(skillsField).sendKeys(input.trim());
        findAvailableElement(addSkills).click();
        findAvailableElement(searchSubmit).submit();
        waitForSpinnerToBeGone(5);
        String countFoundedCV = findAvailableElement(numberOfFoundedCV).getText();
        int numberFoundedCV = Integer.parseInt(countFoundedCV);
        List<WebElement> foundedCV = findElements(allFoundedCV);
        Assert.assertEquals(numberFoundedCV, foundedCV.size());
        for (int i = 0; i < foundedCV.size(); i++) {
            waitForSpinnerToBeGone(5);
            findElements(allFoundedCV).get(i).click();
            findAvailableElement(fullCVButton).click();
            findAvailableElement(tecnologiesContainer);
            List<WebElement> foundedSkills = driver.findElements(realUserSkills);
            List<WebElement> technologiesList = driver.findElements(technologies);
            foundedSkills.addAll(technologiesList);
            boolean founded = false;
            for (int j = 0; j < foundedSkills.size(); j++) {
                String text = foundedSkills.get(j).getText();
                if (input.toLowerCase().contains(text.toLowerCase())) {
                    founded = true;
                    break;
                }
            }
            Assert.assertTrue(founded);
            findAvailableElement(returnToSearchResult).click();
        }
    }

    @Test
    public void extendedSearchTest() {
        openExtendedSearch();
        checkGender();
        checkLanguages();
    }

    public void checkGender() {
        List<WebElement> allProfilesOfCandidate = findElements(allFoundedCV);
        List<WebElement> genderButtons = findElements(genderOfCandidate);
        WebElement manButton = genderButtons.get(0);
        WebElement womanButton = genderButtons.get(1);
        manButton.click();
        findAvailableElement(searchSubmit).submit();
        waitForSpinnerToBeGone(5);
        List<WebElement> foundedManProfiles = driver.findElements(iconOfMan);
        int countOfManProfiles = foundedManProfiles.size();
        List<WebElement> foundedWomanProfiles = driver.findElements(iconOfWoman);
        Assert.assertTrue(foundedWomanProfiles.size() == 0);
        findElements(closeButton).get(4).click();
        womanButton.click();
        findAvailableElement(searchSubmit).submit();
        waitForSpinnerToBeGone(5);
        foundedManProfiles = driver.findElements(iconOfMan);
        foundedWomanProfiles = driver.findElements(iconOfWoman);
        int countOfWomanProfiles = foundedWomanProfiles.size();
        Assert.assertTrue(foundedManProfiles.size() == 0);
        Assert.assertEquals(countOfManProfiles + countOfWomanProfiles, allProfilesOfCandidate.size());
        findElements(closeButton).get(4).click();
    }

    public void checkLanguages() {
        List<WebElement> foreignLanguages = findElements(allForeignlanguages);
        WebElement enLanguage = foreignLanguages.get(0);
        WebElement geLanguage = foreignLanguages.get(1);
        WebElement itLanguage = foreignLanguages.get(2);
        enLanguage.click();
        findAvailableElement(searchSubmit).submit();
        waitForSpinnerToBeGone(5);
        List<WebElement> list_Icon_languages = driver.findElements(iconOfLanguage);
        List<WebElement> allCV = driver.findElements(allFoundedCV);
        for (WebElement element : list_Icon_languages) {
            Assert.assertEquals(element.getAttribute("src"), "http://staffhub.pstlabs.by/assets/images/flag-uk.png");
        }
        Assert.assertEquals(list_Icon_languages.size(), allCV.size());
        findElements(closeButton).get(2).click();
    }

    public void checkSearchByReadyForMissions() {
        List<WebElement> readyForMissionsButtons = findElements(readyForMissions);
        WebElement buttonYes = readyForMissionsButtons.get(0);
        WebElement buttonNo = readyForMissionsButtons.get(1);

        buttonYes.click();
        checkReadyForTrip("Готов к командировкам");

        buttonNo.click();
        checkReadyForTrip("Не готов к командировкам");

        findElements(closeButton).get(3).click();
    }

    public void checkReadyForTrip(String text) {
        findAvailableElement(searchSubmit).click();
        waitForSpinnerToBeGone(5);
        List<WebElement> allProfilesOfCandidate = findElements(allFoundedCV);
        for (WebElement element : allProfilesOfCandidate) {
            element.click();
            findAvailableElement(fullCVButton).click();
            String ready_trips_text = findAvailableElement(readyForTrips).getText();
            Assert.assertTrue(ready_trips_text.contains(text));
            findAvailableElement(returnToSearchResult).click();
            int countOfFoundedCV = findElements(allFoundedCV).size();
            Assert.assertEquals(countOfFoundedCV, allProfilesOfCandidate);
        }
    }

    public void openExtendedSearch() {
        findAvailableElement(loginDropdown).click();
        findAvailableElement(searchMenuItem).click();
        findAvailableElement(searchSubmit).submit();
        waitForSpinnerToBeGone(5);
    }

    @Test
    public void editCompanyInfo() {
        waitForSpinnerToBeGone(5);
        findAvailableElement(loginDropdown).click();
        findAvailableElement(myCompanyItem).click();
        findAvailableElement(editButton).click();
        findAvailableElement(shortNameCompanyField).clear();
        findAvailableElement(shortNameCompanyField).sendKeys("Adhoc");
        findAvailableElement(websiteField).clear();
        findAvailableElement(websiteField).sendKeys("adhoc.by");
        findAvailableElement(companyEmailField).clear();
        findAvailableElement(companyEmailField).sendKeys("irina.chakur@adhoc.by");
        findAvailableElement(phoneNumberField).clear();
        findAvailableElement(phoneNumberField).sendKeys("251234567");
        findAvailableElement(addressField).clear();
        findAvailableElement(addressField).sendKeys("г. Минск, ул. Ленина, 50");
        findAvailableElement(dateOfFoundation).clear();
        findAvailableElement(dateOfFoundation).sendKeys("2013");
        findAvailableElement(allCountOfStuff).clear();
        findAvailableElement(allCountOfStuff).sendKeys("20");
        findAvailableElement(countStuffInRB).clear();
        findAvailableElement(countStuffInRB).sendKeys("18");
        findAvailableElement(fieldOfActivity).clear();
        findAvailableElement(fieldOfActivity).sendKeys("IT сфера");
        findAvailableElement(companyDescription).clear();
        findAvailableElement(companyDescription).sendKeys("Разработка програмного обеспечения под заказ");

//        String path = Thread.currentThread().getContextClassLoader().getResource("Adhoc.png").getPath().substring(1);
//        File file = new File("src/test/resources/Adhoc.png");
//        String path = file.getAbsolutePath();
        findAvailableElement(saveButton).click();
        Assert.assertTrue(findAvailableElement(editButton).isEnabled());
    }

    @DataProvider(name = "vacancyName")
    public Object[][] dataForCreatingVacancy() {
        String randomText = getRandomString();
        return new Object[][] {
                {randomText},
        };
    }

    @Test(dataProvider = "vacancyName", priority = 1)
    public void createNewVacancy(String nameOfVacancy) {
        nameOfNewVacancy = new String(nameOfVacancy);
        findAvailableElement(loginDropdown).click();
        findAvailableElement(myVacanciesItem).click();
        findAvailableElement(addNewVacancy).click();
        findAvailableElement(vacancyName).sendKeys(nameOfNewVacancy);
        List<WebElement> generalInformation = findElements(textAreaFields);
        generalInformation.get(0).sendKeys("TEST1");
        generalInformation.get(1).sendKeys("TEST2");
        generalInformation.get(2).sendKeys("TEST3");
        generalInformation.get(3).sendKeys("TEST4");
        generalInformation.get(4).sendKeys("Comments");
        findAvailableElement(regionField).sendKeys("MINSK");
        List<WebElement> salaryBounds = findElements(salary);
        salaryBounds.get(0).sendKeys("1000");
        salaryBounds.get(1).sendKeys("1500");
        List<WebElement> list = findElements(typeOfEmployment);
        for (WebElement element : list) {
            element.click();
        }
        findAvailableElement(skillsRequirementsField).sendKeys("Java, Spring, Hibernate, MongoDB");
        findAvailableElement(saveButton).click();

        Assert.assertEquals(nameOfNewVacancy ,findAvailableElement(nameOfCreatedVacancy).getText());

        String expectedData = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
        String actualData = findAvailableElement(day_month_creating).getText() + "." + findAvailableElement(year_creating).getText();
        Assert.assertEquals(expectedData, actualData);
    }

    @Test(dependsOnMethods = "createNewVacancy")
    public void sendInvitationNotFullVacancy() {
        findAvailableElement(loginDropdown).click();
        findAvailableElement(myVacanciesItem).click();
        findAvailableElement(By.xpath(getXpathByTextInSelector(nameOfNewVacancy))).click();
        findAvailableElement(editVacancy).click();
        findAvailableElement(regionField).sendKeys(Keys.CONTROL + "a");
        findAvailableElement(regionField).sendKeys(Keys.BACK_SPACE);
        findAvailableElement(saveButton).click();
        Assert.assertTrue(isElementAvailable(buttonClosePopup));
        findAvailableElement(saveNotFullInformations).click();
        sendInvitationToCandidate(nameOfNewVacancy);
        WebElement elementWithError = findAvailableElement(errorText);
        Assert.assertEquals(elementWithError.getText(), "Обязательные поля вакансии не заполнены");
        String color = elementWithError.getCssValue("background-color");
        Assert.assertEquals(color, "rgba(238, 62, 62, 1)");
    }

    @Test(dependsOnMethods = "createNewVacancy")
    public void sendInvitationWhenCompanyInfoIsNotFull() {
        findAvailableElement(loginDropdown).click();
        findAvailableElement(myCompanyItem).click();
        findAvailableElement(editButton).click();
        findAvailableElement(websiteField).sendKeys(Keys.CONTROL + "a");
        findAvailableElement(websiteField).sendKeys(Keys.BACK_SPACE);
        findAvailableElement(saveButton).click();
        Assert.assertTrue(isElementAvailable(buttonClosePopup));
        findAvailableElement(saveNotFullInformations).click();
        Assert.assertEquals(findAvailableElement(textWhenInfoIsNotFull).getText(), "Данные о компании не заполнены," +
                " вы не можете просматривать полные резюме и приглашать кандидатов на собеседования");
        sendInvitationToCandidate(nameOfNewVacancy);
        WebElement elementWithError = findAvailableElement(errorText);
        Assert.assertEquals(elementWithError.getText(), "Обязательные поля компании не заполнены");
        Assert.assertEquals(elementWithError.getCssValue("background-color"), "rgba(238, 62, 62, 1)");
        findAvailableElement(loginDropdown).click();
        findAvailableElement(myCompanyItem).click();
        findAvailableElement(editButton).click();
        findAvailableElement(websiteField).sendKeys(Keys.BACK_SPACE);
        findAvailableElement(websiteField).sendKeys("adhoc.by");
        findAvailableElement(saveButton).click();
    }

    public void sendInvitationToCandidate(String name) {

        findAvailableElement(companyMenuContainer);
        findAvailableElement(loginDropdown).click();
        findAvailableElement(searchMenuItem).click();
        findAvailableElement(searchSubmit).submit();
        List<WebElement> foundedCVList = findElements(allFoundedCV);
        foundedCVList.get(1).click();
        findAvailableElement(fullCVButton).click();
        findAvailableElement(inviteToInterview).click();
        findAvailableElement(By.xpath(getXpathByTextInSelector(name))).click();
        findAvailableElement(sendInviteToCandidate).click();
    }

    @Override
    @AfterClass
    public void logout() {
        waitForSpinnerToBeGone(5);
        findAvailableElement(loginDropdown).click();
        findAvailableElement(logoutMenuItem).click();
    }
}
