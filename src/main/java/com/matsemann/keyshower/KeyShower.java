package com.matsemann.keyshower;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

public class KeyShower {

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            System.out.println("Couldn't connect to keyboard events");
            e.printStackTrace();
            System.exit(1);
        }

        KeyShowerFrame keyShowerFrame = new KeyShowerFrame();
        GlobalScreen.addNativeKeyListener(keyShowerFrame);
    }
}
