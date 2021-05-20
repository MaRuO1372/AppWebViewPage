package com.seventer.appwebviewpage.githubconfig;

public interface AppConfigProvider {
    AppConfigProvider loadConfig() throws Exception;
    boolean has(String path);
    String getString(String path);
    String getString(String path, String def);
    boolean getBoolean(String path);
    boolean getBoolean(String path, boolean def);
    int getInt(String path, int def);
}
