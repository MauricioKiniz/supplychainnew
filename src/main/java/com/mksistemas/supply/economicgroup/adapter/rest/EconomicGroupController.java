package com.mksistemas.supply.economicgroup.adapter.rest;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mksistemas.supply.economicgroup.EconomicGroupManagerUseCase;
import com.mksistemas.supply.economicgroup.GetAllEconomicGroupUseCase;
import com.mksistemas.supply.economicgroup.GetEconomicGroupByIdUseCase;
import com.mksistemas.supply.economicgroup.domain.EconomicGroup;
import com.mksistemas.supply.economicgroup.query.EconomicGroupRecord;

import io.hypersistence.tsid.TSID;

@RestController
@RequestMapping("api/v1/economicgroup")
public class EconomicGroupController {

    private final EconomicGroupManagerUseCase economicgroupUseCase;
    private final GetEconomicGroupByIdUseCase getByIdUseCase;
    private final GetAllEconomicGroupUseCase getAllUseCase;

    public EconomicGroupController(
        EconomicGroupManagerUseCase economicgroupUseCase, GetEconomicGroupByIdUseCase getByIdUseCase,
        GetAllEconomicGroupUseCase getAllUseCase
    ) {
        this.economicgroupUseCase = economicgroupUseCase;
        this.getByIdUseCase = getByIdUseCase;
        this.getAllUseCase = getAllUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> createEconomicGroup(
        @RequestBody EconomicGroupManagerUseCase.EconomicGroupCommand command
    ) {
        EconomicGroup economicGroup = economicgroupUseCase.create(command);
        URI location = URI.create(
            String.format(
                "/api/v1/economicgroup/%s",
                economicGroup.getAsTsid().toLowerCase()
            )
        );
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateEconomicGroup(
        @RequestParam(name = "id") TSID id,
        @RequestBody EconomicGroupManagerUseCase.EconomicGroupCommand command
    ) {
        economicgroupUseCase.update(command, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> removeEconomicGroup(
        @RequestParam(name = "id") TSID id
    ) {
        economicgroupUseCase.remove(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/link")
    public ResponseEntity<EconomicGroup> linkEconomicGroupWithOrganization(
        @PathVariable(name = "id") TSID id,
        @RequestBody EconomicGroupManagerUseCase.EconomicGroupLinkOrganizationCommand command
    ) {
        EconomicGroup economicGroup = economicgroupUseCase.linkWithOrganizations(id, command);
        URI location = URI.create(
            String.format(
                "/api/v1/economicgroup/%s",
                economicGroup.getAsTsid().toLowerCase()
            )
        );
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EconomicGroupRecord> getEconomicGroupById(@PathVariable(name = "id") TSID id) {
        EconomicGroupRecord economicGroup = getByIdUseCase.handle(id);
        return ResponseEntity.ok(economicGroup);
    }

    @GetMapping
    public ResponseEntity<List<EconomicGroupRecord>> getAllEconomic() {
        List<EconomicGroupRecord> allGroups = getAllUseCase.handle();
        return ResponseEntity.ok(allGroups);
    }

}
