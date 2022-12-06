package com.puc.tomasuloapp.core;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public static int defaultWidth = 1024;
    public static int defaultHeight = 600;
    public Dimension dimension;

    public Frame(String title, Boolean customTitleBar, int width, int height) {
        setTitle(title);

        dimension = new Dimension(width, height);

        setUndecorated(customTitleBar);

        if (customTitleBar) {
            // new CustomTitle Bar TODO
        }

        var dim = Toolkit.getDefaultToolkit().getScreenSize();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(dimension);
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
    }

    public Frame(String title) {
        this(title, false, Frame.defaultWidth, Frame.defaultHeight);
    }
}
