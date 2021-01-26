package mk.ukim.finki.dians.parking_application.service.implementations;

import mk.ukim.finki.dians.parking_application.model.Parking;
import mk.ukim.finki.dians.parking_application.model.exceptions.ParkingNotFoundException;
import mk.ukim.finki.dians.parking_application.repository.ParkingRepository;
import mk.ukim.finki.dians.parking_application.service.ParkingService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingServiceImpl implements ParkingService {
    private final ParkingRepository parkingRepository;

    public ParkingServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public List<Parking> findAll() {
        return this.parkingRepository.findAll();
    }

    @Override
    public Optional<Parking> findById(Long id) {
        return this.parkingRepository.findById(id);
    }

    @Override
    public Optional<Parking> findByName(String name) {
        return this.parkingRepository.findByName(name);
    }

    @Override
    public void deleteById(Long id) {
        this.parkingRepository.deleteById(id);
    }

    @Override
    public Optional<Parking> findByRating(double rating) {
        return this.parkingRepository.findByRating(rating);
    }

    @Override
    public Optional<Parking> findByRatingGreaterThanEqual(double rating) {
        return this.parkingRepository.findByRatingGreaterThanEqual(rating);
    }

    @Override
    public Optional<Parking> findByRatingLessThanEqual(double rating) {
        return this.parkingRepository.findByRatingLessThanEqual(rating);
    }

    @Override
    public Optional<Parking> findTop5ByRating(double rating) {
        return this.parkingRepository.findTop5ByRating(rating);
    }

    @Override
    public List<Parking> findByLocationAddress(String address) {
        return parkingRepository.findAllByAddressIgnoreCase(address);
    }

    @Override
    public List<Parking> findByLocationCity(String city) {
        return parkingRepository.findAllByCityIgnoreCase(city);
    }

    @Override
    public List<Parking> findByLocationAddressInCity(String address, String city) {
        return parkingRepository.findAllByAddressAndCity(address, city);
    }
    public List<Parking> findByCurrentAddress(Double currentlat, Double currentlng) {
        return parkingRepository.findAll().stream().filter(parking -> haversineDistance(parking.getLatitude(), parking.getLongitude(), currentlat, currentlng)<3).
                sorted(Comparator.comparing(parking -> haversineDistance(parking.getLatitude(), parking.getLongitude(), currentlat, currentlng)))
                .collect(Collectors.toList());
        //less then 3 km
    }

    private static double haversineDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
        final double R = 6371.0088; // Earth's radius Km
        Double latDistance = toRad(lat2 - lat1);
        Double lonDistance = toRad(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        Double distance = R * c; //distance in kilometers
        return distance;
    }

    private static Double toRad(Double value) {
        return value * Math.PI / 180;
    }

    @Override
    public List<Parking> findAllByCityOrderByRatingDesc(String city) {
        return parkingRepository.findAllByCityIgnoreCaseOrderByRatingDesc(city);
    }

    @Override
    public List<Parking> findAllByAddressOrderByRatingDesc(String address) {
        return parkingRepository.findAllByAddressIgnoreCaseOrderByRatingDesc(address);
    }

    @Override
    public List<Parking> findAllByAddressAndCityOrderByRatingDesc(String address, String city) {
        return parkingRepository.findAllByAddressIgnoreCaseAndCityIgnoreCaseOrderByRatingDesc(address,city);
    }

    @Override
    public List<Parking> findAllByCityOrderByName(String city) {
        return parkingRepository.findAllByCityIgnoreCaseOrderByName(city);
    }

    @Override
    public List<Parking> findAllByAddressOrderByName(String address) {
        return parkingRepository.findAllByAddressIgnoreCaseOrderByName(address);
    }

    @Override
    public List<Parking> findAllByAddressAndCityOrderByName(String address, String city) {
        return parkingRepository.findAllByAddressIgnoreCaseAndCityIgnoreCaseOrderByName(address,city);
    }

    @Override
    public Optional<Parking> save(String name, String city, String address, Double latitude, Double longitude, String rating) {
        return Optional.of(this.parkingRepository.save(new Parking(name, city, address, latitude, longitude, rating)));
    }

    @Override
    public Optional<Parking> edit(Long id, String name, String city, String address, Double latitude, Double longitude, String rating) {
        Parking parking = this.parkingRepository.findById(id).orElseThrow(() -> new ParkingNotFoundException(id));

        parking.setName(name);
        parking.setCity(city);
        parking.setAddress(address);
        parking.setLatitude(latitude);
        parking.setLongitude(longitude);
        parking.setRating(rating);

        return Optional.of(this.parkingRepository.save(parking));
    }
}
