package com.visconde.campaignservice.repository;

import com.visconde.campaignservice.entities.Campaign;
import com.visconde.campaignservice.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    public Team findByTeamName(String teamName);

    @Query(value = "select * from time t " +
            "inner join campanha c on (t.id_time = c.id_time) " +
            "where t.nome_time = ?1 and c.data_inicial < ?2 and c.data_final > ?2", nativeQuery = true)
    public List<Campaign> findByTeamNameAndEffectiveDate(String teamName, LocalDate now);

}
