package stepdefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;


public class GistStep {
    private WebDriver driver;
    private WebDriverWait wait;
    private String gistDescription;
    private List<String> gistFilename = new ArrayList<>();
    private List<String> gistContent = new ArrayList<>();;
    private String updatedGistDescription;
    private String updatedGistFilename;
    private String updatedGistContent;
    private Properties prop = new Properties();

    @Before
    public void setupTest() throws IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
        InputStream inputStreamElement = getClass().getClassLoader().getResourceAsStream("elements/gistElements.properties");
        InputStream inputStreamCred = getClass().getClassLoader().getResourceAsStream("credentials/githubCred.properties");
        prop.load(inputStreamElement);
        prop.load(inputStreamCred);
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Then("^User login into github$")
    public void userLoginIntoGithub(){
        driver.navigate().to("https://github.com/login");
        wait.until(presenceOfElementLocated(By.xpath(prop.getProperty("XPATH_GITHUB_LOGIN_LOGO"))));
        driver.findElement(By.id(prop.getProperty("ID_GITHUB_USERNAME_TEXT_FIELD"))).sendKeys(prop.getProperty("GITHUB_USERNAME"));
        driver.findElement(By.id(prop.getProperty("ID_GITHUB_PASSWORD_TEXT_FIELD"))).sendKeys(prop.getProperty("GITHUB_PASSWORD") + Keys.ENTER);
        String currentUrl = driver.getTitle();
        Assert.assertEquals("GitHub",currentUrl);
    }

    @Then("^User navigate to \"(.+)\"$")
    public void userNavigateTo(String url) {
        driver.navigate().to(url);
    }


    @Then("^User fill the Gist Description text field")
    public void userFillTheGistDescriptionFieldWith() {
        gistDescription = "Gist description " + UUID.randomUUID().toString();
        driver.findElement(By.xpath(prop.getProperty("GIST_DESCRIPTION_TEXT_FIELD"))).sendKeys(gistDescription);
    }

    @Then("^User fill the Gist filename text field$")
    public void userFillTheGistNameTextField() {
        gistFilename.add("Gist Filename " + UUID.randomUUID().toString());
        driver.findElement(By.xpath(prop.getProperty("GIST_FILENAME_TEXT_FIELD"))).sendKeys(gistFilename.get(0));
    }

    @Then("^User click Create secret gist button$")
    public void userClickCreateSecretGistButton() {
        driver.findElement(By.xpath(prop.getProperty("GIST_CREATE_SECRET_GIST_BUTTON"))).click();
    }

    @Then("^User fill the Gist content$")
    public void userFillTheGistContent() {
        gistContent.add("Gist Content " + UUID.randomUUID().toString());
        driver.findElement(By.xpath(prop.getProperty("GIST_CONTENT_TEXT_FIELD")))
                .sendKeys(gistContent.get(0));
    }

    @Then("^User will see the created gist$")
    public void userWillSeeTheCreatedGist() {
        wait.until(presenceOfElementLocated(By.xpath(String.format(prop.getProperty("TEXT_CONTAIN_XPATH"),gistDescription))));
        for (String temp : gistFilename) {
            wait.until(presenceOfElementLocated(By.xpath(String.format(prop.getProperty("TEXT_CONTAIN_XPATH"),temp))));
        }
        for (String temp : gistContent) {
            wait.until(presenceOfElementLocated(By.xpath(String.format(prop.getProperty("TEXT_CONTAIN_XPATH"),temp))));
        }
    }

    @Then("^User click Create public gist button$")
    public void userClickCreatePublicGistButton() {
        driver.findElement(By.xpath(prop.getProperty("XPATH_GIST_TYPE_DROPDOWN"))).click();
        wait.until(presenceOfElementLocated(By.xpath(prop.getProperty("XPATH_GIST_CREATE_PUBLIC_GIST_SELECTOR"))));
        driver.findElement(By.xpath(prop.getProperty("XPATH_GIST_CREATE_PUBLIC_GIST_SELECTOR"))).click();
        driver.findElement(By.xpath(prop.getProperty("XPATH_GIST_CREATE_PUBLIC_GIST_BUTTON"))).click();
    }

    @Then("^User click add file button$")
    public void userClickAddFileButton() {
        driver.findElement(By.xpath(prop.getProperty("XPATH_GIST_ADD_FILE_BUTTON"))).click();
    }

    @Then("^User fill the second Gist filename text field$")
    public void userFillTheSecondGistFilenameTextField() {
        gistFilename.add("Gist Filename " + UUID.randomUUID().toString());
        driver.findElement(By.xpath(prop.getProperty("XPATH_GIST_SECOND_FILE_FILENAME_TEXT_FIELD"))).sendKeys(gistFilename.get(1));
    }

    @Then("^User fill the second Gist content$")
    public void userFillTheSecondGistContent() {
        gistContent.add("Gist Content " + UUID.randomUUID().toString());
        driver.findElement(By.xpath(prop.getProperty("XPATH_GIST_SECOND_FILE_CONTENT_TEXT_FIELD")))
                .sendKeys(gistContent.get(1));
    }

    @Then("^User left the Gist content empty$")
    public void userLeftTheGistContentEmpty() {
        driver.findElement(By.xpath(prop.getProperty("GIST_CONTENT_TEXT_FIELD")))
                .sendKeys("");
    }

    @Then("^User will see the content cannot be empty error$")
    public void userWillSeeTheContentCannotBeEmptyError() {
        wait.until(presenceOfElementLocated(By.xpath(prop.getProperty("XPATH_CONTENT_EMPTY_NOTICE"))));
    }

    @Then("^User click view your gists$")
    public void userClickViewYourGists() {
        driver.findElement(By.xpath(prop.getProperty("XPATH_VIEW_ALL_GIST_LIST"))).click();
    }

    @Then("^User will see all the gist they have$")
    public void userWillSeeAllTheGistTheyHave() {
        driver.findElement(By.xpath(prop.getProperty("XPATH_NEWEST_GIST")));
    }

    @Then("^User click the newest gist$")
    public void userClickTheNewestGist() {
        driver.findElement(By.xpath(prop.getProperty("XPATH_NEWEST_GIST"))).click();
    }

    @Then("^User click edit button$")
    public void userClickEditButton() {
        driver.findElement(By.xpath(prop.getProperty("XPATH_EDIT_BUTTON"))).click();
        wait.until(presenceOfElementLocated(By.xpath(prop.getProperty("XPATH_EDITING_DIV"))));
    }

    @Then("^User edit Description$")
    public void userEditDescription() {
        updatedGistDescription = "Updated Gist Description " + UUID.randomUUID().toString();
        wait.until(presenceOfElementLocated(By.xpath(prop.getProperty("GIST_DESCRIPTION_TEXT_FIELD")))).clear();
        driver.findElement(By.xpath(prop.getProperty("GIST_DESCRIPTION_TEXT_FIELD"))).sendKeys(updatedGistDescription);
    }

    @Then("^User edit FileName$")
    public void userEditFileName() {
        updatedGistFilename = "Updated Gist Filename " + UUID.randomUUID().toString();
        wait.until(presenceOfElementLocated(By.xpath(prop.getProperty("GIST_UPDATE_FILENAME_TEXT_FIELD")))).clear();
        driver.findElement(By.xpath(prop.getProperty("GIST_UPDATE_FILENAME_TEXT_FIELD"))).sendKeys(updatedGistFilename);
    }

    @Then("^User edit content$")
    public void userEditContent() {
        updatedGistContent = "Updated Gist Content " + UUID.randomUUID().toString();
        //wait.until(presenceOfElementLocated(By.xpath(prop.getProperty("GIST_CONTENT_TEXT_FIELD")))).clear();
        driver.findElement(By.xpath(prop.getProperty("GIST_CONTENT_TEXT_FIELD"))).clear();
        driver.findElement(By.xpath(prop.getProperty("GIST_CONTENT_TEXT_FIELD"))).sendKeys(updatedGistContent);
    }

    @Then("^User click Update secret gist button$")
    public void userClickUpdateSecretGistButton() {
        driver.findElement(By.xpath(prop.getProperty("GIST_UPDATE_SECRET_GIST_BUTTON"))).click();
    }

    @Then("^User see updated gist detail$")
    public void userSeeUpdatedGist() {
        wait.until(presenceOfElementLocated(By.xpath(String.format(prop.getProperty("TEXT_CONTAIN_XPATH"),updatedGistDescription))));
        wait.until(presenceOfElementLocated(By.xpath(String.format(prop.getProperty("TEXT_CONTAIN_XPATH"),updatedGistFilename))));
        wait.until(presenceOfElementLocated(By.xpath(String.format(prop.getProperty("TEXT_CONTAIN_XPATH"),updatedGistContent))));
    }

    @And("^User click delete button$")
    public void userClickDeleteButton() {
        driver.findElement(By.xpath(prop.getProperty("GIST_DELETE_GIST_BUTTON"))).click();
        driver.switchTo().alert().accept();
    }

    @Then("^User will see deleted gist notification$")
    public void userWillSeeDeletedGistNotification() {
       wait.until(presenceOfElementLocated(By.xpath(prop.getProperty("GIST_DELETED_NOTICE"))));
    }
}
