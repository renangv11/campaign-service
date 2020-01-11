package com.visconde.campaignservice.controller;

import com.visconde.campaignservice.datacontract.CampaignDataContract;
import com.visconde.campaignservice.service.imp.CampaignServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
public class CampaignController {

    private final CampaignServiceImp campaignServiceImp;

    @PostMapping(value = "/campanha")
    public ResponseEntity<CampaignDataContract> createCampaign(@RequestBody CampaignDataContract requestBody){
        return ResponseEntity.status(CREATED).body(campaignServiceImp.createCampaign(requestBody));
    }

}
