package com.example.client;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private final String name;
    private final ModuleCategory category;
    private boolean enabled;
    private int bindKey;
    private boolean keyHeld;
    private List<Setting> settings = new ArrayList<>();

    public Module(String name, ModuleCategory category) {
        this.name = name;
        this.category = category;
        this.bindKey = 0;
    }

    public void toggle() {
        enabled = !enabled;
    }

    public void addSetting(Setting setting) {
        settings.add(setting);
    }

    public String getName() {
        return name;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getBindKey() {
        return bindKey;
    }

    public void setBindKey(int bindKey) {
        this.bindKey = bindKey;
    }

    public boolean isKeyHeld() {
        return keyHeld;
    }

    public void setKeyHeld(boolean keyHeld) {
        this.keyHeld = keyHeld;
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public String getBindName() {
        if (bindKey == 0) {
            return "NONE";
        }

        String name = org.lwjgl.glfw.GLFW.glfwGetKeyName(bindKey, 0);
        return name == null ? "KEY_" + bindKey : name.toUpperCase();
    }
}
