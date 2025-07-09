package de.p29.menu;

import de.p29.dao.PatientDao;

import de.p29.pojo.Patient;

import de.p29.delete.DeleteEntity;
import de.p29.insert.InsertEntity;

import java.util.List;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;

public class PatientMenu {

    public Screen screen;
    public PatientDao patientDao;

    public PatientMenu(Screen screen, PatientDao patientDao) {
        this.screen = screen;
        this.patientDao = patientDao;
    }

    public void openPatientMenu() {
        BasicWindow window = new BasicWindow("Was wollen Sie machen?");
        window.setHints(List.of(Hint.CENTERED));
        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        Panel panel = new Panel();
        panel.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        panel.addComponent(new Button("Hinzufügen", () -> {
            new InsertEntity<>(screen, "Neuer Patient",
                List.of(
                    new InsertEntity.FieldDefinition<>("Name", Patient::setName),
                    new InsertEntity.FieldDefinition<>("Adresse", Patient::setAdresse),
                    new InsertEntity.FieldDefinition<>("Telefon", Patient::setTelefon),
                    new InsertEntity.FieldDefinition<>("Email", Patient::setEmail),
                    new InsertEntity.FieldDefinition<>("ICD-Key", Patient::setIcdKey),
                    new InsertEntity.FieldDefinition<>("Bemerkung", Patient::setBemerkung)
                ),
                Patient::new,
                patientDao::insertPatient
            ).open();
        }));

        panel.addComponent(new Button("Löschen", () -> {
            new DeleteEntity<>(screen, "Patient Löschen",
                patientDao::findAllPatients,
                Patient::getId,
                patientDao::deletePatient
            ).open();
        }));

        panel.addComponent(new Button("Zurück", window::close));
        window.setComponent(panel);

        WindowBasedTextGUI gui = new MultiWindowTextGUI(screen);
        gui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));
        gui.addWindowAndWait(window);
    }
}

