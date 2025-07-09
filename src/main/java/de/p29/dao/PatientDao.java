package de.p29.dao;

import de.p29.pojo.Patient;

import java.util.List;

import org.jooq.DSLContext;

import static de.projekt29.fpr.dao.jooq.Tables.PATIENT;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PatientDao {
    
    @Inject
    DSLContext dsl;

    public List<Patient> findAllPatients() {
        return dsl
            .selectFrom(PATIENT)
            .fetchInto(Patient.class);
    }

    public void insertPatient(Patient patient) {
        dsl.insertInto(PATIENT)
            .set(dsl.newRecord(PATIENT, patient))
            .execute();
    }

    public boolean deletePatient(String id) {
        return dsl.deleteFrom(PATIENT)
            .where(PATIENT.ID.eq(id))
            .execute() > 0;
    }

    public String findIdByEmailPatient(String email) {
        return dsl.select(PATIENT.ID).from(PATIENT)
            .where(PATIENT.EMAIL.eq(email))
            .fetchOne(PATIENT.ID);
    }
}    


