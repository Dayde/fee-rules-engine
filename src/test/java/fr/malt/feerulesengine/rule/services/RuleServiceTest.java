package fr.malt.feerulesengine.rule.services;

import fr.malt.feerulesengine.rule.model.Rule;
import fr.malt.feerulesengine.rule.repositories.RuleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RuleServiceTest {

    @Mock
    private RuleRepository repository;

    @InjectMocks
    private RuleService ruleService;

    @Test
    public void shouldReturnAllRulesFoundInRepository() {
        // given
        Rule rule = mock(Rule.class);
        when(repository.findAll()).thenReturn(List.of(rule));

        // when
        List<Rule> result = ruleService.findAll();

        // then
        assertThat(result).containsExactly(rule);
    }

    @Test
    public void shouldReturnRuleFoundInRepository() {
        // given
        Optional<Rule> rule = ofNullable(mock(Rule.class));
        String id = "id";
        when(repository.findById(id)).thenReturn(rule);

        // when
        Optional<Rule> result = ruleService.findById(id);

        // then
        assertThat(result).isEqualTo(rule);
    }

}
