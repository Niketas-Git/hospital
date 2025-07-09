package de.p29.delete;

import de.p29.dao.TerminDao;

import de.p29.pojo.Terminplan;

import java.util.List;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import com.googlecode.lanterna.screen.Screen;

public class DeleteTermin {

    private final TerminDao terminDao;
    private final Screen screen;

    public DeleteTermin(TerminDao terminDao, Screen screen) {
        this.terminDao = terminDao;
        this.screen = screen;
    }

    public void open() {
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);
        gui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        BasicWindow window = new BasicWindow("Termin löschen");
        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));
        
        List<Terminplan> termine = terminDao.findAllTerminplaene();
        ComboBox<Terminplan> comboBox = new ComboBox<>();

        if (termine.isEmpty()) {
            comboBox.addItem(new Terminplan("","","Keine Termine vorhanden","","", null));
        } else {
            termine.forEach(comboBox::addItem);
        }
        panel.addComponent(new EmptySpace());
        panel.addComponent(new Label("Wähle einen Termin zum Löschen:"));
        panel.addComponent(comboBox);
        panel.addComponent(new EmptySpace());
        panel.addComponent(new Button("Löschen", () -> {
            Terminplan selected = comboBox.getSelectedItem();
            if (selected != null && selected.getTerminplanId() != null && selected.getTerminId() != null) {
                boolean success = terminDao.deleteTerminplanMitTermin(
                    selected.getTerminplanId(), selected.getTerminId()
                );

                if (success) {
                    MessageDialog.showMessageDialog(gui, "Info", "Termin wurde erfolgreich gelöscht.");
                    window.close();
                } else {
                    MessageDialog.showMessageDialog(gui, "Fehler", "Löschen fehlgeschlagen.");
                }
            }
        }));

        panel.addComponent(new Button("Abbrechen", window::close));

        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }
}
