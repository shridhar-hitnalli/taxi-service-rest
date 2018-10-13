package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.GeoCoordinate;
import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO
{
    @JsonIgnore
    private Long id;

    @NotNull(message = "License plate can not be null!")
    private String licensePlate;

    private Boolean convertible;

    private Float rating;

    private Integer seatCount;

    private EngineType engineType;

    private ManufacturerDTO manufacturer;


    private CarDTO()
    {
    }


    public CarDTO(
        Long id,
        @NotNull(message = "License plate can not be null!") String licensePlate,
        ManufacturerDTO manufacturer,
        Boolean convertible,
        Float rating,
        Integer seatCount, EngineType engineType)
    {
        this.id = id;
        this.licensePlate = licensePlate;
        this.manufacturer = manufacturer;
        this.convertible = convertible;
        this.rating = rating;
        this.seatCount = seatCount;
        this.engineType = engineType;
    }


    public static CarDTOBuilder Builder()
    {
        return new CarDTOBuilder();
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


    public ManufacturerDTO getManufacturer()
    {
        return manufacturer;
    }


    public void setManufacturer(ManufacturerDTO manufacturer)
    {
        this.manufacturer = manufacturer;
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


    public Integer getSeatCount()
    {
        return seatCount;
    }


    public void setSeatCount(Integer seatCount)
    {
        this.seatCount = seatCount;
    }


    public EngineType getEngineType()
    {
        return engineType;
    }


    public void setEngineType(EngineType engineType)
    {
        this.engineType = engineType;
    }


    public static class CarDTOBuilder
    {
        private Long id;
        private String licensePlate;
        private GeoCoordinate coordinate;
        private ManufacturerDTO manufacturer;
        private Boolean convertible;
        private Float rating;
        private Integer seatCount;
        private EngineType engineType;

        public CarDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }

        public CarDTOBuilder setLicensePlate(String licensePlate)
        {
            this.licensePlate = licensePlate;
            return this;
        }

        public CarDTOBuilder setCoordinate(GeoCoordinate coordinate)
        {
            this.coordinate = coordinate;
            return this;
        }

        public CarDTOBuilder setManufacturer(ManufacturerDTO manufacturer)
        {
            this.manufacturer = manufacturer;
            return this;
        }

        public CarDTOBuilder setConvertible(Boolean convertible)
        {
            this.convertible = convertible;
            return this;
        }

        public CarDTOBuilder setRating(Float rating)
        {
            this.rating = rating;
            return this;
        }

        public CarDTOBuilder setSeatCount(Integer seatCount)
        {
            this.seatCount = seatCount;
            return this;
        }

        public CarDTOBuilder setEngineType(EngineType engineType)
        {
            this.engineType = engineType;
            return this;
        }


        public CarDTO CarDTOBuilder()
        {
            return new CarDTO(id, licensePlate, manufacturer, convertible, rating, seatCount, engineType);
        }
    }

}
