package pustrace.elibraryjavaparser;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class DataFetcher {

    public static Response fetchPageData(String url, Map<String, String> cookies) {
        return RestAssured.given()
                .cookies(cookies)
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
                .get(url);
    }
}
