package resources;

import pojo.googleAddPlace.GoogleLocation;
import pojo.googleAddPlace.GooglePlace;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuilder {
    public GooglePlace buildAddPlacePayload() {
        GooglePlace googlePlace = new GooglePlace();
        googlePlace.setAccuracy(55);
        googlePlace.setName("Frontline house");
        googlePlace.setPhoneNumber("(+91) 983 893 3937");
        googlePlace.setAddress("29, side layout, cohen 09");
        googlePlace.setWebsite("http://google.com");
        googlePlace.setLanguage("French-IN");

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
}
