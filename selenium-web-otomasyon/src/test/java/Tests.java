import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Tests {
    public WebDriver driver;
    final static Logger logger = Logger.getLogger(Tests.class);

    @Before
    public void setUp() throws InterruptedException {
        Thread.sleep(10);

        logger.info("TEST BAŞLIYOR!");
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();


    }
    @Test
    public void correctOpen() throws InterruptedException {
        Thread.sleep(10);

        driver.get("https://www.gittigidiyor.com/");

        Assert.assertEquals(driver.getTitle(), "GittiGidiyor - Türkiye'nin Öncü Alışveriş Sitesi");

        logger.info("Ana sayfanın açıldığı kontrol ediliyor."); //

        driver.navigate().to("https://www.gittigidiyor.com/uye-girisi");
        driver.findElement(By.id("L-UserNameField")).sendKeys("USERNAME");
        driver.findElement(By.id("L-PasswordField")).sendKeys("PASSWORD");
        driver.findElement(By.id("gg-login-enter")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.gittigidiyor.com/");
        logger.info("Login işlemi kontrol ediliyor.");

        driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[1]/div[2]/input")).sendKeys("bilgisayar", Keys.RETURN);
        logger.info("Arama kutucuğuna bilgisayar kelimesi girilir.");

        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        Thread.sleep(500);

        driver.findElement(By.xpath("//*[@id=\"best-match-right\"]/div[5]/ul/li[2]/a")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.gittigidiyor.com/arama/?k=bilgisayar&sf=2");
        logger.info("Arama sonuçları sayfasından 2.sayfa açılır.");

        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        driver.findElement(By.id("item-info-block-666551478")).click();
        logger.info("2.Sıradaki ürün seçildi.");

        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");


        WebElement PPrice = driver.findElement(By.id("sp-price-lowPrice"));
        String Price = PPrice.getText();
        logger.info("Ürün Fiyatı : " + Price);

        driver.findElement(By.id("add-to-basket")).click();
        logger.info("Seçilen ürün sepete eklenir.");

        driver.findElement(By.xpath("//*[@id=\"header_wrapper\"]/div[4]/div[3]/a/div[1]")).click();
        logger.info("Sepetim");

        WebElement PBasketPrice = driver.findElement(By.xpath("//*[@id=\"cart-price-container\"]/div[3]/p"));
        String BasketPrice = PBasketPrice.getText();
        logger.info("Ürünün Sepetteki Fiyatı : " + BasketPrice);


        Assert.assertEquals(BasketPrice, Price);
        logger.info("Ürün sayfasındaki fiyat ile sepette yer alan ürün fiyatının doğruluğu karşılaştırılır.");

        Actions action = new Actions(driver);
        WebElement elem = driver.findElement(By.xpath("//*[@class='amount']"));

        action.moveToElement(elem).build().perform();
        action.contextClick(elem).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();

        logger.info("Adet arttırılarak ürün adedinin 2 olduğu doğrulanır.");

        driver.findElement(By.cssSelector(".btn-delete.btn-update-item.hidden-m")).click();
        logger.info("Ürün sepetten silindi.");

        Thread.sleep(500);

        WebElement BasketCheck = driver.findElement(By.xpath("//*[@id=\"cart-price-container\"]/div[3]/p"));
        String Check = BasketCheck.getText();

        if (Check.equals("")) {
            logger.info("Ürün sepetten silinerek sepetin boş olduğu kontrol edilir.");
        }}



    @After
    public void quit() {
        driver.quit();
        logger.info("TEST BAŞARIYLA TAMAMLANDI!");

    }
    }