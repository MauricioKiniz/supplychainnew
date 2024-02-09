package com.mksistemas.supply.organization.adapter.rest;

import java.net.URI;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mksistemas.supply.organization.GetAllOrganizationUseCase;
import com.mksistemas.supply.organization.OrganizationManagerUseCase;
import com.mksistemas.supply.organization.domain.Organization;
import com.mksistemas.supply.shared.library.query.QueryConstants;

import io.hypersistence.tsid.TSID;

@RestController
@RequestMapping("api/v1/organization")
class OrganizacaoController {

    private final OrganizationManagerUseCase organizationUseCase;
    private final GetAllOrganizationUseCase getAllUseCase;

    public OrganizacaoController(
        OrganizationManagerUseCase organizationUseCase,
        GetAllOrganizationUseCase getAllUseCase
    ) {
        this.organizationUseCase = organizationUseCase;
        this.getAllUseCase = getAllUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> createOrganization(
        @RequestBody OrganizationManagerUseCase.OrganizationCommand command
    ) {
        Organization organization = organizationUseCase.create(command);
        URI location = URI.create(
            String.format(
                "/api/v1/organization/%s",
                organization.getId().toString()
            )
        );
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateOrganization(
        @RequestParam(name = "id") TSID id,
        @RequestBody OrganizationManagerUseCase.OrganizationCommand command
    ) {
        organizationUseCase.update(command, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOrganization(@RequestParam(name = "id") TSID id) {
        organizationUseCase.remove(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Slice<?>> getAllOrganization(
        @RequestParam(value = "pageNo", defaultValue = QueryConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
        @RequestParam(value = "pageSize", defaultValue = QueryConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {
        return ResponseEntity.ok(getAllUseCase.executar(PageRequest.of(pageNo, pageSize)));
    }
}
