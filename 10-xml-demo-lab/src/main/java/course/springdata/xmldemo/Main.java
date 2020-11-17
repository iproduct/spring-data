package course.springdata.xmldemo;

import course.springdata.xmldemo.model.Address;
import course.springdata.xmldemo.model.Person;
import course.springdata.xmldemo.model.Persons;
import course.springdata.xmldemo.model.PhoneNumber;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Address> addresses = List.of(
                new Address(1L, "Bulgaria", "Sofia", "bul. Tzar Osvoboditel, 50"),
                new Address(2L, "Bulgaria", "Plovdiv", "ul. Gladston, 17")
        );
        List<Person> persons = List.of(
                new Person(1L, "Petar", "Petrov", addresses.get(0),
                        Set.of(new PhoneNumber("+359 885 123456"), new PhoneNumber("+359 2 9731425"))),
                new Person(2L, "Georgi", "Hristov", addresses.get(1),
                        Set.of(new PhoneNumber("+359 887 456677"), new PhoneNumber("+359 32 654237"))),
                new Person(3L, "Stamat", "Petrov", addresses.get(0),
                        Set.of(new PhoneNumber("+359 889 567895"), new PhoneNumber("+359 2 9731425")))
        );

        // 1. Create JAXBContext
        try {
            JAXBContext ctx = JAXBContext.newInstance(Person.class, Persons.class);

            //2. Create Marshaller
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8"); // Set custom encoding to the XML document

            //3. Marshal POJO to XML
            marshaller.marshal(persons.get(0), new File("person.xml"));
//            marshaller.marshal(persons.get(0), System.out);

            //4. Marshal multiple persons to persons.xml
            marshaller.marshal(new Persons(persons), new File("persons.xml"));
//            StringWriter out  = new StringWriter();
//            marshaller.marshal(new Persons(persons), out);
//            System.out.printf("StringWriter: %s%n", out.toString());

            //5. Unmarshal multiple Persons from XML to Java: Create unmarshaller
            Unmarshaller unmarshaller = ctx.createUnmarshaller();

            //6. Set validation
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File("xsd/persons.xsd"));
            unmarshaller.setSchema(schema);
            unmarshaller.setEventHandler(
                    (ValidationEvent ve) -> {
                        if(ve.getSeverity() != ValidationEvent.WARNING) {
                            System.out.printf("%s: Line:Col[%d:%d] - %s%n",
                                    ve.getSeverity(),
                                    ve.getLocator().getLineNumber(), ve.getLocator().getColumnNumber(),
                                    ve.getMessage()
                            );
                        }
                        return true;
                    }
            );

            //7. Unmarshall data to POJO
            Persons unmarshalled = (Persons) unmarshaller.unmarshal(new File("persons.xml"));
            unmarshalled.getPersons().forEach(p ->
                    System.out.printf("| %5d | %-15.15s | %-15.15s | %-40.40s | %-40.40s |%n",
                            p.getId(), p.getFirstName(), p.getLastName(),
                            p.getAddress().getCountry() + ", " + p.getAddress().getCity() + ", " + p.getAddress().getStreet(),
                            p.getPhoneNumbers().stream().map(PhoneNumber::getNumber)
                                    .collect(Collectors.joining(", "))));
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }
}
