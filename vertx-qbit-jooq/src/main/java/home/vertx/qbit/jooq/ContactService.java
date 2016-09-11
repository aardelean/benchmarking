package home.vertx.qbit.jooq;

import home.vertx.qbit.jooq.dao.Tables;
import home.vertx.qbit.jooq.dao.tables.Contact;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    private DSLContext context;

    public List<ContactDomainDTO> getAllContacts(){
        Result<Record> records = context
                .select()
                .from(Contact.CONTACT)
                .limit(0, 10)
                .fetch();
        return records.stream()
                .map(r -> new ContactDomainDTO(r.into(Tables.CONTACT)))
                .collect(Collectors.toList());
    }

    public ContactDomainDTO getOneContact(Long id){
        Record r = context
                .select()
                .from(Contact.CONTACT)
                .where(Contact.CONTACT.ID.eq(id))
                .fetchOne();
        return new ContactDomainDTO(r.into(Tables.CONTACT));
    }
    public void select(Long id){
        Record r = context
                .select()
                .from(Contact.CONTACT)
                .where(Contact.CONTACT.ID.eq(id))
                .fetchOne();
    }
}
