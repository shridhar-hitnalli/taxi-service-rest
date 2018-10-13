package com.mytaxi.service;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainvalue.Action;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.driver.DefaultDriverService;
import java.util.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by shridhar on 13/10/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DriverServiceTest.class)
public class DriverServiceTest
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private CarRepository carRepository;

    private DefaultDriverService driverService;


    @Before
    public void setup()
    {
        driverService = new DefaultDriverService(driverRepository, carRepository);
    }


    @Test
    public void driverFoundTest() throws Exception
    {
        DriverDO driverDO = new DriverDO("driver02", "driver02pw");
        when(driverRepository.findById(2L)).thenReturn(Optional.of(driverDO));

        DriverDO driver = driverService.find(2L);

        assertThat(driver)
            .isNotNull()
            .extracting("username", "password")
            .contains("driver02", "driver02pw");
    }


    @Test
    public void driverNotFoundExceptionTest() throws Exception
    {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Could not find entity with id: 9");

        driverService.find(9L);

    }


    @Test
    public void selectCarByDriverTest() throws Exception
    {
        CarDO carDO = new CarDO(2L, "MH02AS3532", new ManufacturerDO("tesla"));
        DriverDO driverDO = mock(DriverDO.class);

        when(carRepository.findById(2L)).thenReturn(Optional.of(carDO));
        when(driverRepository.findById(4L)).thenReturn(Optional.of(driverDO));

        driverService.selectOrDeselectCar(4L, 2L, Action.SELECT);

        verify(driverDO).setCar(carDO);
    }


    @Test
    public void carInUseExceptionTest() throws Exception
    {
        CarDO carDO = new CarDO(2L,"MH02AS3532", new ManufacturerDO("tesla"));
        DriverDO driverDO = mock(DriverDO.class);
        carDO.setDriver(driverDO);
        when(carRepository.findById(2L)).thenReturn(Optional.of(carDO));
        when(driverRepository.findById(5L)).thenReturn(Optional.of(driverDO));


        thrown.expect(CarAlreadyInUseException.class);
        thrown.expectMessage("car is already in use by another driver");

        driverService.selectOrDeselectCar(5L, 2L, Action.SELECT);

    }


    @Test
    public void deselectCarTest() throws Exception
    {
        CarDO carDO = new CarDO(1L,"MH02AS3532", new ManufacturerDO("tesla"));

        DriverDO driverDO = mock(DriverDO.class);

        when(driverDO.getCar()).thenReturn(carDO);
        when(carRepository.findById(1L)).thenReturn(Optional.of(carDO));
        when(driverRepository.findById(4L)).thenReturn(Optional.of(driverDO));

        driverService.selectOrDeselectCar(4L, 1L, Action.DESELECT);
        verify(driverDO).setCar(isNull());
    }


    @Test
    public void driveCreateTest() throws Exception
    {
        DriverDO driver = new DriverDO("driver11", "driver11pwd");

        driverService.create(driver);

        verify(driverRepository, only()).save(driver);
    }


    @Test
    public void createDriverExceptionTest() throws Exception
    {
        DriverDO driver = new DriverDO("", "passwd12");
        when(driverRepository.save(driver)).thenThrow(new DataIntegrityViolationException("error"));

        thrown.expect(ConstraintsViolationException.class);
        thrown.expectMessage("error");

        driverService.create(driver);

    }


}
