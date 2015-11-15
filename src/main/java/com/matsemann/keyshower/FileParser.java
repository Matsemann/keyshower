package com.matsemann.keyshower;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matsemann.keyshower.KeyboardFile.KeyData;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.Map;

public class FileParser {

    public static KeyboardFile parse(File file, Settings settings) {
        KeyboardFile kbf;

        try {
            String json = new String(Files.readAllBytes(file.toPath()));
            json = json.replaceAll(",\\s*}", "}"); // allow trailing commas

            ObjectMapper om = new ObjectMapper();
            om.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
            om.configure(Feature.ALLOW_COMMENTS, true);

            kbf = om.readValue(json, KeyboardFile.class);
        } catch (JsonParseException | JsonMappingException e) {
            throw new RuntimeException("Invalid keyboard file/JSON. " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read the file. " + e.getMessage());
        }

        validateKeyboardFile(kbf, settings);
        for (Map.Entry<String, KeyData> keyData : kbf.keys.entrySet()) {
            validateKey(keyData.getValue(), kbf, settings);
            Integer nativeKey = getNativeKey(keyData.getKey());

            kbf.intToKeyData.put(nativeKey, keyData.getValue());
        }

        return kbf;
    }

    private static Integer getNativeKey(String key) {
        try {
            Field field = NativeKeyEvent.class.getField("VC_" + key);
            return field.getInt(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("No key named " + key);
        }
    }

    private static void validateKeyboardFile(KeyboardFile kbf, Settings settings) {
        if (!KeyboardFile.CURRENT_VERSION.equals(kbf.version)) {
            throw new RuntimeException("Expected version " + KeyboardFile.CURRENT_VERSION + " of keyboard file");
        }
        kbf.bgColor = convertColor(kbf.backgroundColor, settings.bgAlpha);
    }

    private static void validateKey(KeyData key, KeyboardFile kbf, Settings settings) {
        if (key.color == null) {
            key.color = kbf.defaultColor;
        }

        if (key.pressedColor == null) {
            key.pressedColor = kbf.defaultPressedColor;
        }

        key.upColor = convertColor(key.color, settings.keyAlpha);
        key.downColor = convertColor(key.pressedColor, settings.keyAlpha);
    }

    private static Color convertColor(String color, int alpha) {
        if (color.length() != 7) {
            throw new IllegalArgumentException("Didn't understand color: " + color);
        }

        try {
            int r = Integer.valueOf(color.substring(1, 3), 16);
            int g = Integer.valueOf(color.substring(3, 5), 16);
            int b = Integer.valueOf(color.substring(5, 7), 16);
            return new Color(r, g, b, alpha);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Didn't understand color: " + color);
        }
    }
}
