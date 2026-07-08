package me.duve3.duve3utils.antiDrop;

import me.duve3.duve3utils.Duve3utils;
import me.duve3.duve3utils.config.ModConfig;
import me.duve3.duve3utils.helpers.Keybinds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyMapping.Category;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class AntiDrop {
    private Duve3utils main;

    private KeyMapping toggleHotbarBind;
    private KeyMapping toggleInventoryBind;

    public AntiDrop(Duve3utils main, Category Category) {
        this.main = main;

        DropEventHandler.register();

        setUpToggleKeybind(Category);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (toggleHotbarBind.consumeClick()) {
                boolean newVal = !ModConfig.getInstance().hotbarProtection;
                client.player.sendOverlayMessage(Component.literal("Antidrop hotbar protection set to " + newVal));

                ModConfig.getInstance().hotbarProtection = newVal;
            }

            while (toggleInventoryBind.consumeClick()) {
                boolean newVal = !ModConfig.getInstance().inventoryProtection;
                client.player.sendOverlayMessage(Component.literal("Antidrop inventory protection set to " + newVal));

                ModConfig.getInstance().inventoryProtection = newVal;
            }
        });
    }

    private void setUpToggleKeybind(Category category) {
        toggleHotbarBind = Keybinds.DefineKeyboardKeybind("toggleAntiDropHotbar", GLFW.GLFW_KEY_LEFT_BRACKET, category);
        toggleInventoryBind = Keybinds.DefineKeyboardKeybind("toggleAntiDropInventory", GLFW.GLFW_KEY_RIGHT_BRACKET, category);
    }
}
