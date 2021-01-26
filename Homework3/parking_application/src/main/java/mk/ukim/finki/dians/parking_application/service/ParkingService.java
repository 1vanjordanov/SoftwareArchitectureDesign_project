package mk.ukim.finki.dians.parking_application.service;

import mk.ukim.finki.dians.parking_application.model.Parking;

import java.util.List;
import java.util.Optional;

public interface ParkingService {
    List<Parking> findAll();

    Optional<Parking> findById(Long id);

    Optional<Parking> findByName(String name);

    void deleteById(Long id);

    Optional<Parking> findByRating(double rating);

    Optional<Parking> findByRatingGreaterThanEqual(double rating);

    Optional<Parking> findByRatingLessThanEqual(double rating);

    Optional<Parking> findTop5ByRating(double rating);

    List<Parking> findByLocationAddress(String text);

    List<Parking> findByLocationCity(String text);

    List<Parking> findByLocationAddressInCity(String address, String city);

    List<Parking> findByCurrentAddress(Double currentlat, Double currentlng);

    List<Parking> findAllByCityOrderByRatingDesc(String city);

    List<Parking> findAllByAddressOrderByRatingDesc(String address);

    List<Parking> findAllByAddressAndCityOrderByRatingDesc(String address, String city);

    List<Parking> findAllByCityOrderByName(String city);

    List<Parking> findAllByAddressOrderByName(String address);

    List<Parking> findAllByAddressAndCityOrderByName(String address, String city);

    Optional<Parking> save(String name, String city, String address, Double latitude, Double longitude, String rating);

    Optional<Parking> edit(Long id, String name, String city, String address, Double latitude, Double longitude, String rating);


}