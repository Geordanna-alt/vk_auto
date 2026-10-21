package com.automatization.tests;

import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream in = Config.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            PROPS.load(in);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot load config.properties", e);
        }
    }

    private Config() {}

    public static String baseUrl() {
        return PROPS.getProperty("swapi.base.url");
    }

    public static int timeoutMs() {
        return Integer.parseInt(PROPS.getProperty("swapi.timeout.ms", "10000"));
    }
    // public static void main(String[] args) {
    //     System.out.println(baseUrl());
    //     System.out.println(timeoutMs());
    // }
}