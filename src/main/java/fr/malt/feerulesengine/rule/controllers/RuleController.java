package fr.malt.feerulesengine.rule.controllers;

import fr.malt.feerulesengine.rule.model.Rule;
import fr.malt.feerulesengine.rule.services.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @GetMapping("/rules")
    public List<Rule> findAll() {
        return ruleService.findAll();
    }

    @GetMapping("/rules/{id}")
    public Rule findById(@PathVariable String id) {
        return ruleService.findById(id)
                .orElseThrow();
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound() {
        return "Rule not found";
    }
}
