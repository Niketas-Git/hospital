package de.p29.dao;

import de.p29.pojo.Arzt;

import java.util.List;

import org.jooq.DSLContext;

import static de.projekt29.fpr.dao.jooq.Tables.ARZT;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ArztDao {

    @Inject
    DSLContext dsl;

    public List<Arzt> findAllAerzte() {
        return dsl
            .selectFrom(ARZT)
            .fetchInto(Arzt.class);        
    }

    public void insertArzt(Arzt arzt) {
        dsl.insertInto(ARZT)
            .set(dsl.newRecord(ARZT, arzt))
            .execute();
    }

    public boolean deleteArzt(String id) {
        return dsl.deleteFrom(ARZT)
            .where(ARZT.ID.eq(id))
            .execute() > 0;
    }

    public String findIdByEmailArzt(String email) {
        return dsl.select(ARZT.ID).from(ARZT)
            .where(ARZT.EMAIL.eq(email))
            .fetchOne(ARZT.ID);
    }
}
