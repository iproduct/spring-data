package softuni.workshop.util;

import softuni.workshop.excepion.CustomXmlException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringWriter;

public class XmlParserImpl implements XmlParser {
    @Override
    @SuppressWarnings("unchecked")
    public <O> O parseXml(Class<O> objectClass, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(objectClass);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (O) unmarshaller.unmarshal(new File(filePath));
        } catch (JAXBException e) {
            throw new CustomXmlException(e.getMessage(), e);
        }
    }

    @Override
    public <O> void exportXml(O object, Class<O> objectClass, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(objectClass);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(object, new File(filePath));
        } catch (JAXBException e) {
            throw new CustomXmlException(e.getMessage(), e);
        }
    }

    @Override
    public <O> String exportXml(O object, Class<O> objectClass) {
        try {
            JAXBContext context = JAXBContext.newInstance(objectClass);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            marshaller.marshal(object, sw);
            return sw.toString();
        } catch (JAXBException e) {
            throw new CustomXmlException(e.getMessage(), e);
        }
    }
}
