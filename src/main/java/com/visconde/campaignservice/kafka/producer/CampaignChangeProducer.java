package com.visconde.campaignservice.kafka.producer;

import com.visconde.campaignservice.datacontract.CampaignDataContract;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.visconde.campaignservice.util.MapperUtils.serializer;

@AllArgsConstructor
@Component
public class CampaignChangeProducer {

    private static final String TOPIC = "CampaignChangeTopic";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(CampaignDataContract payload){
        kafkaTemplate.send(TOPIC, serializer(payload));
    }

}
