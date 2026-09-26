package com.example.client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;

public class ModuleManager {
    private static final ModuleManager INSTANCE = new ModuleManager();

    private final List<Module> modules = new ArrayList<>();

    private ModuleManager() {
        register(new Module("Aim Assist", ModuleCategory.COMBAT));
        register(new Module("Killaura", ModuleCategory.COMBAT));
        register(new Module("Auto Clicker", ModuleCategory.COMBAT));
        register(new Module("Speed", ModuleCategory.MOVEMENT));
        register(new Module("Flight", ModuleCategory.MOVEMENT));
        register(new Module("High Jump", ModuleCategory.MOVEMENT));
        register(new Module("TP", ModuleCategory.PLAYER));
        register(new Module("No Fall", ModuleCategory.PLAYER));
        register(new Module("ESP", ModuleCategory.VISUALS));
        register(new Module("Fullbright", ModuleCategory.VISUALS));
        register(new Module("Xray", ModuleCategory.WORLD));
        register(new Module("Auto Mine", ModuleCategory.WORLD));

        Module aimAssist = getByName("Aim Assist");
        if (aimAssist != null) {
            aimAssist.addSetting(new Setting("Range", 4.0, 2.0, 12.0, 0.5));
            aimAssist.addSetting(new Setting("AimRange", 3.2, 1.0, 8.0, 0.1));
            aimAssist.addSetting(new Setting("Sensitivity", 0.8, 0.1, 2.0, 0.05));
            aimAssist.addSetting(new Setting("Precision", 0.7, 0.0, 1.0, 0.05));
            aimAssist.addSetting(new Setting("Speed", 1.0, 0.1, 3.0, 0.1));
        }

        Module speed = getByName("Speed");
        if (speed != null) {
            speed.addSetting(new Setting("Speed", 1.3, 0.5, 3.0, 0.1));
            speed.addSetting(new Setting("Sprint", 1.0, 0.0, 1.0, 0.1));
        }

        Module tp = getByName("TP");
        if (tp != null) {
            tp.addSetting(new Setting("Distance", 6.0, 2.0, 20.0, 1.0));
            tp.addSetting(new Setting("Smoothness", 0.5, 0.1, 2.0, 0.1));
        }
    }

    public static ModuleManager get() {
        return INSTANCE;
    }

    public void register(Module module) {
        modules.add(module);
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getModulesByCategory(ModuleCategory category) {
        List<Module> result = new ArrayList<>();
        for (Module module : modules) {
            if (module.getCategory() == category) {
                result.add(module);
            }
        }
        return result;
    }

    public Module getByName(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public void handleKeybinds() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.getWindow() == null) {
            return;
        }

        long handle = client.getWindow().getHandle();
        for (Module module : modules) {
            if (module.getBindKey() == 0) {
                continue;
            }

            boolean pressed = InputUtil.isKeyPressed(handle, module.getBindKey());
            if (pressed && !module.isKeyHeld()) {
                module.toggle();
                module.setKeyHeld(true);
            } else if (!pressed) {
                module.setKeyHeld(false);
            }
        }
    }
}
