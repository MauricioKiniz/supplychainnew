package com.mksistemas.supply.economicgroup.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.mksistemas.supply.economicgroup.domain.EconomicGroupView;

import io.hypersistence.tsid.TSID;

public record EconomicGroupRecord(String id, String name, String description,
    List<EconomicGroupOrganizationLinkRecord> organizations) {

    private static final int NONE_ORGANIZATION = 0;

    public static List<EconomicGroupRecord> generateFromView(List<EconomicGroupView> groupView) {
        List<EconomicGroupView> groupViewOrdereds = new ArrayList<>(groupView);
        groupViewOrdereds.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));

        List<EconomicGroupRecord> allGroups = new ArrayList<>();
        EconomicGroupRecord egr = null;
        long actualId = 0;

        for (EconomicGroupView egv : groupViewOrdereds) {
            if (actualId != egv.getId()) {
                if (Objects.nonNull(egr)) {
                    allGroups.add(egr);
                }
                actualId = egv.getId();
                egr = new EconomicGroupRecord(
                    TSID.from(egv.getId()).toLowerCase(), egv.getGroupName(), egv.getGroupDescription(), new ArrayList<>()
                );
            }
            if (egv.getOrgId() != NONE_ORGANIZATION) {
                EconomicGroupOrganizationLinkRecord linkOrganization = new EconomicGroupOrganizationLinkRecord(
                    TSID.from(egv.getOrgId()).toLowerCase(), egv.getOrgName(), egv.getOrgIdentity()
                );
                egr.organizations.add(linkOrganization);
            }
        }
        if (Objects.nonNull(egr))
            allGroups.add(egr);
        return allGroups;
    }

}
