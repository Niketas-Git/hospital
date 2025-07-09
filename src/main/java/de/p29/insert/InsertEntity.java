package de.p29.insert;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.screen.Screen;

public class InsertEntity<T> {

    private final Screen screen;
    private final String title;
    private final List<FieldDefinition<T>> fields;
    private final Supplier<T> builder;
    private final Consumer<T> onInsert;

    public InsertEntity(Screen screen, String title, List<FieldDefinition<T>> fields, Supplier<T> builder, Consumer<T> onInsert) {
        this.screen = screen;
        this.title = title;
        this.fields = fields;
        this.builder = builder;
        this.onInsert = onInsert;
    }

    @SuppressWarnings({"UseSpecificCatch", "CallToPrintStackTrace"})
    public void open() {
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);
        gui.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        BasicWindow window = new BasicWindow(title);
        window.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));
        window.setHints(List.of(Window.Hint.CENTERED));

        Panel panel = new Panel(new LinearLayout(Direction.VERTICAL));
        panel.setTheme(new SimpleTheme(TextColor.ANSI.WHITE_BRIGHT, TextColor.ANSI.BLACK));

        panel.addComponent(new EmptySpace());
        Map<FieldDefinition<T>, TextBox> fieldMap = new HashMap<>();

        for (FieldDefinition<T> field : fields) {
            panel.addComponent(new Label(field.label()));
            TextBox box = new TextBox();
            panel.addComponent(box);
            fieldMap.put(field, box);
        }

        panel.addComponent(new EmptySpace());

        panel.addComponent(new Button("Speichern", () -> {
            try {
                T instance = builder.get();

                for (FieldDefinition<T> field : fields) {
                    String value = fieldMap.get(field).getText();
                    field.setter().accept(instance, value);
                }

                try {
                    Method getId = instance.getClass().getMethod("getId");
                    Object idValue = getId.invoke(instance);

                    if (idValue == null || idValue.toString().isEmpty()) {
                        Method setId = instance.getClass().getMethod("setId", String.class);
                        setId.invoke(instance, UUID.randomUUID().toString());
                    }
                } catch (Exception ignored) {
                    
                }

                onInsert.accept(instance);
                window.close();

            } catch (Exception e) {
                e.printStackTrace();
                MessageDialog.showMessageDialog(gui, "Fehler", "Fehler beim Speichern:\n" + e.getMessage());
            }
        }));

        panel.addComponent(new Button("Abbrechen", window::close));
        window.setComponent(panel);
        gui.addWindowAndWait(window);
    }

    public record FieldDefinition<T>(
        String label,
        BiConsumer<T, String> setter
    ) {}
}
