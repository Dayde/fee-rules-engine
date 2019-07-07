package fr.malt.feerulesengine.rule.services;

import fr.malt.feerulesengine.rule.exceptions.RuleAlreadyExistsException;
import fr.malt.feerulesengine.rule.model.BusinessRule;
import fr.malt.feerulesengine.rule.repositories.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    public List<BusinessRule> findAll() {
        return ruleRepository.findAll();
    }

    public Optional<BusinessRule> findById(String id) {
        return ruleRepository.findById(id);
    }

    public BusinessRule save(BusinessRule rule) {
        Optional<BusinessRule> optional = ruleRepository.findByName(rule.getName());
        optional.ifPresent(existingRule -> {
            String existingRuleId = existingRule.getId();
            if (!existingRuleId.equals(rule.getId())) {
                throw new RuleAlreadyExistsException(
                        String.format("Rule using same name already exists with id %s", existingRuleId));
            }
        });

        return ruleRepository.save(rule);
    }

    public BusinessRule saveWithId(String id, BusinessRule rule) {
        Optional<BusinessRule> optional = ruleRepository.findById(id);
        optional.ifPresentOrElse(existingRule -> {
            if (!existingRule.getId().equals(rule.getId())) {
                throw new IllegalArgumentException(
                        String.format(
                                "While updating, id in uri (\"%s\") should be equal to body id property (\"%s\"",
                                id, rule.getId()
                        ));
            }
        }, () -> {
            if (rule.getId() != null) {
                throw new IllegalArgumentException(
                        String.format(
                                "While inserting a new rule, body id property should not be set",
                                id, rule.getId()
                        ));
            }
        });
        rule.setId(id);
        return save(rule);
    }
}
