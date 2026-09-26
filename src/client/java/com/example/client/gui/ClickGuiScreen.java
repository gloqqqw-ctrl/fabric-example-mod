package com.example.client.gui;

import java.util.List;

import com.example.client.Module;
import com.example.client.ModuleCategory;
import com.example.client.ModuleManager;
import com.example.client.Setting;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ClickGuiScreen extends Screen {
    private ModuleCategory selectedCategory = ModuleCategory.COMBAT;
    private int scrollOffset = 0;
    private static final int MODULE_HEIGHT = 18;

    public ClickGuiScreen() {
        super(Text.literal("ZXSA Client"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        int width = this.width;
        int height = this.height;

        context.fill(12, 12, width - 12, height - 12, 0xFF101114);
        context.fill(18, 18, width - 18, 42, 0xFF1D1F26);
        context.drawText(this.textRenderer, "ZXSA Client", 28, 27, 0xFF7BE8B8, false);

        int categoryX = 28;
        int categoryY = 52;
        for (ModuleCategory category : ModuleCategory.values()) {
            boolean active = category == selectedCategory;
            int x = categoryX;
            int y = categoryY + (active ? 0 : 0);
            int w = 92;
            int h = 22;

            context.fill(x, y, x + w, y + h, active ? 0xFF2D8C73 : 0xFF22252D);
            context.drawText(this.textRenderer, category.name(), x + 8, y + 6, 0xFFFFFFFF, false);
            categoryX += 100;
        }

        List<Module> modules = ModuleManager.get().getModulesByCategory(selectedCategory);
        int listX = 24;
        int listY = 82;
        int listWidth = width - 48;
        int listHeight = height - 120;

        for (int i = scrollOffset; i < modules.size() && i < scrollOffset + 12; i++) {
            Module module = modules.get(i);
            int y = listY + (i - scrollOffset) * (MODULE_HEIGHT + 4);
            if (y + MODULE_HEIGHT > listY + listHeight) {
                break;
            }

            boolean hovered = mouseX >= listX && mouseX <= listX + listWidth && mouseY >= y && mouseY <= y + MODULE_HEIGHT;
            context.fill(listX, y, listX + listWidth, y + MODULE_HEIGHT, module.isEnabled() ? 0xFF2B5B3E : 0xFF1F2127);
            if (hovered) {
                context.fill(listX, y, listX + listWidth, y + MODULE_HEIGHT, 0x4F5B8DFF);
            }

            context.drawText(this.textRenderer, module.getName(), listX + 8, y + 5, 0xFFFFFFFF, false);
            context.drawText(this.textRenderer, module.getBindName(), listX + listWidth - 72, y + 5, 0xFFB9C2D4, false);

            int settingsX = listX + listWidth - 14;
            context.drawText(this.textRenderer, "...", settingsX - 18, y + 5, 0xFFB9C2D4, false);
        }

        int infoY = height - 32;
        context.fill(18, infoY - 10, width - 18, height - 18, 0xFF1D1F26);
        context.drawText(this.textRenderer, "Right click = bind next | Left click = toggle | Scroll = move list", 28, infoY - 2, 0xFF9FB5C8, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        List<Module> modules = ModuleManager.get().getModulesByCategory(selectedCategory);
        int listX = 24;
        int listY = 82;
        int listWidth = this.width - 48;

        for (int i = scrollOffset; i < modules.size() && i < scrollOffset + 12; i++) {
            Module module = modules.get(i);
            int y = listY + (i - scrollOffset) * (MODULE_HEIGHT + 4);

            if (mouseX >= listX && mouseX <= listX + listWidth && mouseY >= y && mouseY <= y + MODULE_HEIGHT) {
                if (button == 0) {
                    module.toggle();
                    return true;
                }

                if (button == 1) {
                    int nextKey = getNextKey(module.getBindKey());
                    module.setBindKey(nextKey);
                    return true;
                }
            }
        }

        int categoryX = 28;
        int categoryY = 52;
        for (ModuleCategory category : ModuleCategory.values()) {
            int x = categoryX;
            int w = 92;
            if (mouseX >= x && mouseX <= x + w && mouseY >= categoryY && mouseY <= categoryY + 22) {
                selectedCategory = category;
                scrollOffset = 0;
                return true;
            }
            categoryX += 100;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        int maxOffset = Math.max(0, ModuleManager.get().getModulesByCategory(selectedCategory).size() - 12);
        scrollOffset = (int) Math.max(0, Math.min(maxOffset, scrollOffset - verticalAmount));
        return true;
    }

    private int getNextKey(int currentKey) {
        int[] keys = {0, org.lwjgl.glfw.GLFW.GLFW_KEY_G, org.lwjgl.glfw.GLFW.GLFW_KEY_H, org.lwjgl.glfw.GLFW.GLFW_KEY_J, org.lwjgl.glfw.GLFW.GLFW_KEY_K, org.lwjgl.glfw.GLFW.GLFW_KEY_M, org.lwjgl.glfw.GLFW.GLFW_KEY_X, org.lwjgl.glfw.GLFW.GLFW_KEY_C, org.lwjgl.glfw.GLFW.GLFW_KEY_V};
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == currentKey) {
                return keys[(i + 1) % keys.length];
            }
        }
        return keys[1];
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
