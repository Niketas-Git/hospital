package org.example;

import java.time.LocalDate;

public class TerminBOX {

    private final String terminplanId;
    private final String terminId;
    private final String patientName;
    private final String arztName;
    private final String fachgebiet;
    private final LocalDate datum;

    public TerminBOX(String terminplanId, String terminId,  String patientName, String arztName, String fachgebiet, LocalDate datum) {
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
