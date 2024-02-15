package com.mksistemas.supply.economicgroup;

import com.mksistemas.supply.economicgroup.query.EconomicGroupRecord;

import io.hypersistence.tsid.TSID;

public interface GetEconomicGroupByIdUseCase {
    EconomicGroupRecord handle(TSID id);
}
