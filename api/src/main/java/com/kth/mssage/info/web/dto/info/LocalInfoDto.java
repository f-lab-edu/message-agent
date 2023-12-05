package com.kth.mssage.info.web.dto.info;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocalInfoDto {

    private final String regionCity;
    private final String regionTown;
    private final String regionVillage;
    private final String nx;
    private final String ny;
}
