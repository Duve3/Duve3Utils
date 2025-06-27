package me.duve3.duve3utils.client;

import me.duve3.duve3utils.client.keybinds.Keybinds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class perfectElytra {
    private final Duve3utilsClient main;
    private static KeyBinding keyBinding;

    public perfectElytra(Duve3utilsClient main) {
        this.main = main;
        perfectGlideDegrees();

        this.main.LOGGER.info("[Perfect Elytra] Initialized!");
    }

    private void perfectGlideDegrees() {
        keyBinding = Keybinds.DefineKeyboardKeybind("key.Duve3Utils.position_elytra", GLFW.GLFW_KEY_SEMICOLON, "category.Duve3Utils.keybinds");

        this.main.LOGGER.debug("[Perfect Elytra] binded SEMICOLON to pitch -2.2 degrees");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                assert client.player != null;
                client.player.setPitch(-2.20f);
                client.player.sendMessage(Text.of("Set to -2.2 degrees (most optimal long distance angle!)"), true);
            }
        });
    }
}
