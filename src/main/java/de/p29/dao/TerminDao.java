package de.p29.dao;

import de.p29.pojo.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jooq.Record;
import org.jooq.DSLContext;

import static de.projekt29.fpr.dao.jooq.Tables.PATIENT;
import static de.projekt29.fpr.dao.jooq.Tables.TERMIN;
import static de.projekt29.fpr.dao.jooq.Tables.TERMINPLAN;
import static de.projekt29.fpr.dao.jooq.Tables.ARZT;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TerminDao {

    @Inject
    DSLContext dsl;

    // Termin einfügen
    public String insertTermin(LocalDate datum) {
        String id = UUID.randomUUID().toString();
        dsl.insertInto(TERMIN)
           .set(TERMIN.ID, id)
           .set(TERMIN.DATUM, datum)
           .execute();
        return id;
    }

    // Terminplan-Eintrag einfügen (Verknüpfung)
    public void insertTerminplan(String terminId, String arztId, String patientId) {
        dsl.insertInto(TERMINPLAN)
           .set(TERMINPLAN.ID, UUID.randomUUID().toString())
           .set(TERMINPLAN.TERMIN_ID, terminId)
           .set(TERMINPLAN.ARZT_ID, arztId)
           .set(TERMINPLAN.PATIENT_ID, patientId)
           .execute();
    }

    // Termin anhand ID holen
    public Optional<Record> findTerminById(String id) {
        return dsl.select()
            .from(TERMIN)
            .where(TERMIN.ID.eq(id))
            .fetchOptional();
    }

    // Alle Termine holen
    public List<Record> findAllTermine() {
        return dsl.select()
            .from(TERMIN)
            .fetch();
    }

    // Termin löschen
    public boolean deleteTermin(String id) {
        int rows = dsl.deleteFrom(TERMIN)
            .where(TERMIN.ID.eq(id))
            .execute();
        return rows > 0;
    }

    // Optional: Termin + Arzt + Patient mit JOIN anzeigen (wenn du UI-Listen brauchst)
    public List<Record> findAllTerminDetails() {
        return dsl.select()
            .from(TERMINPLAN)
            .join(TERMIN).on(TERMIN.ID.eq(TERMINPLAN.TERMIN_ID))
            .fetch();
    }

    public List<Terminplan> findAllTerminplaene() {
        return dsl.select(TERMINPLAN.ID, TERMIN.ID, PATIENT.NAME, ARZT.NAME, ARZT.FACHGEBIET, TERMIN.DATUM)
            .from(TERMINPLAN)
            .join(PATIENT).on(PATIENT.ID.eq(TERMINPLAN.PATIENT_ID))
            .join(ARZT).on(ARZT.ID.eq(TERMINPLAN.ARZT_ID))
            .join(TERMIN).on(TERMIN.ID.eq(TERMINPLAN.TERMIN_ID))
            .fetch()
            .map(r -> new Terminplan(
                r.get(TERMINPLAN.ID),
                r.get(TERMIN.ID),
                r.get(PATIENT.NAME),
                r.get(ARZT.NAME),
                r.get(ARZT.FACHGEBIET),
                r.get(TERMIN.DATUM)
            ));
    }

    public boolean deleteTerminplanMitTermin(String terminplanId, String terminId) {
        int deletedPlan = dsl.deleteFrom(TERMINPLAN)
            .where(TERMINPLAN.ID.eq(terminplanId))
            .execute();
    
        int deletedTermin = dsl.deleteFrom(TERMIN)
            .where(TERMIN.ID.eq(terminId))
            .execute();
    
        return deletedPlan > 0 && deletedTermin > 0;
    }
}
