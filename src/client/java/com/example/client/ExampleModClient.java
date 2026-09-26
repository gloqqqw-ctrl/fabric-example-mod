package com.example.client;

import org.lwjgl.glfw.GLFW;

import com.example.client.gui.ClickGuiScreen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class ExampleModClient implements ClientModInitializer {
    public static final KeyBinding OPEN_GUI = KeyBindingHelper.registerKeyBinding(
            new KeyBinding("key.zxsaclient.open_gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "key.category.zxsaclient")
    );

    @Override
    public void onInitializeClient() {
        ModuleManager.get();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            ModuleManager.get().handleKeybinds();

            while (OPEN_GUI.wasPressed()) {
                MinecraftClient.getInstance().setScreen(new ClickGuiScreen());
            }
        });
    }
}
