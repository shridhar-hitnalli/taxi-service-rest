package com.mytaxi.controller.mapper;

import com.mytaxi.datatransferobject.ManufacturerDTO;
import com.mytaxi.domainobject.ManufacturerDO;

public class ManufacturerMapper
{
    public static ManufacturerDTO makeManufacturerDTO(ManufacturerDO manufacturerDO)
    {
        return ManufacturerDTO.Builder()
            .setManufacturer(manufacturerDO.getName())
            .createManufacturerDTO();

    }


    public static ManufacturerDO makeManufacturerDO(ManufacturerDTO manufacturerDTO)
    {
        return ManufacturerDO.Builder()
            .setManufacturer(manufacturerDTO.getName())
            .createManufacturerDO();
    }
}
