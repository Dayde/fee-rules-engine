package fr.malt.feerulesengine.rule.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.malt.feerulesengine.rule.exceptions.RuleAlreadyExistsException;
import fr.malt.feerulesengine.rule.model.BusinessRule;
import fr.malt.feerulesengine.rule.model.Restriction;
import fr.malt.feerulesengine.rule.services.RuleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RuleController.class)
public class RuleControllerTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleService ruleService;

    @Test
    public void shouldReturnAllRulesFoundInRepository() throws Exception {
        String id = "id";
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule rule = new BusinessRule(id, name, fee, restrictions);
        when(ruleService.findAll()).thenReturn(List.of(rule));

        this.mockMvc.perform(
                get("/rules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].fee").value(fee))
                .andExpect(jsonPath("$[0].restrictions").isArray())
                .andExpect(jsonPath("$[0].restrictions").isEmpty());

        verify(ruleService).findAll();
        verifyNoMoreInteractions(ruleService);
    }

    @Test
    public void shouldReturnRuleFoundInRepository() throws Exception {
        String id = "id";
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule rule = new BusinessRule(id, name, fee, restrictions);
        when(ruleService.findById(id)).thenReturn(java.util.Optional.of(rule));

        this.mockMvc.perform(
                get("/rules/id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.fee").value(fee))
                .andExpect(jsonPath("$.restrictions").isArray())
                .andExpect(jsonPath("$.restrictions").isEmpty());

        verify(ruleService).findById(id);
        verifyNoMoreInteractions(ruleService);
    }

    @Test
    public void shouldSend404WhenNotFoundInRepository() throws Exception {
        String id = "id";
        when(ruleService.findById(id)).thenReturn(Optional.empty());

        this.mockMvc.perform(
                get("/rules/id"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").isString())
                .andExpect(jsonPath("$").value("Rule not found"));

        verify(ruleService).findById(id);
        verifyNoMoreInteractions(ruleService);
    }

    @Test
    public void shouldSaveRuleInRepository() throws Exception {
        String id = "id";
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule ruleToSave = new BusinessRule(null, name, fee, restrictions);
        BusinessRule ruleSaved = new BusinessRule(id, name, fee, restrictions);
        when(ruleService.save(ruleToSave)).thenReturn(ruleSaved);


        this.mockMvc.perform(
                post("/rules")
                        .content(mapper.writeValueAsString(ruleToSave))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.fee").value(fee))
                .andExpect(jsonPath("$.restrictions").isArray())
                .andExpect(jsonPath("$.restrictions").isEmpty());

        verify(ruleService).save(ruleToSave);
        verifyNoMoreInteractions(ruleService);
    }

    @Test
    public void shouldSaveRuleInRepositoryEvenIfItExists() throws Exception {
        String id = "id";
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule ruleToSave = new BusinessRule(id, name, fee, restrictions);
        BusinessRule ruleSaved = new BusinessRule(id, name, fee, restrictions);
        when(ruleService.save(ruleToSave)).thenReturn(ruleSaved);


        this.mockMvc.perform(
                post("/rules")
                        .content(mapper.writeValueAsString(ruleToSave))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.fee").value(fee))
                .andExpect(jsonPath("$.restrictions").isArray())
                .andExpect(jsonPath("$.restrictions").isEmpty());

        verify(ruleService).save(ruleToSave);
        verifyNoMoreInteractions(ruleService);
    }

    @Test
    public void shouldSend409WhenRuleAlreadyExistsInRepository() throws Exception {
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule ruleToSave = new BusinessRule(null, name, fee, restrictions);
        String message = "message";
        when(ruleService.save(ruleToSave)).thenThrow(new RuleAlreadyExistsException(message));

        this.mockMvc.perform(
                post("/rules")
                        .content(mapper.writeValueAsString(ruleToSave))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$").isString())
                .andExpect(jsonPath("$").value(message));

        verify(ruleService).save(ruleToSave);
        verifyNoMoreInteractions(ruleService);
    }

    @Test
    public void shouldUpdateRuleInRepository() throws Exception {
        String id = "id";
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule ruleToSave = new BusinessRule(id, name, fee, restrictions);
        BusinessRule ruleSaved = new BusinessRule(id, name, fee, restrictions);
        when(ruleService.saveWithId(id, ruleToSave)).thenReturn(ruleSaved);

        this.mockMvc.perform(
                put("/rules/" + id)
                        .content(mapper.writeValueAsString(ruleToSave))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.fee").value(fee))
                .andExpect(jsonPath("$.restrictions").isArray())
                .andExpect(jsonPath("$.restrictions").isEmpty());

        verify(ruleService).saveWithId(id, ruleToSave);
        verifyNoMoreInteractions(ruleService);
    }

    @Test
    public void shouldSaveRuleInRepositoryIfItDoesNotExistsWithGivenId() throws Exception {
        String id = "id";
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule ruleToSave = new BusinessRule(null, name, fee, restrictions);
        BusinessRule ruleSaved = new BusinessRule(id, name, fee, restrictions);
        when(ruleService.saveWithId(id, ruleToSave)).thenReturn(ruleSaved);

        this.mockMvc.perform(
                put("/rules/" + id)
                        .content(mapper.writeValueAsString(ruleToSave))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.fee").value(fee))
                .andExpect(jsonPath("$.restrictions").isArray())
                .andExpect(jsonPath("$.restrictions").isEmpty());

        verify(ruleService).saveWithId(id, ruleToSave);
        verifyNoMoreInteractions(ruleService);
    }

    @Test
    public void shouldSend400WhenIllegaleArgumentExceptionIsThrown() throws Exception {
        String id = "id";
        String name = "name";
        float fee = 10;
        List<Restriction> restrictions = Collections.emptyList();
        BusinessRule ruleToSave = new BusinessRule(null, name, fee, restrictions);
        String message = "message";
        when(ruleService.saveWithId(id, ruleToSave)).thenThrow(new IllegalArgumentException(message));


        this.mockMvc.perform(
                put("/rules/" + id)
                        .content(mapper.writeValueAsString(ruleToSave))
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isString())
                .andExpect(jsonPath("$").value(message));

        verify(ruleService).saveWithId(id, ruleToSave);
        verifyNoMoreInteractions(ruleService);
    }

    @Test
    public void shouldDeleteRuleWithGivenId() throws Exception {
        String id = "id";

        this.mockMvc.perform(
                delete("/rules/" + id))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$").doesNotExist());

        verify(ruleService).deleteById(id);
        verifyNoMoreInteractions(ruleService);
    }
}
