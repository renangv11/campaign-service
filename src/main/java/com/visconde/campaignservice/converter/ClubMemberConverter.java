package com.visconde.campaignservice.converter;

import com.visconde.campaignservice.datacontract.CampaignDataContract;
import com.visconde.campaignservice.datacontract.ClubMemberDataContract;
import com.visconde.campaignservice.model.Campaign;
import com.visconde.campaignservice.model.ClubMember;
import org.springframework.stereotype.Component;

import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class ClubMemberConverter {
    public ClubMember convertDataContractToEntity(ClubMemberDataContract clubMemberDataContract){
        ClubMember clubMember = new ClubMember();
        copyProperties(clubMemberDataContract, clubMember);
        return clubMember;
    }
}
