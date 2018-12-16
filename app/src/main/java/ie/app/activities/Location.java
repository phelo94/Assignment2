package ie.app.activities;

public class Location {

    String locationId;
    String locationName;
    String LocationGenre;


    public Location(){


    }

    public Location(String locationId, String locationName, String locationGenre) {
        this.locationId = locationId;
        this.locationName = locationName;
        LocationGenre = locationGenre;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLocationGenre() {
        return LocationGenre;
    }
}
