package com.mksistemas.supply.economicgroup.query;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mksistemas.supply.economicgroup.GetAllEconomicGroupUseCase;
import com.mksistemas.supply.economicgroup.domain.EconomicGroupView;
import com.mksistemas.supply.economicgroup.domain.EconomicGroupViewRepository;

@Service
@Transactional(readOnly = true)
class GetAllEconomicGroupService implements GetAllEconomicGroupUseCase {

    private final EconomicGroupViewRepository repository;

    public GetAllEconomicGroupService(EconomicGroupViewRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<EconomicGroupRecord> handle() {
        List<EconomicGroupView> allGroups = repository.findAll();
        return EconomicGroupRecord.generateFromView(allGroups);
    }

}
