package course.springdata.xmldemo.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Address {
    @XmlAttribute(required = true)
    private Long id;
    @NonNull
    private String country;
    @NonNull
    private String city;
    @NonNull
    private String street;

}
