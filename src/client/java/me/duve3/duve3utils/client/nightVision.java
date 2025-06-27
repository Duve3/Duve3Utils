package me.duve3.duve3utils.client;

import me.duve3.duve3utils.client.keybinds.Keybinds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class nightVision {
    private final Duve3utilsClient main;
    private static KeyBinding keyBinding;
    private boolean enabled = false;

    public nightVision(Duve3utilsClient main) {
        this.main = main;

        enableNightVision();

        this.main.LOGGER.info("[Night Vision] Initialized!");
    }

    private void enableNightVision() {
        keyBinding = Keybinds.DefineKeyboardKeybind("key.Duve3Utils.toggle_nightVision", GLFW.GLFW_KEY_G, "category.Duve3Utils.keybinds");

        this.main.LOGGER.debug("[Night Vision] binded G to NightVision Toggle");


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                assert client.player != null;

                enabled = !enabled;

                if (enabled) {
                    client.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, StatusEffectInstance.INFINITE, StatusEffectInstance.MAX_AMPLIFIER, false, false, false));
                    client.player.sendMessage(Text.of("Night Vision Enabled!"), true);

                } else {
                    client.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
                    client.player.sendMessage(Text.of("Night Vision Disabled!"), true);
                }
            }
        });
    }
}
