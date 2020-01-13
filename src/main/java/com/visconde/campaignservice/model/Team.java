package com.visconde.campaignservice.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity(name = "time")
public class Team {

    @Id
    @Column(name = "id_time")
    private Long teamId;

    @Column(name = "nome_time", unique = true)
    private String teamName;

    @OneToMany
    @JoinColumn(name = "id_time")
    private List<Campaign> campaigns;

}
