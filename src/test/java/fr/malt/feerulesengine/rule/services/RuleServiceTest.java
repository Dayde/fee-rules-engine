package fr.malt.feerulesengine.rule.services;

import fr.malt.feerulesengine.rule.exceptions.RuleAlreadyExistsException;
import fr.malt.feerulesengine.rule.model.BusinessRule;
import fr.malt.feerulesengine.rule.model.Restriction;
import fr.malt.feerulesengine.rule.repositories.RuleRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RuleServiceTest {

    @Mock
    private RuleRepository repository;

    @InjectMocks
    private RuleService ruleService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldReturnAllRulesFoundInRepository() {
        // given
        BusinessRule rule = mock(BusinessRule.class);
        when(repository.findAll()).thenReturn(List.of(rule));

        // when
        List<BusinessRule> result = ruleService.findAll();

        // then
        assertThat(result).containsExactly(rule);
        verify(repository).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldReturnRuleFoundInRepository() {
        // given
        Optional<BusinessRule> rule = ofNullable(mock(BusinessRule.class));
        String id = "id";
        when(repository.findById(id)).thenReturn(rule);

        // when
        Optional<BusinessRule> result = ruleService.findById(id);

        // then
        assertThat(result).isEqualTo(rule);
        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldSaveRuleInRepository() {
        // given
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule ruleToSave = new BusinessRule(null, name, fee, restrictions);
        BusinessRule ruleSaved = new BusinessRule("id", name, fee, restrictions);

        when(repository.findByName(name)).thenReturn(Optional.empty());
        when(repository.save(ruleToSave)).thenReturn(ruleSaved);

        // when
        BusinessRule result = ruleService.save(ruleToSave);

        // then
        assertThat(result).isEqualTo(ruleSaved);
        verify(repository).findByName(name);
        verify(repository).save(ruleToSave);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldSaveRuleInRepositoryIfItExistsWithTheSameId() {
        // given
        String id = "id";
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule existingRule = new BusinessRule(id, "otherName", 1, restrictions);
        BusinessRule ruleToSave = new BusinessRule(id, name, fee, restrictions);
        BusinessRule ruleSaved = new BusinessRule(id, name, fee, restrictions);

        when(repository.findByName(name)).thenReturn(Optional.of(existingRule));
        when(repository.save(ruleToSave)).thenReturn(ruleSaved);

        // when
        BusinessRule result = ruleService.save(ruleToSave);

        // then
        assertThat(result).isEqualTo(ruleSaved);
        verify(repository).findByName(name);
        verify(repository).save(ruleToSave);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldNotSaveRuleInRepositoryWhenRuleWithSameNameExists() {
        // given
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule ruleToSave = new BusinessRule(null, name, fee, restrictions);
        BusinessRule existingRule = new BusinessRule("existingId", name, fee, restrictions);

        when(repository.findByName(name)).thenReturn(Optional.of(existingRule));

        expectedException.expect(RuleAlreadyExistsException.class);
        expectedException.expectMessage("Rule using same name already exists with id existingId");

        // when
        ruleService.save(ruleToSave);
    }

    @Test
    public void shouldUpdateRuleInRepository() {
        // given
        String id = "id";
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule ruleToSave = new BusinessRule(id, name, fee, restrictions);
        BusinessRule oldRule = new BusinessRule(id, "otherName", 8, restrictions);
        BusinessRule ruleSaved = new BusinessRule(id, name, fee, restrictions);

        when(repository.findById(id)).thenReturn(Optional.of(oldRule));
        when(repository.findByName(name)).thenReturn(Optional.empty());
        when(repository.save(ruleToSave)).thenReturn(ruleSaved);

        // when
        BusinessRule result = ruleService.saveWithId(id, ruleToSave);

        // then
        assertThat(result).isEqualTo(ruleSaved);
        verify(repository).findById(id);
        verify(repository).findByName(name);
        verify(repository).save(ruleToSave);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldUpdateRuleEvenWhenNameIsUnchanged() {
        // given
        String id = "id";
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule ruleToSave = new BusinessRule(id, name, fee, restrictions);
        BusinessRule oldRule = new BusinessRule(id, name, 8, restrictions);
        BusinessRule ruleSaved = new BusinessRule(id, name, fee, restrictions);

        when(repository.findById(id)).thenReturn(Optional.of(oldRule));
        when(repository.findByName(name)).thenReturn(Optional.of(oldRule));
        when(repository.save(ruleToSave)).thenReturn(ruleSaved);

        // when
        BusinessRule result = ruleService.saveWithId(id, ruleToSave);

        // then
        assertThat(result).isEqualTo(ruleSaved);
        verify(repository).findById(id);
        verify(repository).findByName(name);
        verify(repository).save(ruleToSave);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldInsertRuleWhenIdIsPassedRuleIdIsNullAndNoRuleWithTheIdPasedExists() {
        // given
        String id = "id";
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule ruleToSave = new BusinessRule(null, name, fee, restrictions);
        BusinessRule ruleSaved = new BusinessRule(id, name, fee, restrictions);

        when(repository.findById(id)).thenReturn(Optional.empty());
        when(repository.findByName(name)).thenReturn(Optional.empty());
        when(repository.save(ruleToSave)).thenReturn(ruleSaved);

        // when
        BusinessRule result = ruleService.saveWithId(id, ruleToSave);

        // then
        assertThat(result).isEqualTo(ruleSaved);
        verify(repository).findById(id);
        verify(repository).findByName(name);
        verify(repository).save(ruleToSave);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldNotUpdateRuleWhenIdExistsAndRuleIdDiffers() {
        // given
        String id = "id";
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule ruleToSave = new BusinessRule("anotherId", name, fee, restrictions);
        BusinessRule oldRule = new BusinessRule(id, name, 8, restrictions);
        BusinessRule ruleSaved = new BusinessRule(id, name, fee, restrictions);

        when(repository.findById(id)).thenReturn(Optional.of(oldRule));
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("While updating, id in uri (\"id\") should be equal to body id property (\"anotherId\")");

        // when
        try {
            ruleService.saveWithId(id, ruleToSave);
        } catch (IllegalArgumentException ex) {
            // then
            verify(repository).findById(id);
            verifyNoMoreInteractions(repository);
            throw ex;
        }
    }

    @Test
    public void shouldNotUpdateRuleWhenIdDoesNotExistAndGivenRuleHasItSet() {
        // given
        String id = "id";
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule ruleToSave = new BusinessRule(id, name, fee, restrictions);

        when(repository.findById(id)).thenReturn(Optional.empty());
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("While inserting a new rule, body id property should not be set");

        // when
        try {
            ruleService.saveWithId(id, ruleToSave);
        } catch (IllegalArgumentException ex) {
            // then
            verify(repository).findById(id);
            verifyNoMoreInteractions(repository);
            throw ex;
        }
    }

    @Test
    public void shouldDeleteRuleWithGivenId() {
        //given
        String id = "id";
        when(repository.existsById(id)).thenReturn(true);

        //when
        ruleService.deleteById(id);

        //then
        verify(repository).existsById(id);
        verify(repository).deleteById(id);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldThrowAnExceptionWhenRuleDoesNotExist() {
        //given
        String id = "id";
        when(repository.existsById(id)).thenReturn(false);

        expectedException.expect(NoSuchElementException.class);

        //when
        try {
            ruleService.deleteById(id);
        } catch (NoSuchElementException ex) {
            verify(repository).existsById(id);
            verifyNoMoreInteractions(repository);
            throw ex;
        }
    }
}
