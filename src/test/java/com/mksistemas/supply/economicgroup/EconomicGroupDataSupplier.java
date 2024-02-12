package com.mksistemas.supply.economicgroup;

import java.util.ArrayList;
import java.util.List;

import com.mksistemas.supply.economicgroup.EconomicGroupManagerUseCase.EconomicGroupCommand;
import com.mksistemas.supply.economicgroup.EconomicGroupManagerUseCase.EconomicGroupLinkOrganizationCommand;
import com.mksistemas.supply.economicgroup.EconomicGroupManagerUseCase.EconomicGroupLinkOrganizationElement;

import io.hypersistence.tsid.TSID;

public class EconomicGroupDataSupplier {
    private EconomicGroupDataSupplier() {}

    public static EconomicGroupCommand getDefaultCommand() {
        return new EconomicGroupCommand("grupo teste principal", "bla bla bla, teste teste");
    }

    public static EconomicGroupCommand getUpdateCommand() {
        return new EconomicGroupCommand("Grupo Teste Principal", "xxxxxxxxxxxxx, yyyyy zzzzzz");
    }

    public static EconomicGroupLinkOrganizationCommand getSuccessLinkCommand() {

        List<EconomicGroupLinkOrganizationElement> elements = new ArrayList<>();

        for (int count = 0; count < 8; count++) {
            EconomicGroupLinkOrganizationElement element = new EconomicGroupLinkOrganizationElement(
                TSID.from(1001l + count).toLowerCase(), true
            );
            elements.add(element);
        }

        return new EconomicGroupLinkOrganizationCommand(false, elements);
    }

    public static EconomicGroupLinkOrganizationCommand getRemoveAllLinkCommand() {
        return new EconomicGroupLinkOrganizationCommand(true, null);
    }

}
