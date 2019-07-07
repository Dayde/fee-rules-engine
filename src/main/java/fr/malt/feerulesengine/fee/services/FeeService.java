package fr.malt.feerulesengine.fee.services;

import fr.malt.feerulesengine.fee.model.Fee;
import fr.malt.feerulesengine.fee.model.Mission;
import fr.malt.feerulesengine.rule.model.BusinessRule;
import fr.malt.feerulesengine.rule.services.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeeService {

    public static final int DEFAULT_FEE = 10;

    @Autowired
    private RuleService ruleService;

    public Fee computeFee(Mission mission) {
        List<BusinessRule> rules = ruleService.findAll();

        return rules.stream()
                .filter(rule -> rule.getRestriction().isFulfilledBy(mission))
                .sorted()
                .findFirst()
                .map(rule -> new Fee(rule.getFee(), rule.getName()))
                .orElse(new Fee(DEFAULT_FEE));
    }
}
