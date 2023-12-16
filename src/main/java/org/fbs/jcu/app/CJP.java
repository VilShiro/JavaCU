package org.fbs.jcu.app;

import org.fbs.jcu.data.App;
import org.fbs.jcu.data.Key;
import org.fbs.jcu.data.Option;
import org.fbs.jcu.exception.ArgsException;

public class CJP extends App {

    public CJP(String[] args, Option[] options, Key[] keys) throws ArgsException {
        super(args, options, keys, false);
    }

    private static final Option[] options = new Option[]{
            new Option("-mainName", "-mn", String.class, "Main", "Set file name for file with main() method"){
                @Override
                public void onSetting(){
                    callArg(keys[0]);
                }
            },

    };
    private static final Key[] keys = new Key[]{
            new Key("--createMain", "--cm")
    };

    public static void main(String[] args) throws ArgsException {
        new CJP(args, options, keys);
        // CREATE: 16.12.2023: create an app for creating java project in console, has command in install.sh(preview)
    }

}
