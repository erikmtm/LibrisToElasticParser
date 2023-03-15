/*
 * Copyright (c) 2023. Myndigheten för tillgängliga medier (MTM)
 */
package se.mtm.LibrisToElasticParser.libris.util;

import java.util.regex.Pattern;

public class Isbn {

    private final String ISBN_PATTERN = "(?=.*\\b9[0-9][0-9]-[0-9][0-9]\\b)([ \\t]*9[0-9][0-9]-[0-9][0-9][ \\t]*([^\\ ]*))|(?=.*\\b9[0-9][0-9]-[0-9]\\b)([ \\t]*9[0-9][0-9]-[0-9][ \\t]*([^\\ ][^\\n]*))|(?=.*\\b9[0-9][0-9]-[0-9][0-9][0-9]\\b)([ \\t]*9[0-9][0-9]-[0-9][0-9][0-9][ \\t]*([^\\ ]*))|((?:\\D|^)\\d{13})(\\D|$)";

    public String extractFrom(String rawText) {

        String matchedString = null;

        Pattern pattern = Pattern.compile(ISBN_PATTERN);
        var matcher = pattern.matcher(rawText);

        var found = matcher.find();

        if (found) {
            matchedString = matcher.group().replaceAll("[^0-9]", "");
        }

        return matchedString;
    }

}

