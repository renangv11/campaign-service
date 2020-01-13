package com.visconde.campaignservice.repository;

import com.visconde.campaignservice.model.Campaign;
import com.visconde.campaignservice.model.CampaignPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    Optional<Campaign> findByInitialDateAndFinalDate(LocalDate initialDate, LocalDate finalDate);

}
