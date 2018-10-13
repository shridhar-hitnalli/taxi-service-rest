package com.mytaxi.service.driver;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.Action;
import com.mytaxi.domainvalue.GeoCoordinate;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.DriverAlreadyUsingCarException;
import com.mytaxi.exception.DriverHasDifferentCarException;
import com.mytaxi.exception.DriverIsOfflineException;
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
public class DefaultDriverService implements DriverService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDriverService.class);

    private final DriverRepository driverRepository;

    private final CarRepository carRepository;

    @Autowired
    public DefaultDriverService(final DriverRepository driverRepository, final CarRepository carRepository)
    {
        this.driverRepository = driverRepository;
        this.carRepository = carRepository;
    }


    /**
     * Selects a driver by id.
     *
     * @param driverId
     * @return found driver
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    public DriverDO find(Long driverId) throws EntityNotFoundException
    {
        return findDriverChecked(driverId);
    }


    /**
     * Creates a new driver.
     *
     * @param driverDO
     * @return
     * @throws ConstraintsViolationException if a driver already exists with the given username, ... .
     */
    @Override
    public DriverDO create(DriverDO driverDO) throws ConstraintsViolationException
    {
        DriverDO driver;
        try
        {
            driver = driverRepository.save(driverDO);
        }
        catch (DataIntegrityViolationException e)
        {
            LOG.warn("ConstraintsViolationException while creating a driver: {}", driverDO, e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return driver;
    }


    /**
     * Deletes an existing driver by id.
     *
     * @param driverId
     * @throws EntityNotFoundException if no driver with the given id was found.
     */
    @Override
    @Transactional
    public void delete(Long driverId) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setDeleted(true);
    }


    /**
     * Update the location for a driver.
     *
     * @param driverId
     * @param longitude
     * @param latitude
     * @throws EntityNotFoundException
     */
    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException
    {
        DriverDO driverDO = findDriverChecked(driverId);
        driverDO.setCoordinate(new GeoCoordinate(latitude, longitude));
    }


    /**
     * Find all drivers by online state.
     *
     * @param onlineStatus
     */
    @Override
    public List<DriverDO> find(OnlineStatus onlineStatus)
    {
        return driverRepository.findByOnlineStatus(onlineStatus);
    }


    /**
     * Select or Deselect a car for a driver
     *
     * @param driverId
     * @param carId
     * @param action
     * @throws EntityNotFoundException,DriverHasDifferentCarException
     */
    @Override
    @Transactional
    public void selectOrDeselectCar(long driverId, long carId, Action action) throws EntityNotFoundException, DriverIsOfflineException, DriverAlreadyUsingCarException,
                                                                                   CarAlreadyInUseException, DriverHasDifferentCarException
    {
        LOG.debug("Selecting / deselecting car for driver: {} with Car: {}", driverId, carId);

        CarDO carDO = findCarChecked(carId);

        DriverDO driverDO = findDriverChecked(driverId);

        if (Action.SELECT == action) {
            if (driverDO.getOnlineStatus() == OnlineStatus.OFFLINE) {
                throw new DriverIsOfflineException("Driver is offline, can not select a car");
            }

            if (driverDO.getCar() != null) {
                throw new DriverAlreadyUsingCarException("Driver is already using a car " + carId);
            }

            if (carDO.getDriver() != null && carDO.getDriver().getId() != driverId) {
                throw new CarAlreadyInUseException("car is already in use by another driver" + carId);
            }

            //save car with driver ....
            carDO.setDriver(driverDO);
            carRepository.save(carDO);

            //save driver with car ...
            driverDO.setCar(carDO);
            driverRepository.save(driverDO);

            LOG.debug("driver has selected car : {}", driverDO.getCar());

        } else if (Action.DESELECT == action) {

            if (driverDO.getCar() == null) {
                return;
            }

            if (driverDO.getCar().getId() != carId) {
                throw new DriverHasDifferentCarException("Can not deselect the car, driver has not selected this car" + carId);
            }

            carDO.setDriver(null);
            carRepository.save(carDO);

            driverDO.setCar(null);
            driverRepository.save(driverDO);
            LOG.debug("driver has deselected car");

        }

    }



    private DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException
    {
        return driverRepository.findById(driverId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find entity with id: " + driverId));
    }


    private CarDO findCarChecked(Long carId) throws EntityNotFoundException
    {
        return carRepository.findById(carId)
            .orElseThrow(() -> new EntityNotFoundException("Could not find car entity with id: " + carId));
    }

}
