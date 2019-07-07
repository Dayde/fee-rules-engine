package fr.malt.feerulesengine.rule.model.restriction;

import fr.malt.feerulesengine.fee.model.Mission;
import fr.malt.feerulesengine.rule.model.restrtiction.BooleanOperatorEnum;
import fr.malt.feerulesengine.rule.model.restrtiction.BooleanRestriction;
import fr.malt.feerulesengine.rule.model.restrtiction.Restriction;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BooleanRestrictionTest {

    @Test
    public void shouldBeFulfilledWhenAllAreFulfilledWithAndOperator() {
        // given
        Restriction restriction1 = mock(Restriction.class);
        Restriction restriction2 = mock(Restriction.class);
        Restriction and = new BooleanRestriction(BooleanOperatorEnum.AND, List.of(restriction1, restriction2));

        Mission mission = mock(Mission.class);

        when(restriction1.isFulfilledBy(mission)).thenReturn(true);
        when(restriction2.isFulfilledBy(mission)).thenReturn(true);

        // when
        boolean fulfilled = and.isFulfilledBy(mission);

        // then
        assertThat(fulfilled).isTrue();
        verify(restriction1).isFulfilledBy(mission);
        verify(restriction2).isFulfilledBy(mission);
        verifyNoMoreInteractions(restriction1, restriction2, mission);
    }

    @Test
    public void shouldNotBeFulfilledWhenOneRestrictionNotFullfilledWithAndOperator() {
        // given
        Restriction restriction1 = mock(Restriction.class);
        Restriction restriction2 = mock(Restriction.class);
        Restriction and = new BooleanRestriction(BooleanOperatorEnum.AND, List.of(restriction1, restriction2));

        Mission mission = mock(Mission.class);

        when(restriction1.isFulfilledBy(mission)).thenReturn(true);
        when(restriction2.isFulfilledBy(mission)).thenReturn(false);

        // when
        boolean fulfilled = and.isFulfilledBy(mission);

        // then
        assertThat(fulfilled).isFalse();
        verify(restriction1).isFulfilledBy(mission);
        verify(restriction2).isFulfilledBy(mission);
        verifyNoMoreInteractions(restriction1, restriction2, mission);
    }

    @Test
    public void shouldBeFulfilledWhenAllAreFulfilledWithOrOperator() {
        // given
        Restriction restriction1 = mock(Restriction.class);
        Restriction restriction2 = mock(Restriction.class);
        Restriction and = new BooleanRestriction(BooleanOperatorEnum.OR, List.of(restriction1, restriction2));

        Mission mission = mock(Mission.class);

        when(restriction1.isFulfilledBy(mission)).thenReturn(true);
        when(restriction2.isFulfilledBy(mission)).thenReturn(true);

        // when
        boolean fulfilled = and.isFulfilledBy(mission);

        // then
        assertThat(fulfilled).isTrue();
        verify(restriction1).isFulfilledBy(mission);
        verifyNoMoreInteractions(restriction1, restriction2, mission);
    }

    @Test
    public void shouldBeFulfilledWhenOneRestrictionFulfilledWithOrOperator() {
        // given
        Restriction restriction1 = mock(Restriction.class);
        Restriction restriction2 = mock(Restriction.class);
        Restriction and = new BooleanRestriction(BooleanOperatorEnum.OR, List.of(restriction1, restriction2));

        Mission mission = mock(Mission.class);

        when(restriction1.isFulfilledBy(mission)).thenReturn(true);
        when(restriction2.isFulfilledBy(mission)).thenReturn(false);

        // when
        boolean fulfilled = and.isFulfilledBy(mission);

        // then
        assertThat(fulfilled).isTrue();
        verify(restriction1).isFulfilledBy(mission);
        verifyNoMoreInteractions(restriction1, restriction2, mission);
    }

    @Test
    public void shouldNotBeFulfilledWhenNoRestrictionFullfilledWithOrOperator() {
        // given
        Restriction restriction1 = mock(Restriction.class);
        Restriction restriction2 = mock(Restriction.class);
        Restriction and = new BooleanRestriction(BooleanOperatorEnum.AND, List.of(restriction1, restriction2));

        Mission mission = mock(Mission.class);

        when(restriction1.isFulfilledBy(mission)).thenReturn(false);
        when(restriction2.isFulfilledBy(mission)).thenReturn(false);

        // when
        boolean fulfilled = and.isFulfilledBy(mission);

        // then
        assertThat(fulfilled).isFalse();
        verify(restriction1).isFulfilledBy(mission);
        verifyNoMoreInteractions(restriction1, restriction2, mission);
    }
}
