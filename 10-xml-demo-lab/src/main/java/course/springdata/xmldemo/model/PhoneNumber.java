package course.springdata.xmldemo.model;

import lombok.*;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class PhoneNumber {
    private static long nextId = 0;
    @XmlAttribute(required = true)
    private Long id = ++ nextId;
    @NonNull
    private String number;

}
