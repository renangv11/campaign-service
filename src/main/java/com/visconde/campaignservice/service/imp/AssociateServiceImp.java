package com.visconde.campaignservice.service.imp;

import com.visconde.campaignservice.converter.ClubMemberConverter;
import com.visconde.campaignservice.datacontract.ClubMemberDataContract;
import com.visconde.campaignservice.model.Campaign;
import com.visconde.campaignservice.model.ClubMember;
import com.visconde.campaignservice.repository.CampaignRepository;
import com.visconde.campaignservice.repository.ClubMemberRepository;
import com.visconde.campaignservice.repository.TeamRepository;
import com.visconde.campaignservice.service.AssociateService;
import com.visconde.campaignservice.service.CampaignService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.now;

@AllArgsConstructor
@Service
public class AssociateServiceImp implements AssociateService {

    private final ClubMemberRepository clubMemberRepository;
    private final TeamRepository teamRepository;
    private final ClubMemberConverter clubMemberConverter;

    @Override
    public void associateClubMemberWithCampaigns(ClubMemberDataContract clubMemberDataContract) {
        Optional<ClubMember> optionalClubMember = clubMemberRepository.findByClubMemberId(clubMemberDataContract.getClubMemberId());
        List<Campaign> actualCampaignsByTeam = teamRepository.findByTeamNameAndEffectiveDate(clubMemberDataContract.getClubMemberTeam(), now());
        if(optionalClubMember.isPresent()){
            List<Campaign> newCampaignsForClubMember = getNewCampaignsForClubMember(optionalClubMember.get().getCampaigns(), actualCampaignsByTeam);
            optionalClubMember.get().getCampaigns().addAll(newCampaignsForClubMember);
            clubMemberRepository.save(optionalClubMember.get());
        } else {
            ClubMember clubMember = clubMemberConverter.convertDataContractToEntity(clubMemberDataContract);
            clubMember.setCampaigns(actualCampaignsByTeam);
            clubMemberRepository.save(clubMember);
        }
    }

    private List<Campaign>  getNewCampaignsForClubMember(List<Campaign> clubMemberCampaigns, List<Campaign> actualCampaigns) {
        List<Campaign> newCampaignsForClubMember = new ArrayList<>(actualCampaigns);
        newCampaignsForClubMember.removeAll(clubMemberCampaigns);
        return newCampaignsForClubMember;
    }

}
