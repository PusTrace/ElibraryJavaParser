package pustrace.elibraryjavaparser;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;
/**
 * Класс для получения данных о страницах с использованием REST API.
 */
public class DataFetcher {
    /**
     * Извлекает данные страницы по URL с использованием cookies.
     *
     * @param url     URL страницы для получения данных.
     * @param cookies Карта cookies для запроса.
     * @return Ответ в виде объекта Response.
     */
    public static Response fetchPageData(String url, Map<String, String> cookies) {
        return RestAssured.given()
                .cookies(cookies)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
                .get(url);
    }
}
