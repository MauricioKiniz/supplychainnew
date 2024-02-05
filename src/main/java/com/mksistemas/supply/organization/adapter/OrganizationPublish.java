package com.mksistemas.supply.organization.adapter;

import java.util.Objects;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.mksistemas.supply.organization.domain.OrganizationUpdateEvent;

@Component
class OrganizationPublish {

  @EventListener
  public void onOrganizationUpdate(OrganizationUpdateEvent event) {
    if (Objects.nonNull(event)) {

    }
  }

}
