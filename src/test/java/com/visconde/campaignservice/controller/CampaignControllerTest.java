package com.visconde.campaignservice.controller;

import com.visconde.campaignservice.service.imp.CampaignServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class CampaignControllerTest {

    private MockMvc mockMvc;
    private CampaignServiceImp campaignService = Mockito.mock(CampaignServiceImp.class);

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CampaignController(campaignService)).build();
    }

    @Test
    public void should_create_a_campaign() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .post("/campanha")
                .content(mockValidRequest().getBytes())
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void should_get_a_campaign_by_team_name() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/campanha")
                .param("nome_time", "corinthians")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_get_a_campaign_by_club_member_id() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .get("/campanha")
                .param("id_socio", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_update_a_campaign() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .put("/campanha")
                .content(mockValidRequest().getBytes())
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_delete_a_campaign() throws Exception {
        mockMvc.perform( MockMvcRequestBuilders
                .delete("/campanha/{id_campanha}", 1l)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private String mockValidRequest() {
        String json = "{\"nome_campanha\":\"Campeonato Paulista\"," +
                "\"id_time_coracao\":\"Corinthians\"," +
                "\"data_inicial\":\"2019-01-17\"," +
                "\"data_final\":\"2019-04-15\"}";
        return json;
    }

}
