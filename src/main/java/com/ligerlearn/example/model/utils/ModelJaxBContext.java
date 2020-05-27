package com.ligerlearn.example.model.utils;

import com.ligerlearn.example.model.xml.Address;
import lombok.SneakyThrows;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Utility class for the JaxB context for our model.
 */
public class ModelJaxBContext {
    /**
     * The singleton instance of this class.
     */
    private static ModelJaxBContext modelJaxBContext;

    /**
     * The JAXB context for our model.
     */
    private static JAXBContext jaxbContext;

    /**
     * The marshaller we will use to marshall POJOs to an XML String representation.
     */
    private static Marshaller marshaller;

    /**
     * The unmarshaller we will use to unmarshall XML Strings to their POJO representation.
     */
    private static Unmarshaller unmarshaller;

    /**
     * Retrieve the singleton instance, instantiating it (if needed).
     * N.B. this converts the checked exception to an unchecked one (@SneakyThrows).
     */
    @SneakyThrows
    public static ModelJaxBContext getInstance() {
        // If it has not been initialised, then initialise it.
        if(modelJaxBContext == null) {
            modelJaxBContext = new ModelJaxBContext();
        }

        return modelJaxBContext;
    }

    /**
     * Constructor which is only used by the getInstance method to initialise an instance.
     *
     * N.B. this converts the checked exception to an unchecked one (@SneakyThrows).
     */
    @SneakyThrows
    private ModelJaxBContext() {
        jaxbContext = JAXBContext.newInstance(Address.class);
        marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        unmarshaller = jaxbContext.createUnmarshaller();
    }

    /**
     * Marshall a given POJO to an XML string representation.
     * @param pojoClass The class of the POJO we want to marshall.
     * @param pojo      The POJO we want to marshall.
     * @param <T>       The type of the POJO.
     * @return          XML String representation of the POJO.
     */
    @SneakyThrows
    public <T> String marshallPojoToXmlString(Class<T> pojoClass, T pojo) {
        // Get string representation of the root element name
        String className = pojo.getClass().getName();

        // Create a JAXB element wrapper. The QName is a 'qualifiedName'.
        // We create a QName for the rootElement without a namespaceURI String (null)
        JAXBElement<T> rootElement = new JAXBElement<T>(
                new QName(null, className),
                pojoClass,  // e.g. Address.class
                pojo);      // e.g. Address

        // Marshal to a string and return it
        StringWriter writer = new StringWriter();
        marshaller.marshal(rootElement, writer);
        return writer.toString();
    }

    /**
     * Unmarshall a string to a POJO.
     * @param clazz      class of the POJO we want to unmarshall to.
     * @param xmlString  the string we want to unmarshall.
     * @param <T>        the type of the class we want to unmarshall to.
     * @return           the POJO representation of the class.
     */
    @SneakyThrows
    public <T> T unmarshallXmlDocumentToPojo(Class<T> clazz, String xmlString) {
        // Create an input stream we will pass to the unmarshaller.
        byte[] bytes = xmlString.getBytes(StandardCharsets.UTF_8);
        InputStream inputStream = new ByteArrayInputStream(bytes);

        // Unmarshal the XML
        JAXBElement<T> rootElement = unmarshaller.unmarshal(
                new StreamSource(inputStream),
                clazz);

        // Return the POJO from the XML
        return rootElement.getValue();
    }
}
