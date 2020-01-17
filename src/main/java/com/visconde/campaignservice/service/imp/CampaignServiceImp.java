package com.visconde.campaignservice.service.imp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.visconde.campaignservice.converter.CampaignConverter;
import com.visconde.campaignservice.datacontract.CampaignDataContract;
import com.visconde.campaignservice.model.Campaign;
import com.visconde.campaignservice.model.ClubMember;
import com.visconde.campaignservice.producer.CampaignChangeProducer;
import com.visconde.campaignservice.repository.CampaignRepository;
import com.visconde.campaignservice.repository.ClubMemberRepository;
import com.visconde.campaignservice.repository.TeamRepository;
import com.visconde.campaignservice.service.CampaignService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalDate.now;

@AllArgsConstructor
@Service
public class CampaignServiceImp implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final TeamRepository teamRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final CampaignConverter campaignConverter;
    private final CampaignChangeProducer campaignChangeProducer;

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
    public List<CampaignDataContract> getCampaignsByTeamName(String teamName) {
        List<CampaignDataContract> campaignDataContracts =
                campaignConverter.convertEntityListToDataContractList(teamRepository.findByTeamName(teamName).getCampaigns());
        return this.filterCampaignByEffectiveDate(campaignDataContracts);
    }

    @Override
    public List<CampaignDataContract> getCampaignsByClubMember(Long clubMemberId) {
        Optional<ClubMember> clubMember = clubMemberRepository.findByClubMemberId(clubMemberId);
        if(clubMember.isPresent()){
            List<CampaignDataContract> campaignDataContracts =
                    campaignConverter.convertEntityListToDataContractList(clubMember.get().getCampaigns());
            return this.filterCampaignByEffectiveDate(campaignDataContracts);
        }
        return new ArrayList<>();
    }

    @Override
    public CampaignDataContract updateCampaign(CampaignDataContract campaignDataContract){
        Campaign campaign = campaignConverter.convertDataContractToEntity(campaignDataContract);
        campaignRepository.save(campaign);
        campaignChangeProducer.send(campaignDataContract);
        return campaignDataContract;
    }

    @Override
    public void deleteCampaign(Long id) {
        campaignRepository.deleteById(id);
    }

    private List<CampaignDataContract> filterCampaignByEffectiveDate(List<CampaignDataContract>  campaignDataContracts){
        return campaignDataContracts.stream()
                .filter(campaign -> now().isBefore(campaign.getFinalDate()) && now().isAfter(campaign.getInitialDate()))
                .collect(Collectors.toList());
    }

}
