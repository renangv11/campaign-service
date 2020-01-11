package com.visconde.campaignservice.datacontract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CampaignDataContract {

    @JsonProperty("nome_campanha")
    private String campaignName;

    @JsonProperty("id_time_coracao")
    private String teamId;

    @JsonProperty("data_vigencia")
    private LocalDate effectiveDate;

}
