package com.ligerlearn.example.model.entities;

import com.ligerlearn.example.jpa.hibernate_types.SQLXMLType;
import com.ligerlearn.example.model.utils.ModelJaxBContext;
import com.ligerlearn.example.model.xml.Address;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A simple 'Person' JPA entity which has its address field as XML.
 */
@Entity
public class Person implements Serializable {
    /* *************************************************************
     *                        Static Fields
     * *************************************************************/
    private final static long serialVersionUID = 7702L;
    private final static Logger log = LoggerFactory.getLogger(Person.class);

    /* *************************************************************
     *                       Instance Fields
     * *************************************************************/
    /**
     * The ID of the person.
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false) @Getter @Setter
    private long id;

    /**
     * The first name of the person.
     */
    @Column(name = "first_name")
    @Getter @Setter
    private String firstName;

    /**
     * The surname of the person.
     */
    @Column(name = "surname")
    @Getter @Setter
    private String surname;

    /**
     * The address of the person as a POJO (which is transient i.e. not persisted).
     */
    @Getter
    transient private Address address;

    /**
     * The address of the person as an XML string.
     */
    @Type(type = SQLXMLType.FULLY_QUALIFIED_CLASS_NAME)
    @Column(name = "address", columnDefinition = "xml")
    private String addressAsXmlString;

    /* *************************************************************
     *               Considerations for the XML field
     * *************************************************************/
    /**
     * Set the address field - this also requires setting the XML string representation.
     */
    public void setAddress(Address address) {
        // Retrieve the model's JAXB context.
        ModelJaxBContext jaxbContext = ModelJaxBContext.getInstance();

        // We need to convert the address to an XML document string and set the string representation.
        addressAsXmlString = jaxbContext.marshallPojoToXmlString(Address.class, address);

        log.info("Set the address xml string representation to: {}", addressAsXmlString);

        // Set the POJO version, too.
        this.address = address;
    }

    /**
     * Setting of non-persisted fields in the entity once it is loaded.
     */
    @PostLoad
    private void postLoadFunction(){
        log.info("PostLoad method called for JPA entity");
        log.info("Attempting to unmarshall XML string to POJO: {}", addressAsXmlString);

        // The Address POJO is not persisted so when the entity is loaded it will
        // be initialised with null. However, the XML string is persisted - so we load
        // the POJO from the string (and avoid future NPEs).
        this.address = ModelJaxBContext.getInstance().unmarshallXmlDocumentToPojo(Address.class, addressAsXmlString);
    }
}
