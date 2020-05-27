package com.ligerlearn.example.model.xml;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


/**
 * A simple representation of an "address" as an xml complex type.
 */
@Setter @Getter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Address")
public class Address implements Serializable {
    private final static long serialVersionUID = 7702L;

    @XmlElement(name = "DoorNumber", required = true)
    protected int doorNumber;

    @XmlElement(name = "RoadName", required = true)
    protected String roadName;

    @XmlElement(name = "PostalCode", required = true)
    protected String postalCode;
}

