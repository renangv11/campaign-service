package com.visconde.campaignservice.service;

import com.visconde.campaignservice.datacontract.CampaignDataContract;

import java.util.List;

public interface CampaignService {

    CampaignDataContract createCampaign(CampaignDataContract campaign);

    List<CampaignDataContract> getCampaigns(String teamName);

    CampaignDataContract updateCampaign(CampaignDataContract campaign);

    void deleteCampaign(Long id);

}
