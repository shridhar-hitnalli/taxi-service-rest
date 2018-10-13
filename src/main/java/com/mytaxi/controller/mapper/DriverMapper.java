package com.mytaxi.controller.mapper;

import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.GeoCoordinate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DriverMapper
{
    public static DriverDO makeDriverDO(DriverDTO driverDTO)
    {
        return new DriverDO(driverDTO.getUsername(), driverDTO.getPassword());
    }


    public static DriverDTO makeDriverDTO(DriverDO driverDO)
    {
        DriverDTO.DriverDTOBuilder driverDTOBuilder = DriverDTO.newBuilder()
            .setId(driverDO.getId())
            .setPassword(driverDO.getPassword())
            .setUsername(driverDO.getUsername());

        GeoCoordinate coordinate = driverDO.getCoordinate();
        if (coordinate != null)
        {
            driverDTOBuilder.setCoordinate(coordinate);
        }

        return driverDTOBuilder.createDriverDTO();
    }


    public static List<DriverDTO> makeDriverDTOList(Collection<DriverDO> drivers)
    {
        return drivers.stream()
            .map(DriverMapper::makeDriverDTO)
            .collect(Collectors.toList());
    }


    public static Predicate<DriverDO> findByCarParams(String manufacturer, Integer seatCount, Boolean convertible, Float rating, String engineType)
    {
        return d -> Objects.nonNull(d.getCar())
            && (manufacturer == null || d.getCar().getManufacturer().getName().equals(manufacturer))
            && (seatCount == null || d.getCar().getSeatCount().equals(seatCount))
            && (convertible == null || d.getCar().getConvertible() == convertible)
            && (rating == null || d.getCar().getRating() >= rating)
            && (engineType == null || d.getCar().getEngineType().toString().equalsIgnoreCase(engineType));
    }
}
