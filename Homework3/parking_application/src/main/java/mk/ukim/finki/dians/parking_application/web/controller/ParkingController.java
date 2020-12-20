package mk.ukim.finki.dians.parking_application.web.controller;

import mk.ukim.finki.dians.parking_application.model.Parking;
import mk.ukim.finki.dians.parking_application.service.ParkingService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class ParkingController {
    private final ParkingService parkingService;


    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/locate")
    public String getLocatePage() {
        return "locate";
    }

    @PostMapping("/result")
    public String searchParking(@RequestParam(required = false) String city,
                                @RequestParam(required = false) String address,
                                @RequestParam(required = false) String sort,
                                Model model) {

        if (city.isEmpty() && address.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", "You must fill at least one field in order to search by city/address");
            model.addAttribute("currentLocation", "");
            return "/locate";
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
        return "results";
    }

    @GetMapping("/currentlocation")
    public String getCurrentLocation(@RequestParam String latlong, Model model) {
        if (latlong.equals("")) { //ne ja zemalo lokacijata
            return "locate"; //javascript ke vrati Geolocation is not supported by this browser
        }
        String[] coords = latlong.split(" ");
        double latitude = Double.parseDouble(coords[0]);
        double longitude = Double.parseDouble(coords[1]);
        List<Parking> result_parking = null;
        result_parking = parkingService.findByCurrentAddress(latitude, longitude);
        if (result_parking.isEmpty()) {
            return "notfoundparking";
        }
        model.addAttribute("parking", result_parking);
        model.addAttribute("currentLocation", "Sorted by shortest distance");
        return "results";
    }

}

