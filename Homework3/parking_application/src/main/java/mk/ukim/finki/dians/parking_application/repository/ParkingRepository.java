package mk.ukim.finki.dians.parking_application.repository;

import mk.ukim.finki.dians.parking_application.model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {

    List<Parking> findAllByCityIgnoreCaseContainsOrderByName(String city);

    List<Parking> findAllByCityIgnoreCaseContainsOrderByRatingDesc(String city);

    List<Parking> findAllByAddressIgnoreCaseContainsOrderByName(String address);

    List<Parking> findAllByAddressIgnoreCaseContainsOrderByRatingDesc(String address);

    List<Parking> findAllByAddressIgnoreCaseContainsAndCityIgnoreCaseContainsOrderByName(String address, String city);

    List<Parking> findAllByAddressIgnoreCaseContainsAndCityIgnoreCaseContainsOrderByRatingDesc(String address, String city);

}
