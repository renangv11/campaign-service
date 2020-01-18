package com.visconde.campaignservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.visconde.campaignservice.datacontract.CampaignDataContract;

import java.util.List;

public interface CampaignService {

    CampaignDataContract createCampaign(CampaignDataContract campaign);

    List<CampaignDataContract> getCampaignsByTeamName(String teamName);

    List<CampaignDataContract> getCampaignsByClubMember(Long clubMemberId);

    CampaignDataContract updateCampaign(CampaignDataContract campaign, Long campaignId) throws JsonProcessingException;

    void deleteCampaign(Long campaignId);

}
