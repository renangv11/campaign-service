package com.visconde.campaignservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.visconde.campaignservice.converter.CampaignConverter;
import com.visconde.campaignservice.datacontract.CampaignDataContract;
import com.visconde.campaignservice.model.Campaign;
import com.visconde.campaignservice.model.ClubMember;
import com.visconde.campaignservice.model.Team;
import com.visconde.campaignservice.producer.CampaignChangeProducer;
import com.visconde.campaignservice.repository.CampaignRepository;
import com.visconde.campaignservice.repository.ClubMemberRepository;
import com.visconde.campaignservice.repository.TeamRepository;
import com.visconde.campaignservice.service.imp.CampaignServiceImp;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class CampaignServiceImpTest {

    private CampaignRepository campaignRepository = mock(CampaignRepository.class);
    private TeamRepository teamRepository = mock(TeamRepository.class);
    private ClubMemberRepository clubMemberRepository = mock(ClubMemberRepository.class);
    private CampaignConverter campaignConverter = mock(CampaignConverter.class);
    private CampaignChangeProducer campaignChangeProducer = mock(CampaignChangeProducer.class);

    private CampaignService campaignService = new CampaignServiceImp(campaignRepository, teamRepository, clubMemberRepository, campaignConverter, campaignChangeProducer);

    @Test
    public void should_create_a_campaign(){
        when(campaignConverter.convertDataContractToEntity(any(CampaignDataContract.class)))
                .thenReturn(new Campaign());

        campaignService.createCampaign(new CampaignDataContract());

        verify(campaignRepository).save(any(Campaign.class));
    }

    @Test
    public void should_create_a_campaign_with_same_effective_period(){
        //TODO
    }

    @Test
    public void should_get_a_campaign_by_team_name(){
        when(teamRepository.findByTeamName("corinthians"))
                .thenReturn(mockTeam());

        campaignService.getCampaignsByTeamName("corinthians");

        verify(teamRepository).findByTeamName("corinthians");
        verify(campaignConverter).convertEntityListToDataContractList(anyList());
    }

    @Test
    public void should_get_a_campaign_by_club_member_id(){
        when(clubMemberRepository.findByClubMemberId(1l))
                .thenReturn(mockClubMember());
        when(campaignConverter.convertEntityListToDataContractList(anyList()))
                .thenReturn(mockDataContractList());

        List<CampaignDataContract> campaings = campaignService.getCampaignsByClubMember(1l);

        verify(clubMemberRepository).findByClubMemberId(1l);
        assertFalse(campaings.isEmpty());
    }

    @Test
    public void should_get_a_empty_list_searching_campaign_by_club_member_id(){
        when(clubMemberRepository.findByClubMemberId(1l))
                .thenReturn(empty());

        List<CampaignDataContract> campaings = campaignService.getCampaignsByClubMember(1l);

        verify(clubMemberRepository).findByClubMemberId(1l);
        assertTrue(campaings.isEmpty());
    }

    @Test
    public void should_update_a_campaign() throws JsonProcessingException {
        CampaignDataContract campaignDataContract = new CampaignDataContract();
        when(campaignConverter.convertDataContractToEntity(campaignDataContract))
                .thenReturn(new Campaign());

        assertEquals(campaignDataContract, campaignService.updateCampaign(campaignDataContract));
        verify(campaignRepository).save(any(Campaign.class));
    }

    @Test
    public void should_delete_a_campaign(){
        campaignService.deleteCampaign(1l);

        verify(campaignRepository).deleteById(1l);
    }

    private Optional<ClubMember> mockClubMember() {
        List<Campaign> campaigns = new ArrayList<Campaign>();
        campaigns.add(Campaign.builder().campaignName("campeonato paulista").build());

        return Optional.of(ClubMember.builder()
                .campaigns(campaigns)
                .clubMemberId(1l)
                .build());
    }

    private List<CampaignDataContract> mockDataContractList() {
        List<CampaignDataContract> campaignDataContracts = new ArrayList<CampaignDataContract>();
        campaignDataContracts.add(new CampaignDataContract());

        return campaignDataContracts;
    }

    private Team mockTeam() {
        return Team.builder().campaigns(new ArrayList<>()).build();
    }

}
