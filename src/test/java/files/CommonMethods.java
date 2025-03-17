package files;

import io.restassured.path.json.JsonPath;

public class CommonMethods {

    public static JsonPath rawToJson(String text) {
        return JsonPath.from(text);
    }
}
