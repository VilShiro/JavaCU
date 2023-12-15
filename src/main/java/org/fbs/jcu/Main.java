package org.fbs.jcu;

import static org.kohsuke.args4j.ExampleMode.ALL;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.BooleanOptionHandler;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws CmdLineException {
        new Main().doMain(args);
    }
    public void doMain(String[] args) throws CmdLineException {
        throw new CmdLineException(new CmdLineParser(this), "dd");
    }
}