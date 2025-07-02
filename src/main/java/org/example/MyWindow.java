/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package org.example;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.Panel;

/**
 *
 * @author nipa
 */
public class MyWindow extends BasicWindow {
    public MyWindow() {
        super("My Window!");
        Panel contentPane = new Panel();
        contentPane.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        contentPane.addComponent(new Label("This is the first label"));
        contentPane.addComponent(new Label("This is the second label, red").setForegroundColor(TextColor.ANSI.RED));
        contentPane.addComponent(new Label("This is the last label\nSpanning\nMultiple\nRows"));
        setComponent(contentPane);

    }
}
