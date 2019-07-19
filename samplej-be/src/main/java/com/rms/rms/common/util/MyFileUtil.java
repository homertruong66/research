package com.rms.rms.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * homertruong
 */

public final class MyFileUtil {

    public static String readText(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF8"));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        // close input stream
        try {
            reader.close();
            in.close();
        }
        catch (Exception ex) { }

        return stringBuilder.toString();
    }

}
