package com.visconde.campaignservice.datacontract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CampaignDataContract {

    @JsonProperty("id_campanha")
    private Long campaignId;

    @NotEmpty(message = "Nome da campanha não pode ser vazio")
    @JsonProperty("nome_campanha")
    private String campaignName;

    @NotNull(message = "Id do time deve ser informado")
    @JsonProperty("id_time_coracao")
    private Long teamId;

    @NotEmpty(message = "Data de início deve ser informada")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("data_inicial")
    private LocalDate initialDate;

    @NotEmpty(message = "Data fim deve ser informada")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("data_final")
    private LocalDate finalDate;

}
