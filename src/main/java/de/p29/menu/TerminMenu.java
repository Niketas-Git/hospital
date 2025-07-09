package de.p29.menu;

import de.p29.dao.*;

import de.p29.pojo.Terminplan;

import de.p29.delete.DeleteTermin;

import de.p29.insert.CreateTermin;
import de.p29.insert.CreateTerminLayout;

import de.p29.select.*;

import java.util.List;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;

public class TerminMenu {

    public Screen screen;
    public PatientDao patientDao;
    public ArztDao arztDao;
    public  TerminDao terminDao;

    public TerminMenu(Screen screen, PatientDao patientDao, ArztDao arztDao, TerminDao terminDao) {
        this.screen = screen;
        this.patientDao = patientDao;
        this.arztDao = arztDao;
        this.terminDao = terminDao;
    }

    public void openTerminMenu() {
        BasicWindow window = new BasicWindow("Terminplaner");
        window.setHints(List.of(Hint.CENTERED));
        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        Panel panel = new Panel();
        panel.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        panel.addComponent(new Button("Einsehen", () -> {
            new OverviewTermine(terminDao, screen).open();
        }));

        panel.addComponent(new Button("Hinzufügen", () -> {
            new CreateTerminLayout(screen, arztDao, patientDao, terminDao).open();
        }));

        panel.addComponent(new Button("Löschen", () -> {
            new DeleteTermin(terminDao, screen).open();
        }));

        panel.addComponent(new Button("Zurück", window::close));
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        window.setComponent(panel);

        WindowBasedTextGUI gui = new MultiWindowTextGUI(screen);
        gui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));
        gui.addWindowAndWait(window);
    }
}
