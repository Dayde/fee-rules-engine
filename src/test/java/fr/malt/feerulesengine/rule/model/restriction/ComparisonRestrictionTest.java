package fr.malt.feerulesengine.rule.model.restriction;

import fr.malt.feerulesengine.fee.model.Mission;
import fr.malt.feerulesengine.fee.model.Person;
import fr.malt.feerulesengine.rule.model.restrtiction.ComparisonOperatorEnum;
import fr.malt.feerulesengine.rule.model.restrtiction.ComparisonRestriction;
import fr.malt.feerulesengine.rule.model.restrtiction.Restriction;
import org.junit.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class ComparisonRestrictionTest {

    @Test
    public void shouldBeOKWhenEQOperatorAndValuesMatch() {
        // given
        Restriction eq = new ComparisonRestriction("missionLength", ComparisonOperatorEnum.EQ, Duration.ofDays(20).toMillis());

        Mission mission = new Mission(null, null, Duration.ofDays(20).toMillis(), null);

        // when
        boolean fulfilled = eq.isFulfilledBy(mission);

        // then
        assertThat(fulfilled).isTrue();
    }

    @Test
    public void shouldNotBeOKWhenEQOperatorAndValuesDontMatch() {
        // given
        Restriction eq = new ComparisonRestriction("missionLength", ComparisonOperatorEnum.EQ, Duration.ofDays(30).toMillis());

        Mission mission = new Mission(null, null, Duration.ofDays(20).toMillis(), null);

        // when
        boolean fulfilled = eq.isFulfilledBy(mission);

        // then
        assertThat(fulfilled).isFalse();
    }

    @Test
    public void shouldBeOKWhenGTOperatorAndMissionLengthGreaterThanRuleValue() {
        // given
        Restriction eq = new ComparisonRestriction("missionLength", ComparisonOperatorEnum.GT, Duration.ofDays(20).toMillis());

        Mission mission = new Mission(null, null, Duration.ofDays(30).toMillis(), null);

        // when
        boolean fulfilled = eq.isFulfilledBy(mission);

        // then
        assertThat(fulfilled).isTrue();
    }

    @Test
    public void shouldNotBeOKWhenGTOperatorAndMissionLengthLesserThanRuleValue() {
        // given
        Restriction eq = new ComparisonRestriction("missionLength", ComparisonOperatorEnum.GT, Duration.ofDays(20).toMillis());

        Mission mission = new Mission(null, null, Duration.ofDays(10).toMillis(), null);

        // when
        boolean fulfilled = eq.isFulfilledBy(mission);

        // then
        assertThat(fulfilled).isFalse();
    }

    @Test
    public void shouldBeOKWhenLTOperatorAndMissionLengthLesserThanRuleValue() {
        // given
        Restriction eq = new ComparisonRestriction("missionLength", ComparisonOperatorEnum.LT, Duration.ofDays(20).toMillis());

        Mission mission = new Mission(null, null, Duration.ofDays(10).toMillis(), null);

        // when
        boolean fulfilled = eq.isFulfilledBy(mission);

        // then
        assertThat(fulfilled).isTrue();
    }

    @Test
    public void shouldNotBeOKWhenLTOperatorAndMissionLengthGreaterThanRuleValue() {
        // given
        Restriction eq = new ComparisonRestriction("missionLength", ComparisonOperatorEnum.LT, Duration.ofDays(20).toMillis());

        Mission mission = new Mission(null, null, Duration.ofDays(30).toMillis(), null);

        // when
        boolean fulfilled = eq.isFulfilledBy(mission);

        // then
        assertThat(fulfilled).isFalse();
    }


    @Test
    public void shouldBeOKWhenEQOperatorAndStringValuesMatch() {
        // given
        Restriction eq = new ComparisonRestriction("client.countryCode", ComparisonOperatorEnum.EQ, "FR");

        Mission mission = new Mission(new Person("FR"), null, 0, null);

        // when
        boolean fulfilled = eq.isFulfilledBy(mission);

        // then
        assertThat(fulfilled).isTrue();
    }

    @Test
    public void shouldNotBeOKWhenEQOperatorAndStringValuesDontMatch() {
        // given
        Restriction eq = new ComparisonRestriction("client.countryCode", ComparisonOperatorEnum.EQ, "FR");

        Mission mission = new Mission(new Person("ES"), null, 0, null);

        // when
        boolean fulfilled = eq.isFulfilledBy(mission);

        // then
        assertThat(fulfilled).isFalse();
    }

}
