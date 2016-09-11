package home.vertx.qbit.jooq;

import home.vertx.qbit.jooq.dao.tables.records.ContactRecord;
import lombok.Data;

import java.text.SimpleDateFormat;

@Data
public class ContactDomainDTO {
    private String firstName;
    private String lastName;
    private Integer age;
    private String birthdate;
    private String country;
    private String street;
    private String city;
    private String plz;
    private Integer streetNo;
    public ContactDomainDTO(){}

    public ContactDomainDTO(ContactRecord contactRecord){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        firstName = contactRecord.getFirstname();
        lastName=contactRecord.getLastname();
        age = contactRecord.getAge();
        birthdate = sdf.format(contactRecord.getBirthdate());
    }
}
