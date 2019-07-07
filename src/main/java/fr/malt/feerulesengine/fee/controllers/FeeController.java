package fr.malt.feerulesengine.fee.controllers;

import fr.malt.feerulesengine.fee.model.Fee;
import fr.malt.feerulesengine.fee.model.Mission;
import fr.malt.feerulesengine.fee.model.Person;
import fr.malt.feerulesengine.fee.services.FeeService;
import fr.malt.feerulesengine.geolocation.ExternalServiceException;
import fr.malt.feerulesengine.geolocation.GeolocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class FeeController {

    @Autowired
    private FeeService feeService;

    @Autowired
    private GeolocationService geolocationService;


    @PostMapping("/fee")
    public Fee computeFee(@RequestBody Mission mission) {
        setCountryCodeUsingIp(mission.getClient());
        setCountryCodeUsingIp(mission.getFreelancer());

        return feeService.computeFee(mission);
    }

    private void setCountryCodeUsingIp(Person person) {
        person.setCountryCode(geolocationService.ipToCountryCode(person.getIp()));
    }

    @ExceptionHandler(ExternalServiceException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public String serviceUnavailable(Exception ex) {
        return ex.getMessage();
    }
}
