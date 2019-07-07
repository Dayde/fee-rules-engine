package fr.malt.feerulesengine.fee.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import fr.malt.feerulesengine.fee.model.CommercialRelation;
import fr.malt.feerulesengine.fee.model.Fee;
import fr.malt.feerulesengine.fee.model.Mission;
import fr.malt.feerulesengine.fee.model.Person;
import fr.malt.feerulesengine.fee.services.FeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;

import static fr.malt.feerulesengine.fee.services.FeeService.DEFAULT_FEE;
import static org.joda.time.DateTime.now;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FeeController.class)
public class FeeControllerTest {

    private ObjectWriter mapper = new ObjectMapper().registerModule(new JodaModule())
            .writerWithDefaultPrettyPrinter();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeeService feeService;

    @Test
    public void shouldFeeComputedInFeeService() throws Exception {
        Person client = new Person("FR");
        Person freelancer = new Person("FR");
        long missionLength = Duration.ofDays(20).toMillis();
        CommercialRelation commercialRelation = new CommercialRelation(now().minusMonths(4), now().minusDays(2));
        Mission mission = new Mission(client, freelancer, missionLength, commercialRelation);
        when(feeService.computeFee(any(Mission.class))).thenReturn(new Fee(DEFAULT_FEE));

        String content = mapper.writeValueAsString(mission);
        System.out.println(content);
        this.mockMvc.perform(
                post("/fee")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.fee").value(DEFAULT_FEE))
                .andExpect(jsonPath("$.reason").doesNotHaveJsonPath());

        verify(feeService).computeFee(any(Mission.class));
        verifyNoMoreInteractions(feeService);
    }
}
