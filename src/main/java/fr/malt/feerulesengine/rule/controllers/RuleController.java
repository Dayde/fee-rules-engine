package fr.malt.feerulesengine.rule.controllers;

import fr.malt.feerulesengine.rule.model.Rule;
import fr.malt.feerulesengine.rule.services.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @GetMapping("/rules")
    public List<Rule> findAll() {
        return ruleService.findAll();
    }
}
