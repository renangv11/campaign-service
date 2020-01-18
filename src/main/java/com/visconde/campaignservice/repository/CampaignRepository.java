package com.visconde.campaignservice.repository;

import com.visconde.campaignservice.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    Optional<Campaign> findByInitialDateAndFinalDate(LocalDate initialDate, LocalDate finalDate);

    @Query(value = "select * from campanha where data_inicial < ?1 and data_final > ?1", nativeQuery = true)
    List<Campaign> findByCampaignsByEffectiveDate(LocalDate now);

}
