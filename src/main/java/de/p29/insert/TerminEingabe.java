package de.p29.insert;

import de.p29.dao.*;

import de.p29.pojo.*;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;

public class TerminEingabe {
    public final ComboBox<String> arztBox = new ComboBox<>();
    public final ComboBox<String> patientBox = new ComboBox<>();
    
    
    public final TextBox yearBox = new TextBox().setValidationPattern(Pattern.compile("\\d{1,4}")).setPreferredSize(new TerminalSize(6, 1));
    public final TextBox monthBox = new TextBox().setValidationPattern(Pattern.compile("\\d{1,2}")).setPreferredSize(new TerminalSize(4, 1));
    public final TextBox dayBox = new TextBox().setValidationPattern(Pattern.compile("\\d{1,2}")).setPreferredSize(new TerminalSize(4, 1));

    public TerminEingabe(List<Arzt> arzt, List<Patient> patient) {
        arzt.forEach(a -> arztBox.addItem(a.getName() + " | " + a.getFachgebiet() + " | " + a.getEmail()));
        if (arzt.isEmpty()) arztBox.addItem("Keine Ärzte gefunden");

        patient.forEach(p -> patientBox.addItem(p.getName() + " | " + p.getEmail()));
        if (patient.isEmpty()) patientBox.addItem("Keine Patienten gefunden");
    }

    public Panel build() {
        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(new EmptySpace());

        panel.addComponent(new Label("Ärzteliste:")); 
        panel.addComponent(arztBox);

        panel.addComponent(new EmptySpace());
        
        panel.addComponent(new Label("Patientenliste:")); 
        panel.addComponent(patientBox);

        panel.addComponent(new EmptySpace());

        Panel datePanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        
        datePanel.addComponent(new Label("YYYY:")); 
        datePanel.addComponent(yearBox);
        
        datePanel.addComponent(new Label("MM:")); 
        datePanel.addComponent(monthBox);
        
        datePanel.addComponent(new Label("DD:")); 
        datePanel.addComponent(dayBox);
        
        panel.addComponent(new Label("Datum:")); 
        panel.addComponent(datePanel);

        return panel;
    }

    public LocalDate getDate() {
        return LocalDate.parse(pad(yearBox.getText()) + "-" + pad(monthBox.getText()) + "-" + pad(dayBox.getText()));
    }

    public String getArztEmail() {
        return extract(arztBox.getSelectedItem(), 2);
    }

    public String getPatientEmail() {
        return extract(patientBox.getSelectedItem(), 1);
    }

    private String extract(String text, int idx) {
        if (text != null && text.contains("|")) {
            String[] parts = text.split(" \\| ");
            if (parts.length > idx) return parts[idx].trim();
        }
        return "";
    }

    private String pad(String s) {
        return s.length() == 1 ? "0" + s : s;
    }
}
