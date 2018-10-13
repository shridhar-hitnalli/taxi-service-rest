package com.mytaxi.dataaccessobject;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by shridhar on 12/10/18.
 */
public interface CarRepository extends CrudRepository<CarDO, Long>
{

    List<CarDO> findByManufacturer(ManufacturerDO manufacturer);
}
