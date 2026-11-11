package com.mycompany.academia;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationManager {

    private static Locale currentLocale = Locale.getDefault();

    public static String getString(String baseName, String key) {
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, currentLocale);
        return bundle.getString(key);
    }

    public static String getFormattedString(String baseName, String key, Object... args) {
        ResourceBundle bundle = ResourceBundle.getBundle(baseName, currentLocale);
        String pattern = bundle.getString(key);
        return MessageFormat.format(pattern, args);
    }

    public static void setLocale(Locale newLocale) {
        currentLocale = newLocale;
        Locale.setDefault(newLocale);
    }

}
