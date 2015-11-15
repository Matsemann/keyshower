package com.matsemann.keyshower.settings;

import com.matsemann.keyshower.FileParser;
import com.matsemann.keyshower.KeyShowerFrame;
import com.matsemann.keyshower.KeyboardFile;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SettingsWindow extends JFrame {

    private Settings settings = new Settings();

    private JCheckBox showFrameCheckbox;
    private JSlider bgSlider;
    private JSlider keySlider;

    public SettingsWindow() {
        super("KeyShower by Matsemann - Settings");
        setLayout(null);

        addShowFrameCheckbox();
        addBackgroundSlider();
        addKeySlider();
        addStartButton();
        addFileDropdown();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(300, 500);
        setVisible(true);
    }

    private void addShowFrameCheckbox() {
        showFrameCheckbox = new JCheckBox("Hide frame", settings.hideFrame);

        showFrameCheckbox.addActionListener(e -> {
            if (showFrameCheckbox.isSelected()) {
                settings.hideFrame = true;
                settings.bgAlpha = bgSlider.getValue();
                bgSlider.setEnabled(true);
            } else {
                settings.hideFrame = false;
                settings.bgAlpha = 255;
                bgSlider.setEnabled(false);
            }
            settings.hideFrame = showFrameCheckbox.isSelected();
        });

        showFrameCheckbox.setBounds(10, 10, 200, 30);
        add(showFrameCheckbox);
    }

    private void addBackgroundSlider() {
        bgSlider = new JSlider(0, 255, settings.bgAlpha);
        bgSlider.setEnabled(settings.hideFrame);
        bgSlider.addChangeListener(e -> settings.bgAlpha = bgSlider.getValue());

        bgSlider.setBounds(10, 60, 200, 30);
        add(bgSlider);
    }

    private void addKeySlider() {
        keySlider = new JSlider(0, 255, settings.bgAlpha);
        keySlider.setEnabled(settings.hideFrame);
        keySlider.addChangeListener(e -> settings.keyAlpha = keySlider.getValue());

        keySlider.setBounds(10, 100, 200, 30);
        add(keySlider);
    }

    private void addStartButton() {
        JButton show = new JButton("Show");
        show.addActionListener(e -> start());
        show.setBounds(50, 140, 60, 20);
        add(show);
    }

    private void addFileDropdown() {
        List<String> fileNames = getKeyboardFiles();
        JComboBox<String> fileDropdown = new JComboBox<>();
        fileNames.forEach(fileDropdown::addItem);

        fileDropdown.addActionListener(e -> settings.kbFile = "keyboardfiles/" + fileDropdown.getSelectedItem());

        fileDropdown.setSelectedIndex(0);
        fileDropdown.setBounds(10, 170, 200, 30);
        add(fileDropdown);
    }


    private void start() {
        try {
            KeyboardFile kbf = FileParser.parse(settings);

            GlobalScreen.registerNativeHook();

            KeyShowerFrame keyShowerFrame = new KeyShowerFrame(kbf, settings);
            GlobalScreen.addNativeKeyListener(keyShowerFrame);

            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            dispose();
        } catch (NativeHookException e) {
            err("Couldn't connect to keyboard events." + e.getMessage());
        } catch (RuntimeException e) {
            err(e.getMessage());
        }

    }

    private void err(String msg) {
        JOptionPane.showMessageDialog(this, "Something went wrong, sorry.\nError message: \n" + msg);
    }

    public List<String> getKeyboardFiles() {
        List<String> fileNames = new ArrayList<>();

        File[] files = Paths.get("keyboardfiles/").toFile().listFiles((f, n) -> n.endsWith(".kbf"));

        if (files == null) {
            fileNames.add("NO FILES FOUND");
            return fileNames;
        }

        for (File file : files) {
            fileNames.add(file.getName());
        }

        return fileNames;
    }
}
