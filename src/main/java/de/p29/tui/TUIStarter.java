/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package de.p29.tui;

import de.p29.dao.*;

import de.p29.pojo.*;

import de.p29.menu.MenuManager;

import java.io.IOException;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import io.quarkus.runtime.QuarkusApplication;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 *
 * // * @author nipa
 */

@ApplicationScoped
public class TUIStarter implements QuarkusApplication{

    @Inject
    PatientDao patientDao;

    @Inject
    ArztDao arztDao;

    @Inject
    TerminDao terminDao;

    // Main
    @Override
    public int run(String[] args) throws IOException, InterruptedException {

        Terminal term = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(term);
        TextGraphics tg = screen.newTextGraphics();

        String continueString = "Enter: Continue";
        String optionsString = "Bitte eine der unteren Optionen wählen!";

        int widthTerm = screen.getTerminalSize().getColumns();
        int lengthTerm = screen.getTerminalSize().getRows();

        // Start
        screen.startScreen();
        screen.setCursorPosition(null);

        boolean keeprunning = true;

        // Run
        while (keeprunning) {

            // Hauptterminal
            tg.setBackgroundColor(TextColor.ANSI.BLACK);
            tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
            tg.fillRectangle(new TerminalPosition(0, 0), screen.getTerminalSize(), ' ');
            
            Title.drawTitle(tg, screen, "Willkommen in der HospitalDB");

            Lines.drawLines(tg, screen, widthTerm, lengthTerm, continueString, optionsString);

            screen.refresh();

            KeyStroke keyPressed = term.pollInput();
            if (keyPressed != null) {
                switch (keyPressed.getKeyType()) {
                    case Escape -> keeprunning = false;
                    case Enter -> {
                        new MenuManager(screen, patientDao, arztDao, terminDao).openHospitalDB();
                    }
                    default -> System.out.println("Unbekannte Eingabe");
                }
            }
        }
        screen.refresh();

        screen.stopScreen();

        return 0;
    }
}
