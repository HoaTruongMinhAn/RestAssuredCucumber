package files;

public class Payload {

    public static String addPlaceBody() {
        return """
                {
                  "location": {
                	"lat": -38.383494,
                	"lng": 33.427362
                  },
                  "accuracy": 50,
                  "name": "Frontline house",
                  "phone_number": "(+91) 983 893 3937",
                  "address": "29, side layout, cohen 09",
                  "types": [
                	"shoe park",
                	"shop"
                  ],
                  "website": "http://google.com",
                  "language": "French-IN"
                }
                """;
    }

    public static String updatePlaceBody(String placeId, String newAddress) {
        String result = """
                {
                "place_id":\"""" + placeId + """
                ",
                "address":\"""" + newAddress + """
                ",
                "key":"qaclick123"
                }
                """;
        return result;
    }

    public static String coursePrice() {
        String result = """
                {
                  "dashboard": {
                    "purchaseAmount": 910,
                    "website": "rahulshettyacademy.com"
                  },
                  "courses": [
                    {
                      "title": "Selenium Python",
                      "price": 50,
                      "copies": 6
                    },
                    {
                      "title": "Cypress",
                      "price": 40,
                      "copies": 4
                    },
                    {
                      "title": "RPA",
                      "price": 45,
                      "copies": 10
                    }
                  ]
                }
                """;
        return result;
    }

    public static String addBook() {
        String result = """
                {
                "name":"Learn Appium Automation with Java",
                "isbn":"hoa",
                "aisle":"0004",
                "author":"John foer"
                }
                """;
        return result;
    }

    public static String addBook(String name, String isbn, String aisle, String author) {
        String result = """
                {
                "name":\"""" + name + """
                ",
                "isbn":\"""" + isbn + """
                ",
                "aisle":\"""" + aisle + """
                ",
                "author":\"""" + author + """
                "
                }""";
        return result;
    }

    public static String deleteBook(String isbn, String aisle) {
        String result = """
                {
                    "ID": \"""" + isbn + aisle + """
                "
                }""";
        return result;
    }

    public static String getCreateIssuePayload(String projectKey, String issueType, String issueSummary) {
        String result = """
                {
                "fields": {
                "project":
                {
                "key": \"""" + projectKey + """
                "
                },
                "summary": \"""" + issueSummary + """
                ",
                "issuetype": {
                "name": \"""" + issueType + """
                "
                }
                }
                }""";
        return result;
    }

}
