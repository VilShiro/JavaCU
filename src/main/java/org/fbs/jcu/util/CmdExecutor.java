package org.fbs.jcu.util;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CmdExecutor {

    private CmdExecutor(){}

    public static @NotNull List<String> execute(String command) throws IOException, InterruptedException {
        final Process p = Runtime.getRuntime().exec(command);
        List<String> strings = new ArrayList<>();
        BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;

        try {
            while ((line = input.readLine()) != null)
                strings.add(line);
        }

        catch (IOException e) {
            e.printStackTrace();
        }

        p.waitFor();
        Collections.sort(strings);

        return strings;
    }

}
