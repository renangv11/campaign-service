package com.visconde.campaignservice.repository;

import com.visconde.campaignservice.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {

    public Team findByTeamName(String teamName);

}
