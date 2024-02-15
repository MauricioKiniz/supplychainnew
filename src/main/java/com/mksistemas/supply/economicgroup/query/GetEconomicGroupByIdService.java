package com.mksistemas.supply.economicgroup.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mksistemas.supply.economicgroup.GetEconomicGroupByIdUseCase;
import com.mksistemas.supply.economicgroup.domain.EconomicGroupView;
import com.mksistemas.supply.economicgroup.domain.EconomicGroupViewRepository;

import io.hypersistence.tsid.TSID;

@Service
@Transactional(readOnly = true)
class GetEconomicGroupByIdService implements GetEconomicGroupByIdUseCase {

    private final EconomicGroupViewRepository repository;

    public GetEconomicGroupByIdService(EconomicGroupViewRepository repository) {
        this.repository = repository;
    }

    @Override
    public EconomicGroupRecord handle(TSID id) {
        List<EconomicGroupView> economicGroupElements = repository.findById(id.toLong());
        return EconomicGroupRecord.generateFromView(economicGroupElements).getFirst();
    }

}
