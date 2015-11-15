package com.matsemann.keyshower;

import javax.swing.*;
import java.awt.*;

class KeyLabel extends JLabel {

    private Color up;
    private Color down;

    public KeyLabel(KeyboardFile.KeyData data) {
        this.up = data.upColor;
        this.down = data.downColor;
        setText(data.text);
        setBounds(data.x, data.y, data.width, data.height);
        setFont(getFont().deriveFont((float) data.fontSize));

        setBackground(up);
        setVerticalAlignment(CENTER);
        setHorizontalAlignment(CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Fix for alpha being drawn on top on alpha making colors multiply
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    public void down() {
        setBackground(down);
    }

    public void up() {
        setBackground(up);
    }


}
