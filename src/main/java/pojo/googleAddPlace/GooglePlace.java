package pojo.googleAddPlace;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GooglePlace {
    private GoogleLocation location;
    private int accuracy;
    private String name;
    private String phoneNumber;
    private String address;
    private List<String> types;
    private String website;
    private String language;
}
