package com.matsemann.keyshower;

import com.matsemann.keyshower.KeyboardFile.KeyData;
import com.matsemann.keyshower.settings.Settings;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyShowerFrame extends JFrame implements NativeKeyListener {

    private Map<Integer, KeyLabel> keys = new HashMap<>();

    public KeyShowerFrame(KeyboardFile kbf, Settings settings) {
        super("Keyshower by Matsemann");

        for (Map.Entry<Integer, KeyData> key : kbf.intToKeyData.entrySet()) {
            keys.put(key.getKey(), new KeyLabel(key.getValue()));
        }


        setLayout(null);
        keys.values().forEach(this::add);

        if (settings.hideFrame) {
            setUndecorated(true);

            if (settings.bgAlpha < 255) {
                setBackground(kbf.bgColor);
            } else {
                getContentPane().setBackground(kbf.bgColor);
            }
        } else {
            getContentPane().setBackground(kbf.bgColor);
        }

        setResizable(false);
        setSize(kbf.keyboardWidth, kbf.keyboardHeight);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);


        MouseDragger mouseDragger = new MouseDragger();
        addMouseListener(mouseDragger);
        addMouseMotionListener(mouseDragger);
    }


    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        KeyLabel keyLabel = keys.get(e.getKeyCode());
        if (keyLabel != null) {
            keyLabel.down();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        KeyLabel keyLabel = keys.get(e.getKeyCode());
        if (keyLabel != null) {
            keyLabel.up();
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // do nothing
    }


    private class MouseDragger extends MouseAdapter {
        int x, y;

        @Override
        public void mousePressed(MouseEvent e) {
            x = e.getX();
            y = e.getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            setBounds(e.getXOnScreen() - x, e.getYOnScreen() - y, getWidth(), getHeight());
        }
    }
}
