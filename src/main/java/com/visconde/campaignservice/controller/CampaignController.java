package com.visconde.campaignservice.controller;

import com.visconde.campaignservice.datacontract.CampaignDataContract;
import com.visconde.campaignservice.service.imp.CampaignServiceImp;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
public class CampaignController {

    private final CampaignServiceImp campaignServiceImp;

    @ApiOperation(value = "Cadastra uma campanha nova, podendo atualizar " +
            "as datas finais caso houverem períodos vigentes iguais")
    @PostMapping(value = "/campanha", produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CampaignDataContract> createCampaign(@RequestBody CampaignDataContract campaignDataContract){
        return ResponseEntity.status(CREATED).body(campaignServiceImp.createCampaign(campaignDataContract));
    }

    @ApiOperation(value = "Busca as campanhas apartir do parameter recebido")
    @GetMapping(value = "/campanha", params = "nome_time", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CampaignDataContract>> getCampaignByTeamName(@RequestParam("nome_time") String teamName){
        return ResponseEntity.status(OK).body(campaignServiceImp.getCampaignsByTeamName(teamName));
    }

    @ApiOperation(value = "Busca as campanhas apartir do parameter recebido")
    @GetMapping(value = "/campanha", params = "id_socio", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CampaignDataContract>> getCampaignByClubMember(@RequestParam("id_socio") Long clubMemberId){
        return ResponseEntity.status(OK).body(campaignServiceImp.getCampaignsByClubMember(clubMemberId));
    }

    @ApiOperation(value = "Atualiza uma campanha por ID")
    @PutMapping(value = "/campanha/{id_campanha}", produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<CampaignDataContract> updateCampaign(@RequestBody CampaignDataContract campaignDataContract,
                                                               @PathVariable("id_campanha") Long campaignId){
        return ResponseEntity.status(OK).body(campaignServiceImp.updateCampaign(campaignDataContract, campaignId));
    }

    @ApiOperation(value = "Deleta uma campanha dado um ID")
    @DeleteMapping(value = "/campanha/{id_campanha}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity deleteCampaign(@PathVariable("id_campanha") Long campaignId){
        campaignServiceImp.deleteCampaign(campaignId);
        return ResponseEntity.status(NO_CONTENT).body(null);
    }

}
