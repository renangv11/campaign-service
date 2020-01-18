package com.visconde.campaignservice.kafka.consumer;

import com.visconde.campaignservice.datacontract.ClubMemberDataContract;
import com.visconde.campaignservice.service.AssociateService;
import com.visconde.campaignservice.util.MapperUtils;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import static com.visconde.campaignservice.util.MapperUtils.desserializer;

@AllArgsConstructor
@Component
public class AssociateClubMemberConsumer {

    private final AssociateService associateService;

    @KafkaListener(topics = "${associateTopic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(String payload, Acknowledgment ack) {
        ClubMemberDataContract clubMemberDataContract = desserializer(payload, ClubMemberDataContract.class);
        associateService.associateClubMemberWithCampaigns(clubMemberDataContract);
        ack.acknowledge();
    }

}
