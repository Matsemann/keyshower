package com.matsemann.keyshower;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.nio.file.Paths;

public class KeyShower {

    public static void main(String[] args) {
        Settings settings = new Settings();
        KeyboardFile kbf = FileParser.parse(Paths.get("testjson.kbf").toFile(), settings);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            throw new RuntimeException("Couldn't connect to keyboard events." + e.getMessage());
        }

        KeyShowerFrame keyShowerFrame = new KeyShowerFrame(kbf, settings);

        GlobalScreen.addNativeKeyListener(keyShowerFrame);

    }
}
