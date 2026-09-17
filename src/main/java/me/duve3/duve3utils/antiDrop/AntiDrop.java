package me.duve3.duve3utils.antiDrop;

import me.duve3.duve3utils.Duve3utils;
import me.duve3.duve3utils.config.ModConfig;
import me.duve3.duve3utils.helpers.Keybinds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyMapping.Category;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class AntiDrop {
    private Duve3utils main;

    private KeyMapping toggleHotbarBind;
    private KeyMapping toggleInventoryBind;
    private KeyMapping toggleBannedItemBind;

    public AntiDrop(Duve3utils main, Category Category) {
        this.main = main;

        DropEventHandler.register();

        setUpToggleKeybind(Category);

        toggleBannedItemBind = Keybinds.DefineKeyboardKeybind("toggleHeldItemAsWhitelisted", GLFW.GLFW_KEY_Y, Category);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (toggleHotbarBind.consumeClick()) {
                boolean newVal = !ModConfig.getInstance().isHotbarProtection();
                client.player.sendOverlayMessage(Component.literal("Antidrop hotbar protection set to " + newVal));

                ModConfig.getInstance().setHotbarProtection(newVal);
            }

            while (toggleInventoryBind.consumeClick()) {
                boolean newVal = !ModConfig.getInstance().isInventoryProtection();
                client.player.sendOverlayMessage(Component.literal("Antidrop inventory protection set to " + newVal));

                ModConfig.getInstance().setInventoryProtection(newVal);
            }

            while (toggleBannedItemBind.consumeClick()) {
                Item heldItem = client.player.getMainHandItem().getItem();
                ModConfig config = ModConfig.getInstance();

                // Get the string identifier of the item.
                // Since you are using BuiltInRegistries (Mojang mappings), we use getKey()
                String itemId = BuiltInRegistries.ITEM.getKey(heldItem).toString();

                // Get the raw list of strings from your config
                List<String> rawBlacklist = config.getBlacklistedItemsRaw();

                // Toggle logic: add if missing, remove if present
                if (rawBlacklist.contains(itemId)) {
                    // Remove the item from the blacklist
                    rawBlacklist.remove(itemId);

                    client.player.sendOverlayMessage(Component.literal("Removed " + itemId + " from blacklist"));
                } else {
                    // Add the item to the blacklist
                    rawBlacklist.add(itemId);

                    client.player.sendOverlayMessage(Component.literal("Added " + itemId + " to blacklist"));
                }

                // Save the config to write the changes to Duve3Utils.json
                ModConfig.save();
            }
        });
    }

    private void setUpToggleKeybind(Category category) {
        toggleHotbarBind = Keybinds.DefineKeyboardKeybind("toggleAntiDropHotbar", GLFW.GLFW_KEY_LEFT_BRACKET, category);
        toggleInventoryBind = Keybinds.DefineKeyboardKeybind("toggleAntiDropInventory", GLFW.GLFW_KEY_RIGHT_BRACKET, category);
    }
}
