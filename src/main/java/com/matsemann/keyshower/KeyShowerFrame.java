package com.matsemann.keyshower;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import static org.jnativehook.keyboard.NativeKeyEvent.*;

public class KeyShowerFrame extends JFrame implements NativeKeyListener {

    private Map<Integer, Button> buttons = new HashMap<>();

    public static final Color WASD_COLOR = new Color(200, 200, 200);
    public static final Color OTHER_COLOR = new Color(255, 255, 255);
    public static final Color PRESS_COLOR = new Color(72, 72, 72);

    public KeyShowerFrame() {
        super("Matsemann's SuperDuper KeyShower");

        buttons.put(VC_Q, new Button("Q", 10, 10, 50, 50, OTHER_COLOR));
        buttons.put(VC_W, new Button("W", 70, 10, 50, 50, WASD_COLOR));
        buttons.put(VC_E, new Button("E", 130, 10, 50, 50, OTHER_COLOR));

        buttons.put(VC_A, new Button("A", 10, 70, 50, 50, WASD_COLOR));
        buttons.put(VC_S, new Button("S", 70, 70, 50, 50, WASD_COLOR));
        buttons.put(VC_D, new Button("D", 130, 70, 50, 50, WASD_COLOR));

        buttons.put(VC_SHIFT_L, new Button("⇧", 10, 130, 50, 50, OTHER_COLOR));
        buttons.put(VC_SPACE, new Button("_", 70, 130, 50, 50, OTHER_COLOR));
        buttons.put(VC_V, new Button("V", 130, 130, 50, 50, OTHER_COLOR));



        setLayout(null);
        buttons.values().forEach(this::add);

//        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.BLUE);
        setSize(300, 300);
        setVisible(true);


        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setBounds(e.getXOnScreen(), e.getYOnScreen(), getWidth(), getHeight());
            }
        });
    }



    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        Button button = buttons.get(e.getKeyCode());
        if (button != null) {
            button.down();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
        Button button = buttons.get(e.getKeyCode());
        if (button != null) {
            button.up();
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        // do nothing
    }


    private static class Button extends JLabel {

        private Color up;

        public Button(String name, int x, int y, int w, int h, Color up) {
            this.up = up;
            setText(name);
            setBounds(x, y, w, h);
            setOpaque(true);
            setBackground(up);
            setVerticalAlignment(CENTER);
            setHorizontalAlignment(CENTER);
        }

        public void down() {
            setBackground(PRESS_COLOR);
        }

        public void up() {
            setBackground(up);
        }

    }
}
