package com.visconde.campaignservice.converter;

import com.visconde.campaignservice.datacontract.CampaignDataContract;
import com.visconde.campaignservice.model.Campaign;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Component
public class CampaignConverter {

    public Campaign convertDataContractToEntity(CampaignDataContract campaignDataContract){
        Campaign campaign = new Campaign();
        copyProperties(campaignDataContract, campaign);
        return campaign;
    }

    public CampaignDataContract convertEntityToDataContract(Campaign campaignEntity){
        CampaignDataContract campaignDataContract = new CampaignDataContract();
        copyProperties(campaignEntity, campaignDataContract);
        return campaignDataContract;
    }

    public List<CampaignDataContract> convertEntityListToDataContractList(List<Campaign> campaignsEntity){
        List<CampaignDataContract> campaignDataContracts = new ArrayList<>();
        campaignsEntity.forEach(campaignEntity -> campaignDataContracts.add(convertEntityToDataContract(campaignEntity)));
        return campaignDataContracts;
    }

}
