package fr.malt.feerulesengine.fee.services;

import fr.malt.feerulesengine.fee.model.CommercialRelation;
import fr.malt.feerulesengine.fee.model.Fee;
import fr.malt.feerulesengine.fee.model.Mission;
import fr.malt.feerulesengine.fee.model.Person;
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
}
