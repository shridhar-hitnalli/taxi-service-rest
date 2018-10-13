package com.mytaxi.service.driver;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.Action;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.DriverAlreadyUsingCarException;
import com.mytaxi.exception.DriverHasDifferentCarException;
import com.mytaxi.exception.DriverIsOfflineException;
import com.mytaxi.exception.EntityNotFoundException;
import java.util.List;

public interface DriverService
{

    DriverDO find(Long driverId) throws EntityNotFoundException;

    DriverDO create(DriverDO driverDO) throws ConstraintsViolationException;

    void delete(Long driverId) throws EntityNotFoundException;

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<DriverDO> find(OnlineStatus onlineStatus);

    void selectOrDeselectCar(long driverId, long carId, Action action) throws EntityNotFoundException, DriverIsOfflineException, DriverAlreadyUsingCarException,
                                                                            CarAlreadyInUseException, DriverHasDifferentCarException;
}
