package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Utils {
    public RequestSpecification requestSpecification;

    public RequestSpecification getRequestSpecification(String stepText) {
        PrintStream log;
        try {
            log = new PrintStream(new FileOutputStream("logging.txt", true));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Add timestamp and step text before request logging
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.println("\n####### Request " + stepText + " - " + now.format(formatter));

        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(getGlobalValue("baseURL"))
                .addQueryParam("key", "qaclick123")

                //Log request
                .addFilter(RequestLoggingFilter.logRequestTo(log))

                //Log response
                .addFilter(new Filter() {
                    @Override
                    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
                        // Log "Response" with timestamp before the response
                        LocalDateTime responseTime = LocalDateTime.now();
                        log.println("\n####### Response " + stepText + " - " + responseTime.format(formatter));
                        return ctx.next(requestSpec, responseSpec);
                    }
                })
                .addFilter(ResponseLoggingFilter.logResponseTo(log))
                .setContentType(ContentType.JSON)
                .build();

        return requestSpecification;
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
}
