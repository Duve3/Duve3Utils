package me.duve3.duve3utils.autoDisconnect;

import me.duve3.duve3utils.Duve3utils;
import me.duve3.duve3utils.config.ModConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.network.chat.Component;

public class AutoDisconnect {
    private Duve3utils main;

    private long joinedAt = -1;

    private long getCurrentTime() {
        return java.time.Instant.now().getEpochSecond();
    }

    public AutoDisconnect(Duve3utils main) {
        this.main = main;

        ClientPlayConnectionEvents.JOIN.register((_, _, _) -> joinedAt = getCurrentTime());

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            if (!ModConfig.getInstance().autoDisconnectEnabled) return;

            if (getCurrentTime() - joinedAt < ModConfig.getInstance().safeDuration) return;

            if (client.player.getHealth() <= ModConfig.getInstance().minHealth) {
                client.player.sendOverlayMessage(Component.literal("[AutoDisconnect] Disconnecting due to health!"));
                client.disconnectFromWorld(Component.literal("[AutoDisconnect] Automatically disconnected due to health!"));
            }
        });
    }
}
