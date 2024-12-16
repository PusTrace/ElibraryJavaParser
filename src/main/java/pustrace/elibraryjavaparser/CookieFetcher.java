package pustrace.elibraryjavaparser;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * Класс для получения cookies с сайта с использованием Selenium WebDriver.
 * Включает решение капчи, если она появляется.
 */
public class CookieFetcher {

    private int captchaDelay;
    /**
     * Конструктор для инициализации задержки капчи.
     *
     * @param captchaDelay Задержка для ожидания решения капчи в миллисекундах.
     */
    public CookieFetcher(int captchaDelay) {
        this.captchaDelay = captchaDelay;
    }
    /**
     * Получает cookies с сайта "https://elibrary.ru/defaultx.asp".
     * Выполняет задержку для решения капчи.
     *
     * @return Карта cookies, где ключом является имя cookie, а значением — его значение.
     */
    public Map<String, String> fetchCookies() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        Map<String, String> cookieMap = new HashMap<>();

        try {
            driver.get("https://elibrary.ru/defaultx.asp");

            // Задержка для решения капчи
            Thread.sleep(captchaDelay);

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            Set<Cookie> cookies = driver.manage().getCookies();
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return cookieMap;
    }
}
