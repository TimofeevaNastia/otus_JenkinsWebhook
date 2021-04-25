import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class BaseClass {

    protected static WebDriver driver;
    public Logger logger = LogManager.getLogger(PersonalPageTest.class);
    public ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    //ввод данных в поле (с предварительной очисткой), где text - вводимое значениее, by - локатор поля ввода
    public void setValue(String text,By by){
        WebElement element =  driver.findElement(by);
        element.click();
        element.clear();
        element.sendKeys(text);

    }

    //Выбор значения из выпадающего списка, где field - раскрытие выпадащего списка, data - выбор значения в выпадающем списке
    public void setValueSelect(By field,By data){
        click(field);
        click(data);
    }

    //получение значения атрибута value
    public String getAttributeValue(By by){
       return driver.findElement(by).getAttribute("value");

    }
    //получение текста элемента
    public String getText(By by){
        return driver.findElement(by).getText();

    }

    //получение текста элемента
    public File takeScreen()  {
        File screenshot = ((TakesScreenshot) driver).
                getScreenshotAs(OutputType.FILE);
        return screenshot;
        //String path = "./target/screenshots/" + screenshot.getName();
        //File screenshot = FileUtils.copyFile(screenshot, new File(path));

    }

    @Before
    public void setUp() {
        //WebDriverManager.chromedriver().setup();
        logger.info("Драйвер поднят");
        /*//если появляется капча
        ChromeOptions options = new ChromeOptions();
        options.addArguments("user-data-dir=~AppData\\Local\\Google\\Chrome\\User Data");
        options.addArguments("--profile-directory=Default");
        driver = new ChromeDriver(options);
        */
        //driver = new ChromeDriver();
        driver =initDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(cfg.url1());
        driver.manage().window().maximize();
    }

    public static WebDriver initDriver() {
        URL url;
        RemoteWebDriver rd;
        // url= new URL("https://127.0.0.1:4444/wd/hub");


        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("browserVersion", "89.0");
        /// capabilities.setCapability("enableVNC",true);
        // capabilities.setCapability("enableVideo",true);
        try {
            rd = new RemoteWebDriver(URI.create("http://localhost:4444/wd/hub").toURL(), capabilities);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return rd;
        //WebDriverManager.chromedriver().setup();
        //return new ChromeDriver();
    }

    @After
    public void setDown() throws IOException {

        if (driver != null) {
            driver.quit();
            logger.info("Драйвер закрыт");
        }

    }

    //клик на элемент
    public void click(By element){
        WebElement element2 = new WebDriverWait(driver, 10).ignoring(StaleElementReferenceException.class).until(elementToBeClickable(element));
        element2.click();
        //int attempts = 0;
       /* while(attempts < 3) {
        try {
                element2.click();
                break;
            }
            catch(ElementClickInterceptedException e) {
                logger.error("Ошибка ElementClickInterceptedException клика на " + element2);
            }
            catch(StaleElementReferenceException e) {
                logger.error("Ошибка StaleElementReferenceException клика на " + element2);
            }
            attempts++;
        }*/
    }
}
