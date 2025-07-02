/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.example;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;
import java.util.regex.Pattern;

import org.jooq.DSLContext;
import org.jooq.Record4;
import org.jooq.Record6;
import org.jooq.Result;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import static de.projekt29.fpr.dao.jooq.tables.Arzt.ARZT;
import static de.projekt29.fpr.dao.jooq.tables.Patient.PATIENT;
import static de.projekt29.fpr.dao.jooq.tables.Termin.TERMIN;
import static de.projekt29.fpr.dao.jooq.tables.Terminplan.TERMINPLAN;


/**
 *
 * // * @author nipa
 */

public class HospitalDB {

    // Titelfunktion
    public static void drawTitle(TextGraphics tg, Screen screen, String title) {
        int width = screen.getTerminalSize().getColumns();
        int x = (width - title.length()) / 2;
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(x, 0, title, SGR.BOLD, SGR.FRAKTUR);

    }

    // Main
    public static void main(String[] args) throws IOException, InterruptedException {

        Terminal term = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(term);

        TextGraphics tg = screen.newTextGraphics();

        String a = "Enter: Continue";
        String b = "Bitte eine der unteren Optionen wählen!";

        int width = screen.getTerminalSize().getColumns();
        int length = screen.getTerminalSize().getRows();

        // Start
        screen.startScreen();
        screen.setCursorPosition(null);

        boolean keeprunning = true;

        // Run
        while (keeprunning) {

            // Hauptterminal
            tg.setBackgroundColor(TextColor.ANSI.BLACK);
            tg.setForegroundColor(TextColor.ANSI.WHITE);
            tg.fillRectangle(new TerminalPosition(0, 0), screen.getTerminalSize(), ' ');

            tg.setBackgroundColor(TextColor.ANSI.RED);
            tg.fillRectangle(new TerminalPosition(0, 0),
                new TerminalSize(width, 1), ' ');

            drawTitle(tg, screen, "Willkommen in der HospitalDB");

            tg.fillRectangle(
                new TerminalPosition(0, screen.getTerminalSize().getRows() - 1),
                new TerminalSize(width, 1), ' ');

            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.putString(1, screen.getTerminalSize().getRows() - 1, "Esc: Quit", SGR.BOLD);
            tg.putString(width - (a.length() + 1), screen.getTerminalSize().getRows() - 1, a, SGR.BOLD);
            tg.setBackgroundColor(TextColor.ANSI.BLACK);

            tg.putString((width - b.length()) / 2, (length / 2) - 1, b, SGR.ITALIC, SGR.BOLD);
            screen.refresh();

            KeyStroke keyPressed = term.pollInput();

            if (keyPressed != null) {
                System.out.println(keyPressed);
                switch (keyPressed.getKeyType()) {

                    // Schließen
                    case Escape -> keeprunning = false;

                    // Auswahlterminal
                    case Enter -> {
                        WindowBasedTextGUI gui = new MultiWindowTextGUI(screen);
                        gui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));

                        BasicWindow window = new BasicWindow("HospitalDB");
                        window.setHints(Arrays.asList(Window.Hint.CENTERED));
                        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

                        panel.addComponent(new EmptySpace(new TerminalSize(4, 1)),
                                LinearLayout.createLayoutData(LinearLayout.Alignment.Fill));

                        Panel inner = new Panel(new LinearLayout(Direction.HORIZONTAL));

                        // Patientenübersicht
                        inner.addComponent(new Button("Patienten", () -> {
                            WindowBasedTextGUI aws = new MultiWindowTextGUI(screen);
                            aws.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));

                            BasicWindow auswahl = new BasicWindow("Was wollen Sie machen?");
                            auswahl.setHints(Arrays.asList(Window.Hint.CENTERED));
                            auswahl.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                            Panel aus = new Panel();

                            // Hinzufüge-Funktion
                            aus.addComponent(new Button("Hinzufügen", () -> {
                                WindowBasedTextGUI phui = new MultiWindowTextGUI(screen);
                                phui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));

                                BasicWindow phw = new BasicWindow("Daten des Patienten eingeben");
                                phw.setHints(Arrays.asList(Window.Hint.CENTERED));
                                phw.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                                DSLContext create = DBConnection.getContext();

                                TextBox eingabeFeldN = new TextBox()
                                .setPreferredSize(new TerminalSize(20, 1));
                                eingabeFeldN.setReadOnly(false);

                                Panel contentph =new Panel();
                                contentph.addComponent(new Label(" "));

                                contentph.addComponent(new Label("Name:"));
                                contentph.addComponent(eingabeFeldN);

                                TextBox eingabeFeldA = new TextBox()
                                .setPreferredSize(new TerminalSize(20, 1)); 
                                eingabeFeldA.setReadOnly(false);

                                contentph.addComponent(new Label(" "));

                                contentph.addComponent(new Label("Adresse:"));
                                contentph.addComponent(eingabeFeldA);
                                
                                TextBox eingabeFeldT = new TextBox()
                                .setPreferredSize(new TerminalSize(20, 1));
                                eingabeFeldT.setReadOnly(false);

                                contentph.addComponent(new Label(" "));

                                contentph.addComponent(new Label("Telefonnummer:"));
                                contentph.addComponent(eingabeFeldT);
                                
                                TextBox eingabeFeldE = new TextBox()
                                .setPreferredSize(new TerminalSize(20, 1));
                                eingabeFeldE.setReadOnly(false);

                                contentph.addComponent(new Label(" "));

                                contentph.addComponent(new Label("Email:"));
                                contentph.addComponent(eingabeFeldE);
                                
                                TextBox eingabeFeldB = new TextBox()
                                .setPreferredSize(new TerminalSize(20, 1)); 
                                eingabeFeldB.setReadOnly(false);

                                contentph.addComponent(new Label(" "));

                                contentph.addComponent(new Label("Bemerkung:"));
                                contentph.addComponent(eingabeFeldB);
                                
                                TextBox eingabeFeldI = new TextBox()
                                .setPreferredSize(new TerminalSize(20, 1));
                                eingabeFeldI.setReadOnly(false);

                                contentph.addComponent(new Label(" "));

                                contentph.addComponent(new Label("ICD-Key:"));
                                contentph.addComponent(eingabeFeldI);

                                contentph.addComponent(new Label(" "));
                                contentph.addComponent(new Button("Erstellen", () -> {
                                    String na = eingabeFeldN.getText();
                                    String ad = eingabeFeldA.getText();
                                    String te = eingabeFeldT.getText();
                                    String em = eingabeFeldE.getText();                                
                                    String be = eingabeFeldB.getText();                                
                                    String ic = eingabeFeldI.getText();                                
                                    String id = UUID.randomUUID().toString();

                                    create.insertInto(PATIENT, 
                                        PATIENT.ID, PATIENT.NAME, PATIENT.ADRESSE, PATIENT.TELEFON, PATIENT.EMAIL, PATIENT.BEMERKUNG, PATIENT.ICD_KEY)
                                        .values(id, na, ad, te, em, be, ic)
                                        .execute();

                                    BasicWindow infoh = new BasicWindow("Die Datenbank wurde aktualisiert!");
                                    infoh.setHints(Arrays.asList(Window.Hint.CENTERED));
                                    infoh.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                                    Panel info = new Panel();

                                    info.addComponent(new Button("OK", () -> {
                                        infoh.close();
                                        phw.close();
                                    }));
                                    infoh.setComponent(info);
                                    phui.addWindowAndWait(infoh);
                                }));

                                contentph.setLayoutManager(new LinearLayout(Direction.VERTICAL));
                                contentph.addComponent(new Button("Abbrechen", phw::close));

                                phw.setComponent(contentph);
                                phui.addWindowAndWait(phw);
                            }));

                            // Lösch-Funktion
                            aus.addComponent(new Button("Löschen", () -> {
                                WindowBasedTextGUI plui = new MultiWindowTextGUI(screen);
                                plui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));

                                BasicWindow plw = new BasicWindow("Patientenübersicht");
                                plw.setHints(Arrays.asList(Window.Hint.CENTERED));
                                plw.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                                Panel contentpl = new Panel();

                                DSLContext create = DBConnection.getContext();
                                ComboBox<PatientBOX> comboBox = new ComboBox<>();

                                if (create != null) {
                                    create.selectFrom(PATIENT)
                                    .fetch()
                                    .forEach(p -> comboBox.addItem(new PatientBOX(p.getId(), p.getName(), p.getEmail())));
                                } else {
                                    comboBox.addItem(new PatientBOX("", "Verbindung zur DB fehlgeschlagen", ""));
                                }

                                contentpl.addComponent(new Label(" "));

                                contentpl.addComponent(new Label("Patientenliste:"));
                                contentpl.addComponent(comboBox);
                                comboBox.takeFocus(); 

                                contentpl.addComponent(new Label(" "));
                                contentpl.setLayoutManager(new LinearLayout(Direction.VERTICAL));
                                contentpl.addComponent(new Button("Entfernen", () -> {
                                    PatientBOX selected = comboBox.getSelectedItem();

                                    if (selected != null && selected.getId() != null) {
                                        create.delete(PATIENT)
                                            .where(PATIENT.ID.eq(selected.getId()))
                                            .execute();
                                    }

                                    BasicWindow infoh = new BasicWindow("Die Datenbank wurde aktualisiert!");
                                    infoh.setHints(Arrays.asList(Window.Hint.CENTERED));
                                    infoh.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                                    Panel info = new Panel();

                                    info.addComponent(new Button("OK", () -> {
                                        infoh.close();
                                        plw.close();
                                    }));
                                    infoh.setComponent(info);
                                    plui.addWindowAndWait(infoh);
                                }));

                                contentpl.setLayoutManager(new LinearLayout(Direction.VERTICAL));
                                contentpl.addComponent(new Button("Abbrechen", plw::close));

                                plw.setComponent(contentpl);
                                plui.addWindowAndWait(plw);
                            }));
                            aus.addComponent(new Button("Zurück", auswahl::close));

                            auswahl.setComponent(aus);
                            aws.addWindowAndWait(auswahl);
                        }));
                        
                        // Ärzteübersicht
                        inner.addComponent(new EmptySpace(new TerminalSize(4, 1)));
                        inner.addComponent(new Button("Ärzte", () -> {
                            WindowBasedTextGUI hlz = new MultiWindowTextGUI(screen);
                            hlz.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));

                            BasicWindow choose = new BasicWindow("Was wollen Sie machen?");
                            choose.setHints(Arrays.asList(Window.Hint.CENTERED));
                            choose.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                            Panel azt = new Panel();

                            // Hinzufüge-Funktion
                            azt.addComponent(new Button("Hinzufügen", () -> {
                                WindowBasedTextGUI ahui = new MultiWindowTextGUI(screen);
                                ahui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));

                                BasicWindow ahw = new BasicWindow("Daten des Arztes eingeben");
                                ahw.setHints(Arrays.asList(Window.Hint.CENTERED));
                                ahw.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                                DSLContext create = DBConnection.getContext();

                                TextBox eingabeFeldN = new TextBox()
                                .setPreferredSize(new TerminalSize(20, 1));

                                Panel contentah =new Panel();
                                contentah.addComponent(new Label(" "));

                                contentah.addComponent(new Label("Name:"));
                                contentah.addComponent(eingabeFeldN);

                                TextBox eingabeFeldT = new TextBox()
                                .setPreferredSize(new TerminalSize(20, 1));

                                contentah.addComponent(new Label(" "));

                                contentah.addComponent(new Label("Telefonnummer:"));
                                contentah.addComponent(eingabeFeldT);

                                TextBox eingabeFeldE = new TextBox()
                                .setPreferredSize(new TerminalSize(20, 1));

                                contentah.addComponent(new Label(" "));

                                contentah.addComponent(new Label("Email:"));
                                contentah.addComponent(eingabeFeldE);

                                TextBox eingabeFeldF = new TextBox()
                                .setPreferredSize(new TerminalSize(20, 1));

                                contentah.addComponent(new Label(" "));

                                contentah.addComponent(new Label("Fachgebiet:"));
                                contentah.addComponent(eingabeFeldF);

                                contentah.addComponent(new Label(" "));
                                contentah.addComponent(new Button("Erstellen", () -> {
                                String na = eingabeFeldN.getText();
                                String te = eingabeFeldT.getText();
                                String em = eingabeFeldE.getText();
                                String fa = eingabeFeldF.getText();
                                String id = UUID.randomUUID().toString();

                                create.insertInto(ARZT, 
                                    ARZT.ID, ARZT.NAME, ARZT.TELEFON, ARZT.EMAIL, ARZT.FACHGEBIET)
                                    .values(id, na, te, em, fa)
                                    .execute();

                                BasicWindow infoh = new BasicWindow("Die Datenbank wurde aktualisiert!");
                                    infoh.setHints(Arrays.asList(Window.Hint.CENTERED));
                                    infoh.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                                    Panel info = new Panel();

                                    info.addComponent(new Button("OK", () -> {
                                        infoh.close();
                                        ahw.close();
                                    }));
                                    infoh.setComponent(info);
                                    ahui.addWindowAndWait(infoh);

                                }));

                                contentah.setLayoutManager(new LinearLayout(Direction.VERTICAL));
                                contentah.addComponent(new Button("Abbrechen", ahw::close));

                                ahw.setComponent(contentah);
                                ahui.addWindowAndWait(ahw);
                            }));

                            // Lösch-Funktion
                            azt.addComponent(new Button("Löschen", () -> {
                                WindowBasedTextGUI alui = new MultiWindowTextGUI(screen);
                                alui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));

                                BasicWindow alw = new BasicWindow("Ärzteübersicht");
                                alw.setHints(Arrays.asList(Window.Hint.CENTERED));
                                alw.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                                Panel contental = new Panel();

                                DSLContext create = DBConnection.getContext();
                                ComboBox<ArztBOX> comboBox = new ComboBox<>();

                                if (create != null) {
                                    create.selectFrom(ARZT)
                                    .fetch()
                                    .forEach(az -> comboBox.addItem(new ArztBOX(az.getId(), az.getName(), az.getFachgebiet(), az.getEmail())));
                                } else {
                                    comboBox.addItem(new ArztBOX ("", "Verbindung zur DB fehlgeschlagen", "", ""));
                                }

                                contental.addComponent(new Label(" "));

                                contental.addComponent(new Label("Ärzteliste:"));
                                contental.addComponent(comboBox);
                                comboBox.takeFocus();

                                contental.addComponent(new Label(" "));
                                contental.setLayoutManager(new LinearLayout(Direction.VERTICAL));
                                contental.addComponent(new Button("Entfernen", () -> {
                                    ArztBOX selected = comboBox.getSelectedItem();

                                    if (selected != null && selected.getId() != null) {
                                            create.delete(ARZT)
                                            .where(ARZT.ID.eq(selected.getId()))
                                            .execute();
                                    }

                                    BasicWindow infoh = new BasicWindow("Die Datenbank wurde aktualisiert!");
                                    infoh.setHints(Arrays.asList(Window.Hint.CENTERED));
                                    infoh.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                                    Panel info = new Panel();

                                    info.addComponent(new Button("OK", () -> {
                                        infoh.close();
                                        alw.close();
                                    }));
                                    infoh.setComponent(info);
                                    alui.addWindowAndWait(infoh);
                                }));

                                contental.setLayoutManager(new LinearLayout(Direction.VERTICAL));
                                contental.addComponent(new Button("Abbrechen", alw::close));

                                alw.setComponent(contental);
                                alui.addWindowAndWait(alw);
                            }));
                            azt.addComponent(new Button("Zurück", choose::close));

                            choose.setComponent(azt);
                            hlz.addWindowAndWait(choose);
                        }));

                        // Terminübersicht
                        inner.addComponent(new EmptySpace(new TerminalSize(4, 1)));
                        inner.addComponent(new Button("Termine", () -> {
                            WindowBasedTextGUI tui = new MultiWindowTextGUI(screen);
                            tui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));

                            BasicWindow tw = new BasicWindow("Terminplaner");
                            tw.setHints(Arrays.asList(Window.Hint.CENTERED));
                            tw.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                            Panel content = new Panel();

                            // Einsichts-Funktion
                            content.addComponent(new Button("Einsehen", () -> {
                                WindowBasedTextGUI teui = new MultiWindowTextGUI(screen);
                                teui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));

                                BasicWindow tew = new BasicWindow("Terminliste");
                                tew.setHints(Arrays.asList(Window.Hint.CENTERED));
                                tew.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                                Panel contentte = new Panel();

                                DSLContext create = DBConnection.getContext();
                                ComboBox<String> comboBox = new ComboBox<>();

                                if (create != null) {
                                    Result<Record4<String, String, String, LocalDate>> 
                                        result = create.select(PATIENT.NAME, ARZT.NAME, ARZT.FACHGEBIET, TERMIN.DATUM)
                                            .from(TERMINPLAN)
                                            .join(PATIENT).on(PATIENT.ID.eq(TERMINPLAN.PATIENT_ID))
                                            .join(ARZT).on(ARZT.ID.eq(TERMINPLAN.ARZT_ID))
                                            .join(TERMIN).on(TERMIN.ID.eq(TERMINPLAN.TERMIN_ID))
                                            .fetch();

                                    result.forEach(record -> {
                                        comboBox.addItem(
                                            record.get(PATIENT.NAME) + " | " + 
                                            record.get(ARZT.NAME) + " | " + 
                                            record.get(ARZT.FACHGEBIET) + " | " + 
                                            record.get(TERMIN.DATUM));
                                    });
                                } else {
                                        comboBox.addItem("Verbindung zur DB fehlgeschlagen");
                                }

                                contentte.addComponent(new Label(" "));

                                contentte.addComponent(new Label("Termine"));
                                contentte.addComponent(comboBox);
                                comboBox.takeFocus();

                                contentte.addComponent(new Label(" "));

                                contentte.setLayoutManager(new LinearLayout(Direction.VERTICAL));
                                contentte.addComponent(new Button("Abbrechen", tew::close));
    
                                tew.setComponent(contentte);
                                teui.addWindowAndWait(tew);
                            }));

                            // Hinzufüge-Funktion
                            content.addComponent(new Button("Hinzufügen", () -> {
                                WindowBasedTextGUI thui = new MultiWindowTextGUI(screen);
                                thui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));

                                BasicWindow thw = new BasicWindow("Termin erstellen");
                                thw.setHints(Arrays.asList(Window.Hint.CENTERED));
                                thw.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                                Panel contentth = new Panel();
    
                                DSLContext create = DBConnection.getContext();
                                ComboBox<String> comboBox = new ComboBox<>();
    
                                if (create != null) {
                                    create.selectFrom(ARZT)
                                        .fetch()
                                        .forEach(az -> comboBox.addItem(az.getName() + " | " + az.getFachgebiet() + " | " + az.getEmail()));
                                } else {
                                        comboBox.addItem("Verbindung zur DB fehlgeschlagen");
                                }

                                contentth.addComponent(new Label(" "));
    
                                contentth.addComponent(new Label("Ärzteliste:"));
                                contentth.addComponent(comboBox);
                                comboBox.takeFocus();

                                contentth.addComponent(new Label(" "));

                                ComboBox<String> combiBox = new ComboBox<>();

                                if (create != null) {
                                    create.selectFrom(PATIENT)
                                        .fetch()
                                        .forEach(p -> combiBox.addItem(p.getName() + " | " + p.getEmail()));
                                } else {
                                    combiBox.addItem("Verbindung zur DB fehlgeschlagen");
                                }

                                contentth.addComponent(new Label("Patientenliste:"));
                                contentth.addComponent(combiBox);

                                contentth.addComponent(new Label(" "));

                                contentth.addComponent(new Label("Datum:"));

                                Panel reihe = new Panel(new LinearLayout(Direction.HORIZONTAL));

                                Panel reiheY = new Panel(new LinearLayout(Direction.VERTICAL));

                                TextBox eingabeFeldY = new TextBox()
                                .setValidationPattern(Pattern.compile("\\d{0,4}"))
                                .setPreferredSize(new TerminalSize(20, 1));
                                eingabeFeldY.setReadOnly(false);
                                
                                reiheY.addComponent(new Label("YYYY:"));
                                reiheY.addComponent(eingabeFeldY);

                                Panel reiheM = new Panel(new LinearLayout(Direction.VERTICAL));

                                TextBox eingabeFeldM = new TextBox()
                                .setValidationPattern(Pattern.compile("\\d{0,2}"))
                                .setPreferredSize(new TerminalSize(20, 1));
                                eingabeFeldM.setReadOnly(false);

                                reiheM.addComponent(new Label("MM:"));
                                reiheM.addComponent(eingabeFeldM);

                                Panel reiheD = new Panel(new LinearLayout(Direction.VERTICAL));

                                TextBox eingabeFeldD = new TextBox()
                                .setValidationPattern(Pattern.compile("\\d{0,2}"))
                                .setPreferredSize(new TerminalSize(20, 1));
                                eingabeFeldD.setReadOnly(false);

                                reiheD.addComponent(new Label("DD:"));
                                reiheD.addComponent(eingabeFeldD);

                                reihe.addComponent(reiheY);
                                reihe.addComponent(reiheM);
                                reihe.addComponent(reiheD);

                                contentth.addComponent(reihe);

                                contentth.addComponent(new Label(" "));
                                contentth.setLayoutManager(new LinearLayout(Direction.VERTICAL));
                                contentth.addComponent(new Button("Erstellen", () -> {
                                    String am = "";
                                    String pm = "";
                                    String dt = eingabeFeldY.getText() + "-" + eingabeFeldM.getText() + "-" + eingabeFeldD.getText();
                                    LocalDate date = LocalDate.parse(dt);
                                    String idt = UUID.randomUUID().toString();

                                    create.insertInto(TERMIN, 
                                        TERMIN.ID, TERMIN.DATUM)
                                        .values(idt, date)
                                        .execute();

                                    String idtp = UUID.randomUUID().toString();

                                    String selectedA = comboBox.getSelectedItem();

                                    if (selectedA != null && selectedA.contains("|")) {
                                    String[] teile = selectedA.split(" \\| ");
                                        if (teile.length >= 3) {
                                            String amail = teile[2].trim();
                                            am = amail;
                                        }
                                    }

                                    String selectedP = combiBox.getSelectedItem();

                                    if (selectedP != null && selectedP.contains("|")) {
                                    String[] teile = selectedP.split(" \\| ");
                                        if (teile.length >= 2) {
                                            String pmail = teile[1].trim();
                                            pm = pmail;
                                        }
                                    }

                                    String ida = create.select(ARZT.ID).from(ARZT).where(ARZT.EMAIL.eq(am)).fetchOne(ARZT.ID);

                                    String idp = create.select(PATIENT.ID).from(PATIENT).where(PATIENT.EMAIL.eq(pm)).fetchOne(PATIENT.ID);

                                    create.insertInto(TERMINPLAN,
                                        TERMINPLAN.ID, TERMINPLAN.ARZT_ID, TERMINPLAN.PATIENT_ID, TERMINPLAN.TERMIN_ID)
                                        .values(idtp, ida, idp, idt)
                                        .execute();

                                    BasicWindow infoh = new BasicWindow("Die Terminliste wurde aktualisiert!");
                                    infoh.setHints(Arrays.asList(Window.Hint.CENTERED));
                                    infoh.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                                        Panel info = new Panel();

                                        info.addComponent(new Button("OK", () -> {
                                        infoh.close();
                                        thw.close();
                                    }));
                                    infoh.setComponent(info);
                                    thui.addWindowAndWait(infoh);
                                }));    
                                contentth.addComponent(new Button("Abbrechen", thw::close));
    
                                thw.setComponent(contentth);
                                thui.addWindowAndWait(thw);
                            }));

                            // Lösch-Funktion
                            content.addComponent(new Button("Löschen", () -> {
                                WindowBasedTextGUI tlui = new MultiWindowTextGUI(screen);
                                tlui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLACK));

                                BasicWindow tlw = new BasicWindow("Termin entfernen");
                                tlw.setHints(Arrays.asList(Window.Hint.CENTERED));
                                tlw.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                                Panel contenttl = new Panel();
    
                                DSLContext create = DBConnection.getContext();
                                ComboBox<TerminBOX> comboBox = new ComboBox<>();

                                if (create != null) {
                                    Result<Record6<String, String, String, String, String, LocalDate>> 
                                        result = create.select(TERMINPLAN.ID, TERMIN.ID, PATIENT.NAME, ARZT.NAME, ARZT.FACHGEBIET, TERMIN.DATUM)
                                            .from(TERMINPLAN)
                                            .join(PATIENT).on(PATIENT.ID.eq(TERMINPLAN.PATIENT_ID))
                                            .join(ARZT).on(ARZT.ID.eq(TERMINPLAN.ARZT_ID))
                                            .join(TERMIN).on(TERMIN.ID.eq(TERMINPLAN.TERMIN_ID))
                                            .fetch();

                                    result.forEach(record -> {
                                        comboBox.addItem(new TerminBOX(
                                            record.get(TERMINPLAN.ID),
                                            record.get(TERMIN.ID),
                                            record.get(PATIENT.NAME),
                                            record.get(ARZT.NAME),
                                            record.get(ARZT.FACHGEBIET),
                                            record.get(TERMIN.DATUM)
                                        ));
                                    });
                                } else {
                                        comboBox.addItem(new TerminBOX("", "", "Verbindung zur DB fehlgeschlagen", "", "", null));
                                }

                                contenttl.addComponent(new Label(" "));

                                contenttl.addComponent(new Label("Termine"));
                                contenttl.addComponent(comboBox);
                                comboBox.takeFocus();

                                contenttl.addComponent(new Label(" "));

                                contenttl.setLayoutManager(new LinearLayout(Direction.VERTICAL));
                                contenttl.addComponent(new Button("Entfernen", () -> {
                                    TerminBOX selected = comboBox.getSelectedItem();
                                    if (selected != null && selected.getTerminId() != null && selected.getTerminplanId() != null) {
                                        
                                        create.deleteFrom(TERMINPLAN)
                                                .where(TERMINPLAN.ID.eq(selected.getTerminplanId()))
                                                .execute();

                                        create.deleteFrom(TERMIN)
                                                .where(TERMIN.ID.eq(selected.getTerminId()))
                                                .execute();
                                    }

                                    BasicWindow infoh = new BasicWindow("Die Terminliste wurde aktualisiert!");
                                    infoh.setHints(Arrays.asList(Window.Hint.CENTERED));
                                    infoh.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

                                    Panel info = new Panel();

                                    info.addComponent(new Button("OK", () -> {
                                        infoh.close();
                                        tlw.close();
                                    }));
                                    infoh.setComponent(info);
                                    tlui.addWindowAndWait(infoh);
                                }));

                                contenttl.setLayoutManager(new LinearLayout(Direction.VERTICAL));
                                contenttl.addComponent(new Button("Abbrechen", tlw::close));
    
                                tlw.setComponent(contenttl);
                                tlui.addWindowAndWait(tlw);
                            }));        
                            content.setLayoutManager(new LinearLayout(Direction.VERTICAL));
                            content.addComponent(new Button("Zurück", tw::close));

                            tw.setComponent(content);
                            tui.addWindowAndWait(tw);
                        }));
                        inner.addComponent(new EmptySpace(new TerminalSize(4, 1)));
                        inner.addComponent(new Button("Zurück", window::close));

                        panel.addComponent(inner,
                                LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

                        panel.addComponent(new EmptySpace(new TerminalSize(1, 1)),
                                LinearLayout.createLayoutData(LinearLayout.Alignment.Fill));

                        window.setComponent(panel);
                        gui.addWindowAndWait(window);
                    }
                    default -> System.out.println("default-branch");
                }
            }
        }
        screen.refresh();

        screen.stopScreen();
    }
}
