package org.fbs.jcu.app;

import org.fbs.jcu.data.App;
import org.fbs.jcu.data.AppArguments;
import org.fbs.jcu.exception.ArgsException;

public class Installer extends App {
    public Installer(String[] args, AppArguments appArguments, boolean mustContainsArgs) throws ArgsException {
        super(args, appArguments, mustContainsArgs, "installer", "org.fbs.jcu.app.Installer");
    }

    public static void main(String[] args) {

    }

}
