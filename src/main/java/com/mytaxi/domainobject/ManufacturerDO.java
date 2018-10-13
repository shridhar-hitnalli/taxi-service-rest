package com.mytaxi.domainobject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by shridhar on 12/10/18.
 */
@Embeddable
public class ManufacturerDO
{
    @Column(name = "manufacturer")
    private String name;


    private ManufacturerDO()
    {
    }


    public ManufacturerDO(String name)
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


    public static ManufacturerDO.ManufacturerDOBuilder Builder()
    {
        return new ManufacturerDO.ManufacturerDOBuilder();
    }


    public static class ManufacturerDOBuilder
    {
        private String name;


        public ManufacturerDO.ManufacturerDOBuilder setManufacturer(String name)
        {
            this.name = name;
            return this;
        }

        public ManufacturerDO createManufacturerDO()
        {
            return new ManufacturerDO(name);
        }
    }

}
