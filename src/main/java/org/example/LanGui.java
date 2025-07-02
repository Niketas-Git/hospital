/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.example;

import java.io.IOException;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

/**
 *
//  * @author nipa
 */
public class LanGui {
    public static void main(String[] args) throws IOException, InterruptedException {
        Terminal term = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(term);

        TextGraphics tg = screen.newTextGraphics();

        screen.startScreen();
        
        boolean keeprunning = true;
        StringBuilder sb = new StringBuilder();

        while (keeprunning) {

            KeyStroke keyPressed = term.pollInput();

            if (keyPressed!= null) {
                System.out.println(keyPressed);
                switch (keyPressed.getKeyType()) {
                    case Escape:
                        keeprunning = false;
                        break;
                    case ArrowRight:
                        tg.setForegroundColor(TextColor.ANSI.BLUE);
                        tg.setBackgroundColor(TextColor.ANSI.GREEN);
                        tg.putString( 0, 0, "size of the window: " 
                            + screen.getTerminalSize().getColumns() 
                            + screen.getTerminalSize().getRows());
                        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
                        screen.refresh();
                        break;
                        case Character:
                        sb.append(keyPressed.getCharacter());
                        break;
                    case Enter:
                        screen.clear();
                        tg.putString(10, 10, sb.toString(), SGR.BOLD);
                        sb.setLength(0); // statt neuer StringBuilder
                        screen.refresh();
                        break;                    
                    case ArrowLeft:
                        int row = 5, col = 60;
                        for (SGR sgr : SGR.values()) {
                            tg.putString(col, row++, sgr.name(), sgr);
                            System.out.println(sgr.name());
                            screen.refresh();
                        }
                        break;
                    case ArrowUp:
                        tg.setForegroundColor(TextColor.ANSI.MAGENTA);
                        for (int i = 29; i < 40; i++) {
                            tg.putString(i, 20, String.valueOf(Symbols.BLOCK_SOLID));
                            Thread.sleep(100);
                            screen.refresh();
                        }
                        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
                        break;
                    default:
                        System.out.println("default-branch");

                }
            }
        }

        screen.refresh();

        screen.stopScreen();
    }
}
