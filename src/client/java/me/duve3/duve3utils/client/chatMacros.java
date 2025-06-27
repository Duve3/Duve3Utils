package me.duve3.duve3utils.client;

import me.duve3.duve3utils.client.keybinds.Keybinds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class chatMacros {
    private final Duve3utilsClient main;
    private static KeyBinding keyBinding;

    public chatMacros(Duve3utilsClient main) {
        this.main = main;

        enableChatMacros();

        this.main.LOGGER.info("[Chat Macros] Initialized!");
    }

    private void enableChatMacros() {
        keyBinding = Keybinds.DefineKeyboardKeybind("key.Duve3Utils.craft_macro", GLFW.GLFW_KEY_V, "category.Duve3Utils.keybinds");

        this.main.LOGGER.debug("[Chat Marcos] Binded V to Craft Chat Macro!");


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                assert client.player != null;

                client.player.networkHandler.sendChatCommand("craft");
            }
        });
    }

}
