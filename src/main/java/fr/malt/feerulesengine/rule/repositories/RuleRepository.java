package fr.malt.feerulesengine.rule.repositories;

import fr.malt.feerulesengine.rule.model.BusinessRule;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RuleRepository extends MongoRepository<BusinessRule, String> {
    Optional<BusinessRule> findByName(String name);
}
