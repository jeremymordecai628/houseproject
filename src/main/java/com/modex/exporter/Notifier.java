package com.modex.exporter;

public class Notifier {
    public static void success(String msg) {
        Toast.show("✔ " + msg);
    }

    public static void error(String msg) {
        Toast.show("✖ " + msg);
    }
}
