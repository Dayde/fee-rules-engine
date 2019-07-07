package fr.malt.feerulesengine.rule.controllers;

import fr.malt.feerulesengine.rule.model.Restriction;
import fr.malt.feerulesengine.rule.model.Rule;
import fr.malt.feerulesengine.rule.services.RuleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RuleController.class)
public class RuleControllerTest {

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
        Rule rule = new Rule(id, name, fee, restrictions);
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
    }

}
