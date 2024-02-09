package com.mksistemas.supply.grupoeconomico;

public interface GrupoEconomicoManagerUseCase {

    record GrupoEconomicoCommand(String name, String description) {}

}
