import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthorizationPage {
    private By  enter=By.cssSelector(".js-open-modal");
    private By login_field=By.cssSelector("[action='/login/'] [name='email']");
    private By password_field=By.name("password");
    private String btn_enter=".new-input-line_relative .new-button_md";
    private WebDriver driver;

    public AuthorizationPage(WebDriver driver) {
        this.driver = driver; }
    public MainPage auth(String login, String password){
        //клик на вход
        driver.findElement(enter).click();
        //ввод логина и пароля
        driver.findElement(login_field).sendKeys(login);
        driver.findElement(password_field).sendKeys(password);
        //нажатие кнопки Войти
        driver.findElement(By.cssSelector(btn_enter)).click();
        return new MainPage(driver);
    }
}
