package fr.malt.feerulesengine.fee.services;

import fr.malt.feerulesengine.fee.model.CommercialRelation;
import fr.malt.feerulesengine.fee.model.Fee;
import fr.malt.feerulesengine.fee.model.Mission;
import fr.malt.feerulesengine.fee.model.Person;
import fr.malt.feerulesengine.rule.model.BusinessRule;
import fr.malt.feerulesengine.rule.model.restrtiction.ComparisonOperatorEnum;
import fr.malt.feerulesengine.rule.model.restrtiction.ComparisonRestriction;
import fr.malt.feerulesengine.rule.model.restrtiction.Restriction;
import fr.malt.feerulesengine.rule.services.RuleService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.joda.time.DateTime.now;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FeeServiceTest {

    @Mock
    private RuleService ruleService;

    @InjectMocks
    private FeeService feeService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldReturnDefaultFeeIfNoRuleExist() {
        // given
        Person client = new Person("FR");
        Person freelancer = new Person("FR");
        long missionLength = Duration.ofDays(20).toMillis();
        CommercialRelation commercialRelation = new CommercialRelation(now().minusMonths(4), now().minusDays(2));
        Mission mission = new Mission(client, freelancer, missionLength, commercialRelation);
        when(ruleService.findAll()).thenReturn(Collections.emptyList());

        // when
        Fee result = feeService.computeFee(mission);

        // then
        assertThat(result.getFee()).isEqualTo(FeeService.DEFAULT_FEE);
        assertThat(result.getReason()).isNull();
        verify(ruleService).findAll();
        verifyNoMoreInteractions(ruleService);
    }

    @Test
    public void shouldReturnFeeOfMatchedRule() {
        // given
        Person client = new Person("FR");
        Person freelancer = new Person("FR");
        long missionLength = Duration.ofDays(20).toMillis();
        CommercialRelation commercialRelation = new CommercialRelation(now().minusMonths(4), now().minusDays(2));
        Mission mission = new Mission(client, freelancer, missionLength, commercialRelation);
        Restriction restriction = new ComparisonRestriction(
                "missionLength",
                ComparisonOperatorEnum.GT,
                Duration.ofDays(2).toMillis());
        float fee = 8;
        String ruleName = "name";
        when(ruleService.findAll()).thenReturn(List.of(new BusinessRule("id", ruleName, fee, restriction)));

        // when
        Fee result = feeService.computeFee(mission);

        // then
        assertThat(result.getFee()).isEqualTo(fee);
        assertThat(result.getReason()).isEqualTo(ruleName);
        verify(ruleService).findAll();
        verifyNoMoreInteractions(ruleService);
    }

    @Test
    public void shouldReturnLowestFeeOfMatchedRules() {
        // given
        Person client = new Person("FR");
        Person freelancer = new Person("FR");
        long missionLength = Duration.ofDays(20).toMillis();
        CommercialRelation commercialRelation = new CommercialRelation(now().minusMonths(4), now().minusDays(2));
        Mission mission = new Mission(client, freelancer, missionLength, commercialRelation);

        Restriction restriction = new ComparisonRestriction(
                "missionLength",
                ComparisonOperatorEnum.GT,
                Duration.ofDays(2).toMillis());
        float fee = 8;
        String ruleName = "name";
        BusinessRule rule1 = new BusinessRule("id", ruleName, fee, restriction);

        Restriction restriction2 = new ComparisonRestriction(
                "missionLength",
                ComparisonOperatorEnum.GT,
                Duration.ofDays(4).toMillis());
        float fee2 = 6;
        String ruleName2 = "name2";
        BusinessRule rule2 = new BusinessRule("id2", ruleName2, fee2, restriction2);
        when(ruleService.findAll()).thenReturn(List.of(rule1, rule2));

        // when
        Fee result = feeService.computeFee(mission);

        // then
        assertThat(result.getFee()).isEqualTo(fee2);
        assertThat(result.getReason()).isEqualTo(ruleName2);
        verify(ruleService).findAll();
        verifyNoMoreInteractions(ruleService);
    }
}
