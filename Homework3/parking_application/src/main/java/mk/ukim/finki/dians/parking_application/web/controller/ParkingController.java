package mk.ukim.finki.dians.parking_application.web.controller;

import mk.ukim.finki.dians.parking_application.model.Parking;
import mk.ukim.finki.dians.parking_application.service.ParkingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/parking")
public class ParkingController {
    private final ParkingService parkingService;


    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/locate")
    public String getLocatePage(Model model) {
        model.addAttribute("bodyContent", "locate");
        return "master-template";
    }

    @PostMapping("/result")
    public String searchParking(@RequestParam(required = false) String city,
                                @RequestParam(required = false) String address,
                                @RequestParam(required = false) String sort,
                                Model model) {

        if  (city=="" && address==""){ // stavi go ova ->if (city.isEmpty() && address.isEmpty())
            model.addAttribute("hasError", true);
            model.addAttribute("error", "You must fill at least one field in order to search by city/address");
            model.addAttribute("currentLocation", "");
            model.addAttribute("bodyContent", "locate");
            return "master-template";
        }
        List<Parking> result_parking = null;

        if (!city.isEmpty() && !address.isEmpty()) {
            if (sort.equals("name")) {
                result_parking = parkingService.findAllByAddressAndCityOrderByName(address, city);
            } else {
                result_parking = parkingService.findAllByAddressAndCityOrderByRatingDesc(address, city);
            }
        } else if (!city.isEmpty()) {
            if (sort.equals("name")) {
                result_parking = parkingService.findAllByCityOrderByName(city);
            } else {
                result_parking = parkingService.findAllByCityOrderByRatingDesc(city);
            }
        } else if (!address.isEmpty()) {
            if (sort.equals("name")) {
                result_parking = parkingService.findAllByAddressOrderByName(address);
            } else {
                result_parking = parkingService.findAllByAddressOrderByRatingDesc(address);
            }
        }
        if (result_parking.isEmpty()) {
            return "notfoundparking";
        }
        model.addAttribute("parking", result_parking);
        model.addAttribute("currentLocation", "");

        model.addAttribute("bodyContent", "results");
        return "master-template";
    }

    @GetMapping("/currentlocation")
    public String getCurrentLocation(@RequestParam String latlong, Model model) {
        if (latlong.equals("")) { //ne ja zemalo lokacijata
            model.addAttribute("bodyContent", "locate");
            return "master-template"; //javascript ke vrati Geolocation is not supported by this browser
        }
        String[] coords = latlong.split(" ");
        double latitude = Double.parseDouble(coords[0]);
        double longitude = Double.parseDouble(coords[1]);
        List<Parking> resultParking = null;
        resultParking = parkingService.findByCurrentAddress(latitude, longitude);
        if (resultParking.isEmpty()) {
            return "notfoundparking";
        }
        model.addAttribute("parking", resultParking);
        model.addAttribute("currentLocation", "Sorted by shortest distance");
        model.addAttribute("bodyContent", "results");
        return "master-template";
    }

    @GetMapping("/allparkings")
    public String getParkingPage(Model model) {
        List<Parking> allParkings = parkingService.findAll();
        model.addAttribute("parking", allParkings);
        model.addAttribute("bodyContent", "allparkings");
        return "master-template";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteParking(@PathVariable Long id) {
        this.parkingService.deleteById(id);
        return "redirect:/parking/allparkings";
    }

    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editParkingPage(@PathVariable Long id, Model model) {

        Optional<Parking> parkingOpt = this.parkingService.findById(id);

        if (parkingOpt.isPresent()) {

            Parking parking = parkingOpt.get();
            model.addAttribute("parking", parking);
            model.addAttribute("bodyContent", "add-parking");
            return "master-template";
        }

        return "notfoundparking";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addParkingPage(Model model) {
        model.addAttribute("bodyContent", "add-parking");
        return "master-template";
    }

    @PostMapping("/add")
    public String saveParking(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String city,
            @RequestParam String address,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam String rating) {

        if (id != null) {
            this.parkingService.edit(id, name, city, address, latitude, longitude, rating);
        } else {
            this.parkingService.save(name, city, address, latitude, longitude, rating);
        }
        return "redirect:/parking/allparkings";
    }

}

