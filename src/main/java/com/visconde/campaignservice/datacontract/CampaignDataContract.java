package com.visconde.campaignservice.datacontract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CampaignDataContract {

    @JsonProperty("nome_campanha")
    private String campaignName;

    @JsonProperty("id_time_coracao")
    private Long teamId;

    @JsonProperty("data_inicial")
    private LocalDate initialDate;

    @JsonProperty("data_final")
    private LocalDate finalDate;

}
