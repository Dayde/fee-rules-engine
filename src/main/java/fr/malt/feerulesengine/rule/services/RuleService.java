package fr.malt.feerulesengine.rule.services;

import fr.malt.feerulesengine.rule.model.Rule;
import fr.malt.feerulesengine.rule.repositories.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }

    public Optional<Rule> findById(String id) {
        return ruleRepository.findById(id);
    }
}