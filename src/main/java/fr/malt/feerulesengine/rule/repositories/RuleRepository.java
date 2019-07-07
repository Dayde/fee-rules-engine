package fr.malt.feerulesengine.rule.repositories;

import fr.malt.feerulesengine.rule.model.Rule;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RuleRepository extends MongoRepository<Rule, String> {
}
