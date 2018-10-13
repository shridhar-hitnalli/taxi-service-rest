package com.mytaxi.controller.mapper;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CarMapper
{

    public static CarDTO makeCarDTO(CarDO carDO)
    {
        CarDTO.CarDTOBuilder carDTOBuilder = CarDTO.Builder()
            .setId(carDO.getId())
            .setLicensePlate(carDO.getLicensePlate())
            .setSeatCount(carDO.getSeatCount())
            .setConvertible(carDO.getConvertible())
            .setRating(carDO.getRating())
            .setEngineType(carDO.getEngineType())
            .setManufacturer(ManufacturerMapper.makeManufacturerDTO(carDO.getManufacturer()));


        return carDTOBuilder.CarDTOBuilder();
    }


    public static List<CarDTO> makeCarDTOList(Collection<CarDO> cars)
    {
        return cars.stream()
            .map(CarMapper::makeCarDTO)
            .collect(Collectors.toList());
    }


    public static CarDO makeCarDO(CarDTO carDTO) {

        CarDO.CarDOBuilder carDOBuilder = new CarDO.CarDOBuilder()
            .setId(carDTO.getId())
            .setLicensePlate(carDTO.getLicensePlate())
            .setSeatCount(carDTO.getSeatCount())
            .setConvertible(carDTO.getConvertible())
            .setRating(carDTO.getRating())
            .setEngineType(carDTO.getEngineType())
            .setManufacturer(ManufacturerMapper.makeManufacturerDO(carDTO.getManufacturer()));

        return carDOBuilder.CarDOBuilder();
    }
}
