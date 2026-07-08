package me.duve3.duve3utils;

import me.duve3.duve3utils.helpers.Keybinds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyMapping.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.jspecify.annotations.NonNull;
import org.lwjgl.glfw.GLFW;

public class NightVision {
    private final Duve3utils main;
    private static KeyMapping keyBinding;
    private static Category category;
    private boolean enabled = false;

    public NightVision(Duve3utils main, Category assignedCat) {
        this.main = main;

        category = assignedCat;

        enableNightVision();

        Duve3utils.LOGGER.info("[Night Vision] Initialized!");
    }

    private void applyNightVision(@NonNull Minecraft client) {
        if (client.player == null) { return; }
        client.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, MobEffectInstance.INFINITE_DURATION, MobEffectInstance.MAX_AMPLIFIER, false, false, false));
    }

    private void enableNightVision() {
        keyBinding = Keybinds.DefineKeyboardKeybind("toggleNightVision", GLFW.GLFW_KEY_G, category);

        Duve3utils.LOGGER.debug("[Night Vision] bound G to NightVision Toggle");

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (keyBinding.consumeClick()) {
                enabled = !enabled;

                if (enabled) {
                    applyNightVision(client);
                    client.player.sendOverlayMessage(Component.literal("Night Vision Enabled!"));
                } else {
                    client.player.removeEffect(MobEffects.NIGHT_VISION);
                    client.player.sendOverlayMessage(Component.literal("Night Vision Disabled!"));
                }
            }

            // re-apply upon death or similar
            if (!client.player.hasEffect(MobEffects.NIGHT_VISION) && enabled) {
                applyNightVision(client);
            }
        });
    }
}
