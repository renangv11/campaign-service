package com.visconde.campaignservice.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

@Data
@NoArgsConstructor
@Embeddable
public class CampaignPK implements Serializable {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_campanha")
    private Long campaignId;

    @Column(name = "data_inicial", nullable = false)
    private LocalDate initialDate;

    @Column(name = "data_final", nullable = false)
    private LocalDate finalDate;

}
