/*
package me.duve3.duve3utils.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class antiDrop {
    private final Duve3utilsClient main;
    private static KeyBinding keyBinding;

    private List<ItemStack> items = new ArrayList<ItemStack>();

    public antiDrop(Duve3utilsClient main) {
        this.main = main;

        keybind_init();
        event_init();

        this.main.LOGGER.info("[antiDrop] Initialized");
    }

    private void keybind_init() {
        keyBinding = new KeyBinding("key.Duve3Utils.toggle_drop", GLFW.GLFW_KEY_B, "category.Duve3Utils.keybinds");

        this.main.LOGGER.debug("[Chat Marcos] Binded B to Craft Chat Macro!");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                assert client.player != null;

                ItemStack activeItem = client.player.getActiveItem();

                if (activeItem != null && !items.contains(activeItem)) {
                    items.add(activeItem);

                    client.player.sendMessage(Text.of("Set Item to NO DROP list!"), true);
                } else if (activeItem != null) {
                    items.remove(activeItem);

                    client.player.sendMessage(Text.of("Set Item to **DO DROP THIS ITEM** list!"), true);
                }
            }
        });
    }

    private void event_init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            assert client.player != null;
            client.player.input.jump();
        });
    }
}
*/
