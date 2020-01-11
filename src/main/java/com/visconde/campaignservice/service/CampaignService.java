package com.visconde.campaignservice.service;

import com.visconde.campaignservice.datacontract.CampaignDataContract;

public interface CampaignService {

    CampaignDataContract createCampaign(CampaignDataContract campaign);

    CampaignDataContract getCampaign(String teamName);

    CampaignDataContract updateCampaign(CampaignDataContract campaign);

    void deleteCampaign(Integer id);

}
