package course.springdata.xmldemo;

import course.springdata.xmldemo.model.Address;
import course.springdata.xmldemo.model.Person;
import course.springdata.xmldemo.model.PhoneNumber;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Address address1 = new Address(1L, "Bulgaria", "Sofia", "bul. Tzar Osvoboditel, 50");
        Person person1 = new Person(1L, "Petar", "Petrov", address1,
                Set.of(new PhoneNumber("+359 885 123456"), new PhoneNumber("+359 2 9731425")));

        // 1. Create JAXBContext
        try {
            JAXBContext ctx = JAXBContext.newInstance(Person.class);
            //2. Create Marshaller
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //3. Marshal POJO to XML
            marshaller.marshal(person1, new File("person.xml"));
            marshaller.marshal(person1, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
