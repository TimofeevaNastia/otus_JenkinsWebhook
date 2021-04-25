import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertEquals;

public class AboutYourself extends BaseClass{
    private WebDriver driver;
    private By by_name=By.name("fname");
    private By by_last_name=By.name("lname");
    private By by_name_lat=By.name("fname_latin");
    private By by_last_name_lat=By.name("lname_latin");
    private By by_blog_name=By.name("blog_name");
    private By by_date_birth=By.name("date_of_birth");
    private By by_country=By.xpath("//label[input[@name='country']]/div");
    private String css_country2="[title='%s']";
    private By by_city=By.xpath("//label[input[@name='city']]/div");
    private String css_city2="[title='%s']";
    private By by_english=By.xpath("//label[input[@name='english_level']]/div");
    private String css_english2="[title='%s']";
    private By by_set_check_format_work=By.xpath("//label[input[@name='work_schedule']]");
    private By by_get_check_format_work=By.name("work_schedule");
    private String xpath_contact="//label[input[@name='contact-%s-service']]//div";
    private String css_contact_2="[data-num='%s'] [title='%s']";
    private String name_contact_3="contact-%s-value";
    private By by_gender=By.name("gender");
    private By by_get_gender=By.xpath("//*[@name='gender']/option[@selected]");
    private String xpath_gender2="//option[@value='%s']";
    private By by_company=By.name("company");
    private By by_post=By.name("work");
    private By by_save_and_continue=By.name("continue");
    private By by_save_message=By.cssSelector(".hide-sm.no-print .success");
    private String save_message="Данные успешно сохранены";

    public AboutYourself(WebDriver driver) {
        this.driver = driver; }

    //ввод Имени
    public void setName(String text){
        setValue(text,by_name);
    }
    //получение имени
    public String getName(){
       return getAttributeValue(by_name);
    }
    //ввод Имени латиницей
    public void setNameLat(String text){
        setValue(text,by_name_lat);
    }
    //получение Имени латиницей
    public String getNameLat(){
        return getAttributeValue(by_name_lat);
    }
    //ввод Фамилии
    public void setLastName(String text){
        setValue(text,by_last_name);
    }
    //получение Фамилии
    public String getLastName(){
        return getAttributeValue(by_last_name);
    }
    //ввод Фамилии латиницей
    public void setLastNameLat(String text){
        setValue(text,by_last_name_lat);
    }
    //получение Фамилии латиницей
    public String getLastNameLat(){
        return getAttributeValue(by_last_name_lat);
    }
    //ввод Имени для блога
    public void setBlogName(String text){
        setValue(text,by_blog_name);
    }
    //получение Имени для блога
    public String getBlogName() {
        return getAttributeValue(by_blog_name);
    }
    //ввод даты рождения
    public void setDateBirth(String text){
            setValue(text,by_date_birth);
            driver.findElement(by_date_birth).sendKeys(Keys.ENTER);//выход из календаря
    }
    //получение даты рождения
    public String getDateBirth() {
        return getAttributeValue(by_date_birth);
    }
    //выбор страны
    public void setCountry(String text){
        String text_format=  String.format(css_country2,text);
        By by_country2=By.cssSelector(text_format);
        setValueSelect(by_country,by_country2);
    }
    //получение страны
    public String getCountry() {
        return getText(by_country);
    }
    //выбор города
    public void setCity(String text){
        String text_format=  String.format(css_city2,text);
        By by_city2=By.cssSelector(text_format);
        setValueSelect(by_city,by_city2);
    }
    //получение города
    public String getCity() {
        return getText(by_city);
    }
    //выбор уровня владения английским языком
    public void setLang(String text){
        String text_format=  String.format(css_english2,text);
        By by_english2=By.cssSelector(text_format);
        setValueSelect(by_english,by_english2);
    }
    //получение уровня владения английским языком
    public String getLang() {
        return getText(by_english);
    }
    //отметка формата работы, где number - порядковый номер формата, начиная с 0
    public void setFormatJob(int number){
        if (!getFormatJob(number)) {
            WebElement element = driver.findElements(by_set_check_format_work).get(number);
            element.click();
        }
    }
    //получение уровня владения английским языком
    public Boolean getFormatJob(int number) {
        WebElement element=driver.findElements(by_get_check_format_work).get(number);
        return element.isSelected();
    }

    //ввод компании
    public void setComppany(String text){
        setValue(text,by_company);
    }
    //получение компании
    public String getComppany() {
        return getAttributeValue(by_company);
    }
    //ввод должности
    public void setPost(String text){
        setValue(text,by_post);
    }
    //получение должности
    public String getPost() {
        return getAttributeValue(by_post);
    }

    //Добавление контакта, где number - номер добавляемого контакта, с нуля, name_contact - имя добавляемого контакта, value - значение контакта
    public void setContact(String number, String name_contact, String value){
        String xpath_contact1_format=  String.format(xpath_contact,number);
        By by_contact_vk=By.xpath(xpath_contact1_format);
        String css_contact2_format=String.format(css_contact_2,number,name_contact);
        By by_contact_2=By.cssSelector(css_contact2_format);
        String name_contact_format=String.format(name_contact_3,number);
        By by_contact_3=By.name(name_contact_format);
        addContact(by_contact_vk,by_contact_2,by_contact_3,value);
    }

    //Добавление контакта, где by1 - раскрытие выпадащего списка, by2 - выбор значения, by3 - поле для ввода значения контакта, text- текст для ввода
    private void addContact(By by1,By by2, By by3,String text){
        setValueSelect(by1,by2);
        setValue(text,by3);
    }
    //получение типа добавленного контакта, где number - порядковый номер контакта, с 0
    public String getTypeContact(String number) {
        String xpath_contact1_format=  String.format(xpath_contact,number);
        By by_contact_vk=By.xpath(xpath_contact1_format);
        return getText(by_contact_vk);
    }
    //получение значения в поле ввода добавленного контакта, где number - порядковый номер контакта, с 0
    public String getValueContact(String number) {
        String name_contact_format=String.format(name_contact_3,number);
        By by_contact_3=By.name(name_contact_format);
        return getAttributeValue(by_contact_3);
    }
    //ввод пола, где value - значение атрибута value
    public void setGender(String value){
        String text_format=  String.format(xpath_gender2,value);
        By by_gender2=By.xpath(text_format);
        setValueSelect(by_gender,by_gender2);
    }
    //получение пола
    public String getGender() {
        return getText(by_get_gender);
    }
    //нажатие Кнопки Сохранить и продолжить
    public void save(){
        click(by_save_and_continue);
        String mes=driver.findElement(by_save_message).getText();
        assertEquals(save_message,mes);
    }

}
