package course.springdata.xmldemo;

import course.springdata.xmldemo.model.Address;
import course.springdata.xmldemo.model.Person;
import course.springdata.xmldemo.model.PhoneNumber;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<Address> addresses = List.of(
                new Address(1L, "Bulgaria", "Sofia", "bul. Tzar Osvoboditel, 50"),
                new Address(1L, "Bulgaria", "Plovdiv", "ul. Gladston, 17")
        );
        List<Person> persons = List.of(
                new Person(1L, "Petar", "Petrov", addresses.get(0),
                        Set.of(new PhoneNumber("+359 885 123456"), new PhoneNumber("+359 2 9731425"))),
                new Person(1L, "Georgi", "Hristov", addresses.get(1),
                        Set.of(new PhoneNumber("+359 887 456677"), new PhoneNumber("+359 32 654237"))),
                new Person(1L, "Stamat", "Petrov", addresses.get(0),
                        Set.of(new PhoneNumber("+359 889 567895"), new PhoneNumber("+359 2 9731425")))
        );

        // 1. Create JAXBContext
        try {
            JAXBContext ctx = JAXBContext.newInstance(Person.class);
            //2. Create Marshaller
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //3. Marshal POJO to XML
            marshaller.marshal(persons.get(0), new File("person.xml"));
            marshaller.marshal(persons.get(0), System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
