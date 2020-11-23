package softuni.workshop.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {

   <O> O parseXml(Class<O> objectClass, String filePath);

   <O> void exportXml(O object, Class<O> objectClass, String filePath);

   <O> String exportXml(O object, Class<O> objectClass);
}
