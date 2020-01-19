package com.visconde.campaignservice.service.imp;

import com.visconde.campaignservice.converter.CampaignConverter;
import com.visconde.campaignservice.datacontract.CampaignDataContract;
import com.visconde.campaignservice.exception.CampaignNotFoundException;
import com.visconde.campaignservice.entities.Campaign;
import com.visconde.campaignservice.entities.ClubMember;
import com.visconde.campaignservice.kafka.producer.CampaignChangeProducer;
import com.visconde.campaignservice.repository.CampaignRepository;
import com.visconde.campaignservice.repository.ClubMemberRepository;
import com.visconde.campaignservice.repository.TeamRepository;
import com.visconde.campaignservice.service.CampaignService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager entityManager;

    public CampaignDataContract createCampaign(CampaignDataContract campaignDataContract) {
        insertCampaign(campaignDataContract);
        return campaignDataContract;
    }

    private void insertCampaign(CampaignDataContract campaignDataContract) {
        Optional<Campaign> campaign = campaignRepository.findByInitialDateAndFinalDate(campaignDataContract.getInitialDate(), campaignDataContract.getFinalDate());
        if(campaign.isPresent()){
            organizeAllCampaignsAlertingChanges(campaign);
        }
        campaignRepository.save(campaignConverter.convertDataContractToEntity(campaignDataContract));
    }

    private void organizeAllCampaignsAlertingChanges(Optional<Campaign> optionalCampaign) {
        if(optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
            LocalDate datePlusOneDay = campaign.getFinalDate().plusDays(1);
            campaign.setFinalDate(datePlusOneDay);
            entityManager.clear();
            organizeAllCampaignsAlertingChanges(campaignRepository.findByInitialDateAndFinalDate(campaign.getInitialDate(), datePlusOneDay));
            campaignRepository.save(campaign);
            campaignChangeProducer.send(campaignConverter.convertEntityToDataContract(campaign));
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
    public CampaignDataContract updateCampaign(CampaignDataContract campaignDataContract, Long campaignId){
        Optional<Campaign> campaign = campaignRepository.findById(campaignId);
        if(!campaign.isPresent()){
            throw new CampaignNotFoundException("Campanha com ID " + campaignId + "não encontrada");
        }
        campaignRepository.save(campaign.get());
        campaignChangeProducer.send(campaignConverter.convertEntityToDataContract(campaign.get()));
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
