package com.visconde.campaignservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@IdClass(CampaignPK.class)
@Entity(name = "campanha")
public class Campaign {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_campanha")
    private Long campaignId;

    @Id
    @Column(name = "data_inicial")
    private LocalDate initialDate;

    @Id
    @Column(name = "data_final")
    private LocalDate finalDate;

    @Column(name = "nome_campanha")
    private String campaignName;

    @Column(name = "id_time")
    private Long teamId;
}
