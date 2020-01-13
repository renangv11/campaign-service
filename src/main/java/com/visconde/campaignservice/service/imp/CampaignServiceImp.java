package com.visconde.campaignservice.service.imp;

import com.visconde.campaignservice.converter.CampaignConverter;
import com.visconde.campaignservice.datacontract.CampaignDataContract;
import com.visconde.campaignservice.model.Campaign;
import com.visconde.campaignservice.repository.CampaignRepository;
import com.visconde.campaignservice.repository.TeamRepository;
import com.visconde.campaignservice.service.CampaignService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@Service
public class CampaignServiceImp implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final TeamRepository teamRepository;
    private final CampaignConverter campaignConverter;

    public CampaignDataContract createCampaign(CampaignDataContract campaignDataContract) {
        insertCampaign(campaignDataContract);
        return campaignDataContract;
    }

    private void insertCampaign(CampaignDataContract campaignDataContract) {
        Optional<Campaign> byInitialDateAndFinalDate = campaignRepository
                .findByInitialDateAndFinalDate(campaignDataContract.getInitialDate(), campaignDataContract.getFinalDate());
        if(byInitialDateAndFinalDate.isPresent()){
            organizeAllCampaigns(byInitialDateAndFinalDate);
        }
        campaignRepository.save(campaignConverter.convertDataContractToEntity(campaignDataContract));
    }

    private void organizeAllCampaigns(Optional<Campaign> optionalCampaign) {
        if(optionalCampaign.isPresent()){
            Campaign campaign = optionalCampaign.get();
            LocalDate datePlusOneDay = campaign.getFinalDate().plusDays(1);
            campaign.setFinalDate(datePlusOneDay);
            organizeAllCampaigns(campaignRepository.findByInitialDateAndFinalDate(campaign.getInitialDate(), datePlusOneDay));
            campaignRepository.save(campaign);
        }
    }

    @Override
    public List<CampaignDataContract> getCampaigns(String teamName) {
        return campaignConverter.convertEntityListToDataContractList(teamRepository.findByTeamName(teamName).getCampaigns());
    }

    @Override
    public CampaignDataContract updateCampaign(CampaignDataContract campaignDataContract) {
        Campaign campaign = campaignConverter.convertDataContractToEntity(campaignDataContract);
        campaignRepository.save(campaign);
        return campaignDataContract;
    }

    @Override
    public void deleteCampaign(Long id) {
        campaignRepository.deleteById(id);
    }

}
