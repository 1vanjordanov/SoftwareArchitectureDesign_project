package mk.ukim.finki.dians.parking_application.repository;

import mk.ukim.finki.dians.parking_application.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {

    Optional<Parking> findByName(String name);

    Optional<Parking> findByRating(double rating);

    Optional<Parking> findByRatingGreaterThanEqual(double rating);

    Optional<Parking> findByRatingLessThanEqual(double rating);

    Optional<Parking> findTop5ByRating(double rating);

    List<Parking> findAllByAddressIgnoreCase(String address);

    List<Parking> findAllByCityIgnoreCase(String city);

    List<Parking> findAllByAddressAndCity(String address, String city);

    List<Parking> findAllByCityIgnoreCaseOrderByRatingDesc(String city);

    List<Parking> findAllByAddressIgnoreCaseOrderByRatingDesc(String address);

    List<Parking> findAllByAddressIgnoreCaseAndCityIgnoreCaseOrderByRatingDesc(String address, String city);

    List<Parking> findAllByCityIgnoreCaseOrderByName(String city);

    List<Parking> findAllByAddressIgnoreCaseOrderByName(String address);

    List<Parking> findAllByAddressIgnoreCaseAndCityIgnoreCaseOrderByName(String address, String city);

}
