package course.springdata.xmldemo.model;

import lombok.*;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Address {
    @XmlAttribute(required = true)
    private Long id;
    @NonNull
    @XmlElement(required = true, nillable = true)
    private String country;
    @NonNull
    @XmlElement(required = true)
    private String city;
    @NonNull
    private String street;

}
