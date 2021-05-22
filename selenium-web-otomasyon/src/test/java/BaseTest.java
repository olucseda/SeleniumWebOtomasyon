import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
    protected WebDriver driver ;

    @BeforeAll
    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver.exe");
        System.out.println("Test başlıyor..");
    }

    @BeforeEach
    public void beforeMethod(){
        driver = new ChromeDriver();
    }

    @AfterEach
    public void afterMethod(){
        driver.quit();
    }



}
