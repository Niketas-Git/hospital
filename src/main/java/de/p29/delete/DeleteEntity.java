package de.p29.delete;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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
import com.googlecode.lanterna.screen.Screen;

public class DeleteEntity<T> {

    private final Screen screen;
    private final String title;
    private final Supplier<List<T>> loadItems;
    private final Function<T, String> getId;
    private final Consumer<String> deleteById;

    public DeleteEntity(Screen screen, String title,
        Supplier<List<T>> loadItems,
        Function<T, String> getId,
        Consumer<String> deleteById) {
            this.screen = screen;
            this.title = title;
            this.loadItems = loadItems;
            this.getId = getId;
            this.deleteById = deleteById;
    }

    public void open() {
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);
        gui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        BasicWindow window = new BasicWindow(title);
        window.setHints(List.of(Window.Hint.CENTERED));
        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));
        ComboBox<T> comboBox = new ComboBox<>();
        loadItems.get().forEach(comboBox::addItem);

        panel.addComponent(new EmptySpace());
        panel.addComponent(new Label("Auswahl:"));
        panel.addComponent(comboBox);
        panel.addComponent(new EmptySpace());

        panel.addComponent(new Button("Löschen", () -> {
            T selected = comboBox.getSelectedItem();
            if (selected != null) {
                deleteById.accept(getId.apply(selected));

                BasicWindow infoWindow = new BasicWindow("Info");
                infoWindow.setHints(List.of(Window.Hint.CENTERED));
                infoWindow.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));
                Panel infoPanel = new Panel();
                
                infoPanel.addComponent(new Label("Eintrag gelöscht!"));
                infoPanel.addComponent(new Button("OK", () -> {
                    infoWindow.close();
                    window.close();
                }));
                infoWindow.setComponent(infoPanel);
                gui.addWindowAndWait(infoWindow);
            }
        }));

        panel.addComponent(new Button("Abbrechen", window::close));
        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }
}

