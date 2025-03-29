package resources;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Custom filter for logging request with timestamp and step text
public class CustomResponseLoggingFilter implements Filter {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final PrintStream logStream;
    private final String stepText;

    public CustomResponseLoggingFilter(PrintStream logStream, String stepText) {
        this.logStream = logStream;
        this.stepText = stepText;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        LocalDateTime responseTime = LocalDateTime.now();

        // Add custom log before the response
        logStream.println("\n####### Response " + stepText + " - " + responseTime.format(formatter));
        return ctx.next(requestSpec, responseSpec);
    }
}
