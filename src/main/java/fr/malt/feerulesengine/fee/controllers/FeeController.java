package fr.malt.feerulesengine.fee.controllers;

import fr.malt.feerulesengine.fee.model.Fee;
import fr.malt.feerulesengine.fee.model.Mission;
import fr.malt.feerulesengine.fee.services.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeeController {

    @Autowired
    private FeeService feeService;

    @PostMapping("/fee")
    public Fee computeFee(@RequestBody Mission mission) {
        return feeService.computeFee(mission);
    }
}
