package ru.yandex.practicum.tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.practicum.utils.Constants;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setUp() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        if (browser.equals("chrome")) {
            startChrome();
        } else if (browser.equals("firefox")) {
            startFirefox();
        } else {
            throw new RuntimeException("Браузер " + browser + " не поддерживается");
        }

        // Устанавливаем таймаут загрузки страницы 60 секунд
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.get(Constants.BASE_URL);
    }

    private void startChrome() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
    }

    private void startFirefox() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}