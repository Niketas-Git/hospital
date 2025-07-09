package de.p29.tui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class Lines {
    public static void drawLines(TextGraphics tg, Screen screen, int widthTerm, int lengthTerm, String inputBottomRight, String inputCenter) {
        tg.setBackgroundColor(TextColor.ANSI.RED);
        tg.fillRectangle(new TerminalPosition(0, 0),
            new TerminalSize(widthTerm, 1), ' ');

        tg.fillRectangle(new TerminalPosition(0, screen.getTerminalSize().getRows() - 1),
            new TerminalSize(widthTerm, 1), ' ');

        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(1, screen.getTerminalSize().getRows() - 1, "Esc: Quit", SGR.BOLD);
        tg.putString(widthTerm - (inputBottomRight.length() + 1), 
            screen.getTerminalSize().getRows() - 1, inputBottomRight, SGR.BOLD);

        tg.setBackgroundColor(TextColor.ANSI.BLACK);
        tg.putString((widthTerm - inputCenter.length()) / 2, (lengthTerm / 2) - 1, inputCenter, SGR.ITALIC, SGR.BOLD);
    }
}
