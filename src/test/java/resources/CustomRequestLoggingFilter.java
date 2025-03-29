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
public class CustomRequestLoggingFilter implements Filter {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final PrintStream logStream;
    private final String stepText;

    public CustomRequestLoggingFilter(PrintStream logStream, String stepText) {
        this.logStream = logStream;
        this.stepText = stepText;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        LocalDateTime now = LocalDateTime.now();

        // Add custom log before the request
        logStream.println("\n####### Request " + stepText + " - " + now.format(formatter));
        return ctx.next(requestSpec, responseSpec);
    }
}
