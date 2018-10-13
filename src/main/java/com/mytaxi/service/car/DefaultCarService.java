package com.mytaxi.service.car;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some driver specific things.
 * <p/>
 */
@Service
public class DefaultCarService implements CarService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

    private final DriverRepository driverRepository;

    private final CarRepository carRepository;


    @Autowired
    public DefaultCarService(final DriverRepository driverRepository, final CarRepository carRepository)
    {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }


    /**
     * finds a car by id.
     *
     * @param carId
     * @return found car
     * @throws EntityNotFoundException if no car with the given id was found.
     */
    @Override
    public CarDO find(Long carId) throws EntityNotFoundException
    {
        return findCarChecked(carId);
    }


    /**
     * Creates a new car.
     *
     * @param carDO
     * @return
     * @throws ConstraintsViolationException if a car already exists with the given licensePlate, ... .
     */
    @Override
    public CarDO create(CarDO carDO) throws ConstraintsViolationException
    {
        CarDO car;
        try
        {
            car = carRepository.save(carDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a car: {}", carDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return car;
    }


    /**
     * Deletes an existing car by id.
     *
     * @param carId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long carId) throws EntityNotFoundException
    {
        CarDO carDO = findCarChecked(carId);
        carDO.setDeleted(true);
    }


    @Override
    public List<CarDO> findByManufacturer(String manufacturer)
    {
        return carRepository.findByManufacturer(new ManufacturerDO(manufacturer));
    }


    /**
     * Update a car
     *
     * @param carId
     * @param carDO
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public CarDO update(Long carId, CarDO carDO) throws EntityNotFoundException {

        CarDO car = findCarChecked(carId);

        car.setRating(carDO.getRating());
        car.setConvertible(carDO.getConvertible());
        car.setLicensePlate(carDO.getLicensePlate());
        car.setEngineType(carDO.getEngineType());
        car.setSeatCount(carDO.getSeatCount());
        car.setManufacturer(carDO.getManufacturer());
        carRepository.save(car);

        return car;
    }



    private CarDO findCarChecked(Long carId) throws EntityNotFoundException
    {
        return carRepository.findById(carId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find car entity with id: " + carId));
    }

}
