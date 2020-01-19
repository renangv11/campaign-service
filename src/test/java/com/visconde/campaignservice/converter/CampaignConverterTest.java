package com.visconde.campaignservice.converter;

import com.visconde.campaignservice.datacontract.CampaignDataContract;
import com.visconde.campaignservice.entities.Campaign;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.of;
import static org.junit.Assert.assertEquals;

public class CampaignConverterTest {

    private CampaignConverter campaignConverter = new CampaignConverter();

    @Test
    public void should_convert_data_contract_to_entity(){
        Campaign campaign = campaignConverter.convertDataContractToEntity(mockCampaignDataContract());

        assertEquals("nome teste", campaign.getCampaignName());
        assertEquals(Long.valueOf(1), campaign.getTeamId());
        assertEquals(of(2019, 1, 1), campaign.getInitialDate());
        assertEquals(of(2019, 2, 2), campaign.getFinalDate());
    }

    @Test
    public void should_convert_entity_list_to_data_contract_list() {
        List<CampaignDataContract> campaignDataContracts = campaignConverter.convertEntityListToDataContractList(mockEntityList());

        assertEquals("nome teste", campaignDataContracts.get(0).getCampaignName());
        assertEquals(Long.valueOf(1), campaignDataContracts.get(0).getTeamId());
        assertEquals(of(2019, 1, 1), campaignDataContracts.get(0).getInitialDate());
        assertEquals(of(2019, 2, 2), campaignDataContracts.get(0).getFinalDate());

    }

    @Test
    public void should_convert_entity_to_data_contract_(){
        CampaignDataContract campaignDataContract = campaignConverter.convertEntityToDataContract(mockCampaignEntity());

        assertEquals("nome teste", campaignDataContract.getCampaignName());
        assertEquals(Long.valueOf(1), campaignDataContract.getTeamId());
        assertEquals(of(2019, 1, 1), campaignDataContract.getInitialDate());
        assertEquals(of(2019, 2, 2), campaignDataContract.getFinalDate());
    }

    private CampaignDataContract mockCampaignDataContract(){
        CampaignDataContract campaignDataContract = new CampaignDataContract();
        campaignDataContract.setCampaignName("nome teste");
        campaignDataContract.setTeamId(1l);
        campaignDataContract.setInitialDate(of(2019, 1, 1));
        campaignDataContract.setFinalDate(of(2019, 2, 2));

        return campaignDataContract;
    }

    private Campaign mockCampaignEntity(){
        Campaign campaign = new Campaign();
        campaign.setCampaignName("nome teste");
        campaign.setTeamId(1l);
        campaign.setInitialDate(of(2019, 1, 1));
        campaign.setFinalDate(of(2019, 2, 2));

        return campaign;
    }

    private List<Campaign> mockEntityList() {
        List<Campaign> campaigns = new ArrayList<>();
        campaigns.add(mockCampaignEntity());

        return campaigns;
    }
}
