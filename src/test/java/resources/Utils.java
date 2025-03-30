package resources;

import files.CommonMethods;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Properties;

public class Utils {
    //    private RequestSpecification requestSpecification;
    private PrintStream log;

    public RequestSpecification getRequestSpecification(String stepText) {
        try {
            log = new PrintStream(new FileOutputStream("logging.txt", true));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        RequestSpecification spec = new RequestSpecBuilder()
                .setBaseUri(getGlobalValue("baseURL"))
                .addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON)
                .build();

        return given().spec(spec)
                .filter(new CustomRequestLoggingFilter(log, stepText))
                .filter(RequestLoggingFilter.logRequestTo(log))
                .filter(new CustomResponseLoggingFilter(log, stepText))
                .filter(ResponseLoggingFilter.logResponseTo(log));
    }

    private RequestSpecification given() {
        return io.restassured.RestAssured.given();
    }

    public String getGlobalValue(String key) {
        String result = null;
        try {
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream("src/test/java/resources/global.properties");
            prop.load(fis);
            result = prop.getProperty(key);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getResponseValue(Response response, String keyPath) {
        JsonPath jsonPath = CommonMethods.rawToJson(response.asString());
        return jsonPath.get(keyPath).toString();
    }
}
