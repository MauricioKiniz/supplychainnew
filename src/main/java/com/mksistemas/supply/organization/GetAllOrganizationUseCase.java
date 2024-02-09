package com.mksistemas.supply.organization;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface GetAllOrganizationUseCase {

    Slice<Response> executar(Pageable pageable);

    record Response(String id, String name, String identity, String countryIsoCode, String zoneId) {}

}
