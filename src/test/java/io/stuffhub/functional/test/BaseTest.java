package io.stuffhub.functional.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;

import java.text.MessageFormat;
import java.util.List;

public class BaseTest {

    private static WebDriverManager manager;
    protected static WebDriver driver;


    //http://staffhub.pstlabs.by/company без логаута
    protected By enterButton = By.className("btn-co-signin");

    //http://staffhub.pstlabs.by/company/signin
    protected By emailField = By.id("co-email");
    protected By passwordField = By.id("co-password");
    protected By submit = By.xpath("//div/button[contains(@class,\"sh-btn-primary-green\")]");
    protected By lookingForAJobButton = By.xpath("//a[@routerlink='/']");
    protected String xPathAfterAcceptingInvititation = "//p[contains(text(),'В ближайшее время с Вами свяжется представитель ко')]";

    //all menu item in dropdown after login
    protected By loginDropdown = By.id("dropdownBasic1");
    protected By searchMenuItem = By.xpath("//nav/div/div/div/a[1]");
    protected By myCompanyItem = By.xpath("//div/nav/div/div[2]/div/div/a[1]");
    protected By logoutMenuItem = By.xpath("//nav/div/div/div/a[2]");
    protected By myVacanciesItem = By.xpath("//div/div/a[@routerlink='/company/cabinet/vacancies']");
    protected By myCandidatesItem = By.xpath("//a[@routerlink='/company/cabinet/candidates']");
    protected By companyMenuContainer = By.xpath("//nav[contains(@class, 'cabinet__menu')]");

    //http://staffhub.pstlabs.by/company/cabinet/vacancies
    protected By addNewVacancy = By.xpath("//a[@class='control-link']");
    protected By vacancyName = By.xpath("//html//form[contains(@class, 'form ng-untouched')]/div/div/input[1]");
    protected By textAreaFields = By.xpath("//textarea[contains(@class, 'input-field--textarea')]");
    protected By regionField = By.xpath("//label[contains(text(),'Регион*')]/../input");
    protected By salary = By.xpath("//div[contains(@class, 'grid__box--size-auto')]/input");
    protected By typeOfEmployment = By.xpath("//html//div/label[1]/span[1][@class='input-checkbox__decor']");
    protected By skillsRequirementsField = By.xpath("//html//div[5]/div[1]/input[1]");
    protected By nameOfCreatedVacancy = By.xpath("//div[@class='flow__subtitle']");
    protected By day_month_creating = By.xpath("//html//div[@class='flow__section']/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/span[1]");
    protected By year_creating = By.xpath("//html//div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/span[2]");
    protected By allcreatedVacancies = By.xpath("//div[contains(@class, 'flow__subtitle')]");
    protected By editVacancy = By.xpath("//app-icon");
    protected By checkboxOfFirstVacancy = By.xpath("//html//div[1]/div[1]/div[1]/div[2]/div[1]/div[3]/label[1]/span[1]");
    protected By indicatorsOfCountInvited = By.xpath("//div/span[contains(@class,'badge badge--info')]");
    protected By indicatorsOfCountSuccess = By.xpath("//div/span[contains(@class,'badge badge--success')]");
    protected By indicatorsOfCountWaiting = By.xpath("//div/span[contains(@class,'badge badge--warning')]");
    protected By indicatorsOfCountDeclined = By.xpath("//div/span[contains(@class,'badge badge--error')]");
    protected By buttonClosePopup = By.xpath("//span[@aria-hidden='true']");
    protected By saveNotFullInformations = By.xpath("//button[@class='btn co-primary']");
    protected By errorText = By.xpath("//div[@class='error-box']");

    //http://staffhub.pstlabs.by/company/cabinet/candidates
    protected By candidateName = By.xpath("//html//div[1]/div[1]/div[1]/div[1]/div[2]/div[1]");
    protected By currentVacancyName = By.xpath("//div[@class='txt-caption']");
    protected By candidateAcceptedInvitationText = By.xpath("//html//div[1]/div[1]/div[1]/div[1]/div[2]/div[3]");

    //http://staffhub.pstlabs.by/company/search
    protected By skillsField = By.xpath("//form/div[2]/div[2]/input[contains(@class, \"form-control\")]");
    protected By addSkills = By.xpath("//div[2]/span/button[contains(@class, \"btn-secondary\")]");
    protected By searchSubmit = By.xpath("//form/div/div/button");

    //http://staffhub.pstlabs.by/company/result?&   extended search
    protected By numberOfFoundedCV = By.xpath("//div[contains(@class, 'score-result')]//span");
    protected By allFoundedCV = By.xpath("//div[contains(@class, 'result-content')]");
    protected By genderOfCandidate = By.xpath("//div[@class='btn-group ng-untouched ng-pristine ng-valid']/label");
    protected By iconOfMan = By.xpath("//div[@class='gender male']");
    protected By closeButton = By.xpath("//span[contains(@class, 'close')]");
    protected By iconOfWoman = By.xpath("//div[@class='gender female']");
    protected By allForeignlanguages = By.xpath("//fieldset/div[contains(@class, 'btn-group')]//label");
    protected By iconOfLanguage = By.xpath("//div/img");
    protected By readyForMissions = By.xpath("//label[contains(@class, 'ready-for-travel')]");
    protected By readyForTrips = By.xpath("//p[contains(@class, 'trips')]");
//    protected By  = By.xpath("");
//    protected By  = By.xpath("");
//    protected By  = By.xpath("");

    //http://staffhub.pstlabs.by/company/candidatecard/full
    protected By fullCVButton = By.xpath("//app-candidate-card/div/div[1]/div[2]/a");
    protected By realUserSkills = By.className("skill-name");
    protected By technologies = By.xpath("//app-full/div[2]/div/div[5]/div/div[2]/div/p/span");
    protected By returnToSearchResult = By.xpath("//a[contains(@class, \"return\")]/span");
    protected By inviteToInterview = By.xpath("//div[@id='toggle-1-header']");
    protected By checkBoxOfVacancy = By.xpath("//label[contains(@class, 'd-inline-block')]");
    protected By sendInviteToCandidate = By.xpath("//button[@class='btn co-primary']");
    protected By tecnologiesContainer = By.xpath("//p[contains(@class, 'text-blue ')]");

    //http://staffhub.pstlabs.by/company/cabinet/mycompany
    protected By editButton = By.xpath("//a[@class='control-link']");
    protected By downloadAvatar = By.xpath("//div/div/form/div[1]/div/div[1]/div/div[2]/label");
    protected By shortNameCompanyField = By.xpath("//html//div[@class='grid__box grid__box--size-12 grid__box--size-m-auto']/div[1]/div[1]/input[1]");
    protected By websiteField = By.xpath("//html//div[3]/div[1]/div[1]/div[1]/input[1]");
    protected By companyEmailField = By.xpath("//html//div[4]/div[1]/div[1]/div[1]/input[1]");
    protected By phoneNumberField = By.xpath("//html//div[5]/div[1]/div[1]/div[1]/input[1]");
    protected By addressField = By.xpath("//div[6]/div[1]/div[1]/div[1]/textarea[1]");
    protected By dateOfFoundation = By.xpath("//div/div[3]/div[1]/div/div/div/input");
    protected By allCountOfStuff = By.xpath("//form/div[1]/div/div[3]/div[2]/div/div/div[1]/input");
    protected By countStuffInRB = By.xpath("//div/div/form/div[1]/div/div[3]/div[2]/div/div/div[2]/input");
    protected By fieldOfActivity = By.xpath("//form/div[1]/div/div[3]/div[3]/div/textarea[contains(@class, \"input-field\")]");
    protected By companyDescription = By.xpath("//div/div/form/div[1]/div/div[3]/div[4]/div/textarea");
    protected By saveButton = By.xpath("//button[contains(@class, 'sh-btn')]");
    protected By cancelButton = By.xpath("//a[@class='link link--gray link--dotted']");
    protected By textWhenInfoIsNotFull = By.xpath("//div[@class='company__fill-info']");

    //http://staffhub.pstlabs.by/
    protected By loginButton = By.xpath("//a[@class='btn-link signin']");
    protected By telophoneField = By.xpath("//input[@formcontrolname='phoneNumber']");
    protected By clientPasswordField = By.xpath("//input[@formcontrolname='password']");
    protected By submitButton = By.xpath("//input[@class='sh-btn sh-btn-primary-flat sh-btn-size-medium sign-in']");
    protected By forCompanyLink = By.xpath("//html//footer[@class='blur']//a[4]");

    //http://staffhub.pstlabs.by/resume/cv
    protected By myCVMenu = By.xpath("//a[@routerlink='cv']");
    protected By editClientProfileButton = By.xpath("//a[@class='edit']");
    protected By addCertificate = By.xpath("//div[@class='certificate-add']");
    protected By downloadPhotoButton = By.xpath("//label[@class='certificate-add-pic']/../div/input");
    protected By downloadPhotoText = By.xpath("//label[@class='certificate-add-pic']");
    protected By saveChanges = By.xpath("//a[@class='edit'][contains(text(),'Сохранить изменения')]");
    protected By cancelEditButton = By.xpath("//h2[contains(@class, 'top-pan')]/a[2]");
    protected By certificateName = By.xpath("//textarea[@maxlength='255']");
    protected By deleteCertificateButton = By.xpath("//div[@class='certificate-delete']");
    protected By changeAvatarButton = By.xpath("//div[contains(@class, 'user-pic')]/input");
    protected By changeAvatarText = By.xpath("//div[contains(@class, 'user-pic')]/button");
    protected By downloadPDFCV = By.xpath("//a[@class='pdf']");
    protected By logoutButton = By.xpath("//html//div[@class='bg-candidate']//li[5]/a[1]");
    protected By downloadPdf = By.xpath("//h5[@class='certificate']");

    //Client Invitations page
    protected By menuInvitations = By.xpath("//a[@routerlink='invitation']//i[@class='icon icon-invoices']");
    protected By allInvitationsOfList =By.xpath("//ul[contains(@class, 'invoices')]//li//div[1]//div[2]//div[2]//h3[1]");
    protected By acceptInvitation = By.xpath("//html//li[1]/div[1]/div[2]/div[3]/button[1]");
    protected By buttonYes = By.xpath("//button[@class='sh-btn sh-btn-flat sh-btn-secondary-green'][contains(text(),'Да')]");
    protected By declineInvitation = By.xpath("//html//li[1]/div[1]/div[2]/div[3]/button[2]");
    protected By allInvitations = By.xpath("//html//div[@class='col-md-8']//li");
    protected By buttonsInterestAndNoInterest = By.xpath("//html//li[1]/div[1]/div[2]/div[3]/button");
    protected By closedVacancyText = By.xpath("//p[@class='description closed']");
    protected By footer = By.xpath("//div[contains(@class, 'copy')]");
    protected By inviteContainer = By.xpath("//div[contains(@class, 'description-wrapper')]");
    protected By candidatMenuContainer = By.xpath("//ul");

    //client how improve your cv
    protected By improveCVItem = By.xpath("//a[@routerlink='recomendations']");

    protected By spinner = By.xpath("//app-spinner");
    private Logger logger = LoggerFactory.getLogger(BaseTest.class);


    public BaseTest() {
        setup();
    }

    public void waitForSpinnerToBeGone(int timeout) {
        try {
            new WebDriverWait(driver, 1).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//app-spinner")));
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver driver) {
                    WebElement spinner = driver.findElement(By.xpath("//app-spinner"));
                    String isVisible = spinner.getAttribute("ng-reflect-show");
                    return !Boolean.parseBoolean(isVisible);
                }
            });
        } catch (Exception e) {
            System.out.println("Spinner has't been appeared during 1 second. There is no sense in waiting for his disappearance");
        }
    }

    public void elementDisappearing(By by) {
        try {
            new WebDriverWait(driver, 1).until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (Exception e) {
            System.out.println("Element has't been visible or present in DOM, so ExpectedConditions can't wait his invisibility");
        }

    }

    public WebElement findAvailableElement(By by) {
        WebElement loadingElement = driver.findElement(by);
        try {
            loadingElement = new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Exception e) {
            logger.warn(e.getMessage());
            System.out.println("Element hasn't been founded on the page. Waiting was 10 seconds");
        }
        return loadingElement;
    }

    public List<WebElement> findElements(By by) {
        try {
            findAvailableElement(by);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }

        return driver.findElements(by);
    }

    public static void setup() {
        if (manager == null) {
            WebDriverManager.chromedriver().setup();
        }
        if (driver == null) {
            ChromeOptions chromeOptions = new ChromeOptions();
            if (Boolean.valueOf(System.getProperty("my.param"))) {
                chromeOptions.addArguments("--headless");
            }
            driver = new ChromeDriver(chromeOptions);
            driver.manage().window().maximize();
//            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
    }

    public String getRandomString() {
        return RandomStringUtils.random(10, "abcdefghijklmnopqrstuvwxyz");
    }

    public String getXpathByTextInSelector(String textInSelector) {
        String xPath = MessageFormat.format(".//*[text()=\"{0}\"]", textInSelector);
        return xPath;
    }

    public boolean isElementAvailable(By by) {
        return driver.findElements(by).size() == 0 ? false : true;
    }

    @AfterSuite
    public void logout() {
        driver.quit();
    }

}
