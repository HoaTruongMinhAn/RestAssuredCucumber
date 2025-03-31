package resources;

import pojo.googleAddPlace.GoogleLocation;
import pojo.googleAddPlace.GooglePlace;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuilder {
    public GooglePlace buildAddPlacePayload(String name, String language, String address) {
        GooglePlace googlePlace = new GooglePlace();
        googlePlace.setAccuracy(55);
        googlePlace.setName(name);
        googlePlace.setPhoneNumber("(+91) 983 893 3937");
        googlePlace.setAddress(address);
        googlePlace.setWebsite("http://google.com");
        googlePlace.setLanguage(language);

        GoogleLocation googleLocation = new GoogleLocation();
        googleLocation.setLat(-38.383494);
        googleLocation.setLng(33.427362);
        googlePlace.setLocation(googleLocation);

        List<String> placeTypes = new ArrayList<String>();
        placeTypes.add("shoe park");
        placeTypes.add("shop");
        googlePlace.setTypes(placeTypes);

        return googlePlace;
    }

    public String buildDeletePlacePayload(String placeId) {
        String result = """
                {
                    "place_id":\"""" + placeId + """
                "
                }""";
        return result;
    }
}
