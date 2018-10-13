package com.mytaxi.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotNull;

/**
 * Created by shridhar on 12/10/18.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManufacturerDTO
{
    @NotNull(message = "manufacturers can not be null!")
    private String name;


    public ManufacturerDTO(@NotNull(message = "manufacturer can not be null!") String name)
    {
        this.name = name;
    }


    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }


    public static ManufacturerDTOBuilder Builder()
    {
        return new ManufacturerDTOBuilder();
    }


    public static class ManufacturerDTOBuilder
    {
        private String name;


        public ManufacturerDTO.ManufacturerDTOBuilder setManufacturer(String name)
        {
            this.name = name;
            return this;
        }

        public ManufacturerDTO createManufacturerDTO()
        {
            return new ManufacturerDTO(name);
        }
    }

}
