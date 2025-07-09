package de.p29.tui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;

public class Title {
    public static void drawTitle(TextGraphics tg, Screen screen, String title) {
        int widthTerm = screen.getTerminalSize().getColumns();
        int x = (widthTerm - title.length()) / 2;
        tg.setForegroundColor(TextColor.ANSI.WHITE_BRIGHT);
        tg.putString(x, 0, title, SGR.BOLD, SGR.FRAKTUR);
    }
}
