package de.p29.menu;

import de.p29.dao.ArztDao;

import de.p29.pojo.Arzt;

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

public class ArztMenu {

    public Screen screen;
    public ArztDao arztDao;

    public ArztMenu(Screen screen, ArztDao arztDao) {
        this.screen = screen;
        this.arztDao = arztDao;
    }

    public void openArztMenu() {
        BasicWindow window = new BasicWindow("Was wollen Sie machen?");
        window.setHints(List.of(Hint.CENTERED));
        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        Panel panel = new Panel();
        panel.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        panel.addComponent(new Button("Hinzufügen", () -> {
            new InsertEntity<>(screen, "Neuer Arzt",
                List.of(
                    new InsertEntity.FieldDefinition<>("Name", Arzt::setName),
                    new InsertEntity.FieldDefinition<>("Telefon", Arzt::setTelefon),
                    new InsertEntity.FieldDefinition<>("Email", Arzt::setEmail),
                    new InsertEntity.FieldDefinition<>("Fachgebiet", Arzt::setFachgebiet)
                ),
                Arzt::new,
                arztDao::insertArzt
            ).open();
        }));

        panel.addComponent(new Button("Löschen", () -> {
            new DeleteEntity<>(screen, "Arzt Löschen",
                arztDao::findAllAerzte,
                Arzt::getId,
                arztDao::deleteArzt
            ).open();
        }));

        panel.addComponent(new Button("Zurück", window::close));
        window.setComponent(panel);

        WindowBasedTextGUI gui = new MultiWindowTextGUI(screen);
        gui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));
        gui.addWindowAndWait(window);
    }
}
