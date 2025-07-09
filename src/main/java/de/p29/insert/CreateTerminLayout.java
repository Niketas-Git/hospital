package de.p29.insert;

import de.p29.dao.*;

import de.p29.pojo.*;

import com.googlecode.lanterna.gui2.*;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;

import java.util.List;

import com.googlecode.lanterna.TerminalSize;

public class CreateTerminLayout {

    private final ArztDao arztDao;
    private final PatientDao patientDao;
    private final TerminDao terminDao;
    private final Screen screen;

    public CreateTerminLayout(Screen screen, ArztDao arztDao, PatientDao patientDao, TerminDao terminDao) {
        this.screen = screen;
        this.arztDao = arztDao;
        this.patientDao = patientDao;
        this.terminDao = terminDao;
    }

    public void open() {
        WindowBasedTextGUI gui = new MultiWindowTextGUI(screen);
        gui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        BasicWindow window = new BasicWindow("Termin erstellen");
        window.setHints(List.of(Window.Hint.CENTERED));
        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        List<Arzt> aerzte = arztDao.findAllAerzte();
        List<Patient> patienten = patientDao.findAllPatients();
        TerminEingabe eingabe = new TerminEingabe(aerzte, patienten);

        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));
        panel.addComponent(eingabe.build());
        panel.addComponent(new EmptySpace(new TerminalSize(1, 1)));

        CreateTermin createTermin = new CreateTermin(arztDao, patientDao, terminDao);

        panel.addComponent(new Button("Erstellen", () -> {
            createTermin.erstellen(eingabe, gui, window);
        }));

        panel.addComponent(new Button("Abbrechen", window::close));

        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }
}

