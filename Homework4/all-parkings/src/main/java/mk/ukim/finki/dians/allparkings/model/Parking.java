package mk.ukim.finki.dians.allparkings.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Class for the Parking Entity
 * Contains all of the entity fields and their
 * declaration, the class constructor and/or setters/getters.
 */
@Data
@Entity
@Table(name = "parkings")
public class Parking {

    //name, desc, rating
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

/*    public Parking(String parkingId, String name) {
        this.parkingId = parkingId;
        this.name = name;
    }*/

    public Parking(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}