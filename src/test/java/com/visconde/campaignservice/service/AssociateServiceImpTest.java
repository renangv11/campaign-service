package com.visconde.campaignservice.service;

import com.visconde.campaignservice.converter.ClubMemberConverter;
import com.visconde.campaignservice.datacontract.ClubMemberDataContract;
import com.visconde.campaignservice.model.Campaign;
import com.visconde.campaignservice.model.ClubMember;
import com.visconde.campaignservice.repository.ClubMemberRepository;
import com.visconde.campaignservice.repository.TeamRepository;
import com.visconde.campaignservice.service.imp.AssociateServiceImp;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AssociateServiceImpTest {

    private ClubMemberRepository clubMemberRepository = mock(ClubMemberRepository.class);
    private TeamRepository teamRepository = mock(TeamRepository.class);
    private ClubMemberConverter clubMemberConverter = mock(ClubMemberConverter.class);

    private AssociateService associateService = new AssociateServiceImp(clubMemberRepository, teamRepository, clubMemberConverter);

    @Test
    public void associate_new_club_members_with_new_campaigns(){
        when(clubMemberRepository.findByClubMemberId(1l))
                .thenReturn(empty());
        when(teamRepository.findByTeamNameAndEffectiveDate(any(String.class), any(LocalDate.class)))
                .thenReturn(mockTeamRepository());
        when(clubMemberConverter.convertDataContractToEntity(any(ClubMemberDataContract.class)))
                .thenReturn(new ClubMember());

        associateService.associateClubMemberWithCampaigns(mockClubMemberDataContract());

        verify(clubMemberRepository).save(any(ClubMember.class));
    }

    @Test
    public void associate_a_already_registered_club_members_with_new_campaigns(){
        when(clubMemberRepository.findByClubMemberId(1l))
                .thenReturn(Optional.of(mockClubMember()));
        when(teamRepository.findByTeamNameAndEffectiveDate(any(String.class), any(LocalDate.class)))
                .thenReturn(mockTeamRepository());

        associateService.associateClubMemberWithCampaigns(mockClubMemberDataContract());

        Mockito.verify(clubMemberRepository).save(any(ClubMember.class));
    }

    private ClubMemberDataContract mockClubMemberDataContract() {
        return ClubMemberDataContract
                .builder()
                .clubMemberTeam("Corinthians")
                .clubMemberId(1l)
                .build();
    }

    private ClubMember mockClubMember() {
        List<Campaign> campaigns = new ArrayList<>();
        campaigns.add(Campaign.builder().campaignName("camapnha teste 1").teamId(1l).build());

        return ClubMember.builder()
                .campaigns(campaigns)
                .email("emailTest@gmail.com")
                .clubMemberId(1l)
                .build();
    }

    private List<Campaign> mockTeamRepository() {
        List<Campaign> campaigns = new ArrayList<>();
        campaigns.add(Campaign.builder().campaignName("camapnha teste 1").teamId(1l).build());
        campaigns.add(Campaign.builder().campaignName("camapnha teste 2").teamId(1l).build());
        return campaigns;
    }

}
