package de.p29.pojo;

import java.time.LocalDate;

public class Terminplan {

    public String terminplanId;
    public String terminId;
    public String patientName;
    public String arztName;
    public String fachgebiet;
    public LocalDate datum;

    public Terminplan(String terminplanId, String terminId,  String patientName, String arztName, String fachgebiet, LocalDate datum) {
        this.terminplanId = terminplanId;
        this.terminId = terminId;
        this.patientName = patientName;
        this.arztName = arztName;
        this.fachgebiet = fachgebiet;
        this.datum = datum;
    }

    public String getTerminplanId() {
        return terminplanId;
    }

    public String getTerminId() {
        return terminId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getArztName() {
        return arztName;
    }

    public String getFachgebiet() {
        return fachgebiet;
    }

    public LocalDate getDatum() {
        return datum;
    }

    @Override
    public String toString() {
        return patientName + " | " + arztName + " | " + fachgebiet + " | " + datum;
    }
}
