package de.p29.menu;

import de.p29.dao.*;

import java.util.List;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window.Hint;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;

public class MenuManager {

    public final Screen screen;
    public final PatientDao patientDao;
    public final ArztDao arztDao;
    public final TerminDao terminDao;
    public final TerminMenu terminMenu;
    public final ArztMenu arztMenu;
    public final PatientMenu patientMenu;

    public MenuManager(Screen screen, PatientDao patientDao, ArztDao arztDao, TerminDao terminDao) {
        this.screen = screen;
        this.patientDao = patientDao;
        this.arztDao = arztDao;
        this.terminDao = terminDao;

        this.patientMenu = new PatientMenu(screen, patientDao);
        this.arztMenu = new ArztMenu(screen, arztDao);
        this.terminMenu = new TerminMenu(screen, patientDao, arztDao, terminDao);
    }

    public void openHospitalDB() {
        WindowBasedTextGUI gui = new MultiWindowTextGUI(screen);
        gui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        BasicWindow window = new BasicWindow("HospitalDB");
        window.setHints(List.of(Hint.CENTERED));
        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        Panel outerPanel = new Panel(new LinearLayout(Direction.VERTICAL));
        outerPanel.addComponent(new EmptySpace(new TerminalSize(4, 1)));
        outerPanel.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        Panel innerPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
        innerPanel.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        innerPanel.addComponent(new Button("Patienten", patientMenu::openPatientMenu));
        innerPanel.addComponent(new EmptySpace(new TerminalSize(4, 1)));

        innerPanel.addComponent(new Button("Ärzte", arztMenu::openArztMenu));
        innerPanel.addComponent(new EmptySpace(new TerminalSize(4, 1)));

        innerPanel.addComponent(new Button("Termine", terminMenu::openTerminMenu));
        innerPanel.addComponent(new EmptySpace(new TerminalSize(4, 1)));

        innerPanel.addComponent(new Button("Zurück", window::close));

        outerPanel.addComponent(innerPanel);
        outerPanel.addComponent(new EmptySpace(new TerminalSize(1, 1)));

        window.setComponent(outerPanel);
        gui.addWindowAndWait(window);
    }
}
