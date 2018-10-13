package com.mytaxi.service.car;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import java.util.List;

public interface CarService
{

    CarDO find(Long carId) throws EntityNotFoundException;

    CarDO create(CarDO carDO) throws ConstraintsViolationException;

    CarDO update(Long carId, CarDO carDO) throws EntityNotFoundException;

    void delete(Long carId) throws EntityNotFoundException;

    List<CarDO> findByManufacturer(String manufacturer);
}
