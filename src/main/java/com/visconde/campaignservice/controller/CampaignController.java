package com.visconde.campaignservice.controller;

import com.visconde.campaignservice.datacontract.CampaignDataContract;
import com.visconde.campaignservice.service.imp.CampaignServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
public class CampaignController {

    private final CampaignServiceImp campaignServiceImp;

    @PostMapping(value = "/campanha")
    public ResponseEntity<CampaignDataContract> createCampaign(@RequestBody CampaignDataContract campaignDataContract){
        return ResponseEntity.status(CREATED).body(campaignServiceImp.createCampaign(campaignDataContract));
    }

    @GetMapping(value = "/campanha", params = "nome_time")
    public ResponseEntity<List<CampaignDataContract>> getCampaignByTeamName(@RequestParam("nome_time") String teamName){
        return ResponseEntity.status(OK).body(campaignServiceImp.getCampaignsByTeamName(teamName));
    }

    @GetMapping(value = "/campanha", params = "id_socio")
    public ResponseEntity<List<CampaignDataContract>> getCampaignByClubMember(@RequestParam("id_socio") Long clubMemberId){
        return ResponseEntity.status(OK).body(campaignServiceImp.getCampaignsByClubMember(clubMemberId));
    }

    @PutMapping(value = "/campanha")
    public ResponseEntity<CampaignDataContract> updateCampaign(@RequestBody CampaignDataContract campaignDataContract){
        return ResponseEntity.status(OK).body(campaignServiceImp.updateCampaign(campaignDataContract));
    }

    @DeleteMapping(value = "/campanha/{id_campanha}")
    public ResponseEntity deleteCampaign(@PathVariable("id_campanha") Long campaignId){
        campaignServiceImp.deleteCampaign(campaignId);
        return ResponseEntity.status(NO_CONTENT).body(null);
    }

}
