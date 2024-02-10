package com.mksistemas.supply.economicgroup;

import com.mksistemas.supply.economicgroup.EconomicGroupManagerUseCase.EconomicGroupCommand;

public class EconomicGroupDataSupplier {
    private EconomicGroupDataSupplier() {}

    public static EconomicGroupCommand getDefaultCommand() {
        return new EconomicGroupCommand("grupo teste principal", "bla bla bla, teste teste");
    }

    public static EconomicGroupCommand getUpdateCommand() {
        return new EconomicGroupCommand("Grupo Teste Principal", "xxxxxxxxxxxxx, yyyyy zzzzzz");
    }

}
