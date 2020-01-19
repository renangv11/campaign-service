package com.visconde.campaignservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints= @UniqueConstraint(columnNames={"data_inicial", "data_final"}))
@Entity(name = "campanha")
public class Campaign {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id_campanha")
    private Long campaignId;

    @Column(name = "data_inicial", nullable = false)
    private LocalDate initialDate;

    @Column(name = "data_final", nullable = false)
    private LocalDate finalDate;

    @Column(name = "nome_campanha", nullable = false)
    private String campaignName;

    @Column(name = "id_time", nullable = false)
    private Long teamId;

}
