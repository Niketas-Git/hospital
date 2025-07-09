package de.p29.select;

import de.p29.dao.TerminDao;

import de.p29.pojo.Terminplan;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;

import java.util.Arrays;
import java.util.List;

public class OverviewTermine {

    private final TerminDao terminDao;
    private final Screen screen;

    public OverviewTermine(TerminDao terminDao, Screen screen) {
        this.terminDao = terminDao;
        this.screen = screen;
    }

    public void open() {
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);
        gui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        BasicWindow window = new BasicWindow("Terminliste");
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));

        List<Terminplan> termine = terminDao.findAllTerminplaene();
        ComboBox<Terminplan> comboBox = new ComboBox<>();

        if (termine.isEmpty()) {
            comboBox.addItem(new Terminplan("","","Keine Termine vorhanden","","", null));
        } else {
            termine.forEach(comboBox::addItem);
        }

        panel.addComponent(new EmptySpace());
        panel.addComponent(new Label("Termine:"));
        panel.addComponent(comboBox);
        panel.addComponent(new EmptySpace());
        panel.addComponent(new Button("Abbrechen", window::close));

        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }
}
