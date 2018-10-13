package com.mytaxi.domainobject;

import com.mytaxi.domainvalue.EngineType;
import java.time.ZonedDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by shridhar on 12/10/18.
 */
@Entity
@Table(name = "car",
        uniqueConstraints = @UniqueConstraint(name = "uc_license_plate", columnNames = {"license_plate"}))
public class CarDO
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "license_plate")
    @NotNull(message = "License plate cannot be null!")
    private String licensePlate;

    @Column(nullable = false, name = "seat_count")
    @NotNull(message = "Seat count cannot be null!")
    private Integer seatCount;

    @Column
    private Boolean convertible;

    @Column
    private Float rating;

    @Enumerated(EnumType.STRING)
    @Column
    private EngineType engineType;

    @Column(nullable = false, name = "date_created")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    private Boolean deleted = false;

    @Embedded
    @NotNull(message = "Manufacturer cannot be null")
    private ManufacturerDO manufacturer;

    @OneToOne(mappedBy = "car", cascade = CascadeType.DETACH)
    private DriverDO driver;

    private CarDO()
    {

    }

    public CarDO(
        Long id,
        @NotNull(message = "License plate cannot be null!") String licensePlate,
        @NotNull(message = "Manufacturer cannot be null") ManufacturerDO manufacturer)
    {
        this.id = id;
        this.licensePlate = licensePlate;
        this.manufacturer = manufacturer;
    }

    public CarDO(
        Long id,
        @NotNull(message = "License plate cannot be null!") String licensePlate,
        @NotNull(message = "Seat count cannot be null!") Integer seatCount,
        Boolean convertible,
        Float rating,
        EngineType engineType,
        @NotNull(message = "Manufacturer cannot be null") ManufacturerDO manufacturer)
    {
        this.id = id;
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
    }


    public Long getId()
    {
        return id;
    }


    public void setId(Long id)
    {
        this.id = id;
    }


    public String getLicensePlate()
    {
        return licensePlate;
    }


    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }


    public Integer getSeatCount()
    {
        return seatCount;
    }


    public void setSeatCount(Integer seatCount)
    {
        this.seatCount = seatCount;
    }


    public Boolean getConvertible()
    {
        return convertible;
    }


    public void setConvertible(Boolean convertible)
    {
        this.convertible = convertible;
    }


    public Float getRating()
    {
        return rating;
    }


    public void setRating(Float rating)
    {
        this.rating = rating;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public void setEngineType(EngineType engineType)
    {
        this.engineType = engineType;
    }


    public ZonedDateTime getDateCreated()
    {
        return dateCreated;
    }


    public void setDateCreated(ZonedDateTime dateCreated)
    {
        this.dateCreated = dateCreated;
    }


    public Boolean getDeleted()
    {
        return deleted;
    }


    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }


    public ManufacturerDO getManufacturer()
    {
        return manufacturer;
    }


    public void setManufacturer(ManufacturerDO manufacturer)
    {
        this.manufacturer = manufacturer;
    }


    public DriverDO getDriver()
    {
        return driver;
    }


    public void setDriver(DriverDO driver)
    {
        this.driver = driver;
    }

    public static class CarDOBuilder
    {
        private Long id;
        private String licensePlate;
        private Integer seatCount;
        private Boolean convertible;
        private Float rating;
        private EngineType engineType;
        private ManufacturerDO manufacturer;

        public CarDOBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public CarDOBuilder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public CarDOBuilder setSeatCount(Integer seatCount) {
            this.seatCount = seatCount;
            return this;
        }

        public CarDOBuilder setConvertible(Boolean convertible) {
            this.convertible = convertible;
            return this;
        }

        public CarDOBuilder setRating(Float rating) {
            this.rating = rating;
            return this;
        }

        public CarDOBuilder setEngineType(EngineType engineType) {
            this.engineType = engineType;
            return this;
        }

        public CarDOBuilder setManufacturer(ManufacturerDO manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public CarDO CarDOBuilder()
        {
            return new CarDO(id, licensePlate, seatCount, convertible, rating, engineType, manufacturer);
        }
    }
}
