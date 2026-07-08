package me.duve3.duve3utils.mixin;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractContainerScreen.class)
public interface AbstractContainerScreenAccessor {
    // Fetches the protected 'hoveredSlot' field from AbstractContainerScreen
    @Accessor("hoveredSlot")
    Slot duve3utils$getHoveredSlot();
}