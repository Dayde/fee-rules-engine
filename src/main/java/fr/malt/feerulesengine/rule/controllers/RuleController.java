package fr.malt.feerulesengine.rule.controllers;

import fr.malt.feerulesengine.rule.exceptions.RuleAlreadyExistsException;
import fr.malt.feerulesengine.rule.model.BusinessRule;
import fr.malt.feerulesengine.rule.services.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @GetMapping("/rules")
    public List<BusinessRule> findAll() {
        return ruleService.findAll();
    }

    @GetMapping("/rules/{id}")
    public BusinessRule findById(@PathVariable String id) {
        return ruleService.findById(id)
                .orElseThrow();
    }

    @PostMapping("/rules")
    public ResponseEntity<BusinessRule> save(@RequestBody BusinessRule rule) {
        HttpStatus status = rule.getId() == null ? HttpStatus.CREATED : HttpStatus.OK;
        BusinessRule savedRule = ruleService.save(rule);
        return new ResponseEntity<>(savedRule, status);
    }

    @PutMapping("/rules/{id}")
    public ResponseEntity<BusinessRule> upsert(@PathVariable String id, @RequestBody BusinessRule rule) {
        HttpStatus status = rule.getId() == null ? HttpStatus.CREATED : HttpStatus.OK;
        BusinessRule savedRule = ruleService.saveWithId(id, rule);
        return new ResponseEntity<>(savedRule, status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badRequest(Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound() {
        return "Rule not found";
    }

    @ExceptionHandler(RuleAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String conflict(Exception ex) {
        return ex.getMessage();
    }
}
