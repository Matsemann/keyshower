package com.matsemann.keyshower;

import com.matsemann.keyshower.KeyboardFile.KeyData;

import javax.swing.*;
import java.awt.*;

class KeyLabel extends JLabel {

    private Color upColor;
    private Color downColor;
    private Color upFontColor;
    private Color downFontColor;

    public KeyLabel(KeyData data) {
        this.upColor = data.upColor;
        this.downColor = data.downColor;
        this.upFontColor = data.upFontColor;
        this.downFontColor = data.downFontColor;
        setText(data.text);
        setBounds(data.x, data.y, data.width, data.height);
        setFont(getFont().deriveFont((float) data.fontSize));

        setBackground(upColor);
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
        setBackground(downColor);
        setForeground(downFontColor);
    }

    public void up() {
        setBackground(upColor);
        setForeground(upFontColor);
    }


}
