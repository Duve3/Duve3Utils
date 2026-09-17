package me.duve3.duve3utils.mixin;

import me.duve3.duve3utils.config.ModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public class HotbarMixin {

    // Injects right when the player attempts to drop an item from the hotbar
    @Inject(method = "drop(Z)Z", at = @At("HEAD"), cancellable = true)
    private void duve3utils$onDropItem(boolean all, CallbackInfoReturnable<Boolean> cir) {
        if (ModConfig.getInstance().isHotbarProtection()) {
            LocalPlayer player = (LocalPlayer) (Object) this;

            // Ignore empty hands
            if (player.getMainHandItem().isEmpty()) return;

            Item mainHandItem = player.getMainHandItem().getItem();

            // If the item is NOT on the allow-list, cancel the drop!
            if (!ModConfig.getInstance().getBlacklistedItems().contains(mainHandItem)) {
                if (ModConfig.getInstance().isShowMessage()) {
                    player.sendOverlayMessage(
                            Component.literal("You disabled dropping items!").withStyle(ChatFormatting.RED)
                    );
                }
                // cir.setReturnValue(false) stops the method here and prevents the drop packet from sending
                cir.setReturnValue(false);
            }
        }
    }
}