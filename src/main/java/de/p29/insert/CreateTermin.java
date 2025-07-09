package de.p29.insert;

import de.p29.dao.*;

import java.time.LocalDate;

import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class CreateTermin {

    private final ArztDao arztDao;
    private final PatientDao patientDao;
    private final TerminDao terminDao;

    public CreateTermin(ArztDao arztDao, PatientDao patientDao, TerminDao terminDao) {
        this.arztDao = arztDao;
        this.patientDao = patientDao;
        this.terminDao = terminDao;
    }

    public void erstellen(
            TerminEingabe eingabe,
            WindowBasedTextGUI gui,
            Window parentWindow
    ) {
        try {
            LocalDate date = eingabe.getDate();
            String arztId = arztDao.findIdByEmailArzt(eingabe.getArztEmail());
            String patientId = patientDao.findIdByEmailPatient(eingabe.getPatientEmail());
            String terminId = terminDao.insertTermin(date);
            terminDao.insertTerminplan(terminId, arztId, patientId);
            showInfo(gui, parentWindow, "Die Terminliste wurde aktualisiert!");
        } catch (Exception e) {
            showInfo(gui, parentWindow, "Fehler: " + e.getMessage());
        }
    }

    private void showInfo(WindowBasedTextGUI gui, Window parent, String message) {
        new MessageDialogBuilder()
            .setTitle("Info")
            .setText(message)
            .addButton(MessageDialogButton.OK)
            .build()
            .showDialog(gui);
        parent.close();
    }
} 
