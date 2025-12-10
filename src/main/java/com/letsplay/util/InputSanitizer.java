package com.letsplay.util;

import java.util.regex.Pattern;

public class InputSanitizer {
    
    private static final Pattern MONGO_INJECTION = Pattern.compile("[{}$]");
    
    public static String sanitize(String input) {
        if (input == null) return null;
        return MONGO_INJECTION.matcher(input.trim()).replaceAll("");
    }
}
