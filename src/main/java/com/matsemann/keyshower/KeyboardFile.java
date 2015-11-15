package com.matsemann.keyshower;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class KeyboardFile {
    public static String CURRENT_VERSION = "v1";

    // Fields available in JSON and their default values
    public String version;
    public int keyboardWidth = 300;
    public int keyboardHeight = 300;

    public String backgroundColor = "#aaaaaa";

    public String defaultColor = "#ffffff";
    public String defaultPressedColor = "#000000";
    public String defaultFontColor = "#000000";
    public String defaultPressedFontColor = "#ffffff";


    public Map<String, KeyData> keys;

    // Converted and validated fields
    public Color bgColor;
    public Map<Integer, KeyData> intToKeyData = new HashMap<>();

    public static class KeyData {
        // Fields available in JSON and their default values
        public String text = "not set";
        public int x = 0;
        public int y = 0;
        public int width = 50;
        public int height = 50;
        public String color;
        public String pressedColor;
        public String fontColor;
        public String pressedFontColor;
        public int fontSize = 15;

        // Converted fields
        public Color upColor;
        public Color downColor;
        public Color upFontColor;
        public Color downFontColor;
    }
}
