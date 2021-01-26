package mk.ukim.finki.dians.parking_application.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "parkings")
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private String address;
    private Double latitude;
    private Double longitude;
    private String rating;
    private String mapUrl;


    public Parking() {

    }

    public Parking(String name, String city, String address, Double latitude, Double longitude, String rating) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
    }

    public String getMapUrl() {
        return mapUrl;
    }

}
