package com.visconde.campaignservice.datacontract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ClubMemberDataContract {

    @JsonProperty("id_socio")
    private Long clubMemberId;

    @JsonProperty("nome_socio")
    private String clubMemberName;

    @JsonProperty("email_socio")
    private String clubMemberEmail;

    @JsonProperty("data_nascimento_socio")
    private LocalDate clubMemberBirthday;

    @JsonProperty("clube_socio")
    private String clubMemberTeam;

}
