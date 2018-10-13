package com.mytaxi.service;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.service.car.CarService;
import com.mytaxi.service.car.DefaultCarService;
import java.util.List;
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
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by shridhar on 13/10/18.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CarServiceTest.class)
public class CarServiceTest
{

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private CarRepository carRepository;


    private CarService defaultCarService;


    @Before
    public void setup()
    {
        defaultCarService = new DefaultCarService(carRepository);
    }


    @Test
    public void carFoundTest() throws Exception
    {
        CarDO car = new CarDO(1L, "MH12EU2422", new ManufacturerDO("volvo"));
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));

        CarDO carLoaded = defaultCarService.find(1L);

        assertThat(carLoaded)
            .isNotNull()
            .extracting("licensePlate", "manufacturer.name")
            .contains("MH12EU2422", "volvo");
    }



    @Test
    public void carByIdNotFoundExceptionTest() throws Exception
    {
        thrown.expect(EntityNotFoundException.class);
        thrown.expectMessage("Could not find car entity with id: 1");

        defaultCarService.find(1L);
    }


    @Test
    public void carByManufacturerEmptyListTest() throws Exception
    {
        List<CarDO> cars= defaultCarService.findByManufacturer("xyz");
        assertThat(cars).hasSize(0);
    }


    @Test
    public void carCreateTest() throws Exception
    {
        CarDO car = new CarDO(10L,"MH032299", new ManufacturerDO("toyota"));

        defaultCarService.create(car);

        verify(carRepository, only()).save(car);
    }


    @Test
    public void carCreateExceptionTest() throws Exception
    {
        CarDO carDO = new CarDO(3L, "MH12EU2422", new ManufacturerDO("volvo"));
        when(carRepository.save(carDO)).thenThrow(new DataIntegrityViolationException("error"));

        thrown.expect(ConstraintsViolationException.class);
        thrown.expectMessage("error");

        defaultCarService.create(carDO);

    }

}
