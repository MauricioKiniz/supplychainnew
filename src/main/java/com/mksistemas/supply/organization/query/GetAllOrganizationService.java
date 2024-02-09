package com.mksistemas.supply.organization.query;

import java.util.List;

import org.hibernate.query.NativeQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mksistemas.supply.organization.GetAllOrganizationUseCase;
import com.mksistemas.supply.shared.library.query.QueryHelper;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.EntityManager;

@Service
@Transactional(readOnly = true)
class GetAllOrganizationService implements GetAllOrganizationUseCase {

    private static final String SQL = "select id, name, \"identity\", country_iso_code, zone_id from hub.organization where deleted is false limit :totalItems offset :startItem";

    private final EntityManager entityManager;

    public GetAllOrganizationService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Slice<Response> executar(Pageable pageable) {

        long off = pageable.getOffset();

        List<Response> responseList = entityManager
            .createNativeQuery(SQL)
            .setParameter("totalItems", pageable.getPageSize() + 1l)
            .setParameter("startItem", pageable.getOffset())
            .unwrap(NativeQuery.class)
            .setTupleTransformer((tuple, aliases) -> {
                String id = TSID.from((Long) tuple[0]).toLowerCase();
                String name = (String) tuple[1];
                String identity = (String) tuple[2];
                String countryIsoCode = (String) tuple[3];
                String zoneId = (String) tuple[4];
                return new Response(id, name, identity, countryIsoCode, zoneId);
            })
            .getResultList();

        return QueryHelper.<Response> processPage(responseList, pageable);
    }

}
