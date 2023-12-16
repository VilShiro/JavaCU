package org.fbs.jcu;

import org.fbs.jcu.data.Key;
import org.fbs.jcu.data.Option;
import org.fbs.jcu.exception.ArgsException;
import org.fbs.jcu.util.ArgsParser;

public class Main {

    public static void main(String[] args) throws ArgsException {
        new ArgsParser(args, new Option[6], new Key[6], false);
        System.out.println(789);
    }
}