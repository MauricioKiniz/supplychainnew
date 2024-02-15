package com.mksistemas.supply.economicgroup.adapter.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.mksistemas.supply.economicgroup.EconomicGroupManagerUseCase;
import com.mksistemas.supply.shared.domain.EventKindEnum;

import io.hypersistence.tsid.TSID;

@Component
public class EconomicGroupAmqpReceiver {

    private final EconomicGroupManagerUseCase managerUseCase;

    public EconomicGroupAmqpReceiver(EconomicGroupManagerUseCase managerUseCase) {
        this.managerUseCase = managerUseCase;
    }

    @RabbitListener(queues = { EconomicGroupAmqpConstants.QUEUE_NAME })
    public void receiveMessages(OrganizationUpdateEvent event, Message message) {
        if (event.eventKind() == EventKindEnum.REMOVE) {
            managerUseCase.removeLink(TSID.from(event.id()));
        }
    }

}
