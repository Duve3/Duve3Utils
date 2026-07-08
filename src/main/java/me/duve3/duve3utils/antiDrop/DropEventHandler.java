package me.duve3.duve3utils.antiDrop;

import me.duve3.duve3utils.config.ModConfig;
import me.duve3.duve3utils.mixin.AbstractContainerScreenAccessor;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;

public class DropEventHandler {
    public static void register() {

        ScreenEvents.BEFORE_INIT.register((client, screen, _, _) -> ScreenKeyboardEvents.allowKeyPress(screen).register((scr, event) -> {

            if (ModConfig.getInstance().inventoryProtection) {

                if (client.options.keyDrop.matches(event)) {
                    if (scr instanceof AbstractContainerScreen<?> containerScreen) {

                        Slot hoveredSlot = ((AbstractContainerScreenAccessor) containerScreen).duve3utils$getHoveredSlot();

                        if (hoveredSlot != null && hoveredSlot.hasItem()) {
                            Item hoveredItem = hoveredSlot.getItem().getItem();

                            // If the item IS on the allow-list, let vanilla handle it
                            if (ModConfig.getInstance().blacklistedItems.contains(hoveredItem)) {
                                return true;
                            }
                        }
                    }

                    // Block the drop if they try to drop an item not on the list
                    if (client.player != null && ModConfig.getInstance().showMessage) {
                        client.player.sendOverlayMessage(
                                Component.literal("You disabled dropping items!").withStyle(ChatFormatting.RED)
                        );
                    }
                    return false;
                }
            }
            return true;
        }));
    }
}