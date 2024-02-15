package com.mksistemas.supply.economicgroup;

import java.util.List;

import com.mksistemas.supply.economicgroup.query.EconomicGroupRecord;

public interface GetAllEconomicGroupUseCase {
    List<EconomicGroupRecord> handle();
}
