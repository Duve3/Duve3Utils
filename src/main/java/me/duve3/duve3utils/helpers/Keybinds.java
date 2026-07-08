package me.duve3.duve3utils.helpers;

import com.mojang.blaze3d.platform.InputConstants;
import me.duve3.duve3utils.Duve3utils;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyMapping.Category;

public class Keybinds {
    public static KeyMapping DefineKeyboardKeybind(String translationKey, int key, Category category) {
        Duve3utils.LOGGER.debug("Define Keybind: {}", translationKey);
        return KeyMappingHelper.registerKeyMapping(new KeyMapping(
                translationKey,
                InputConstants.Type.KEYSYM,
                key,
                category
        ));
    }

    public static KeyMapping DefineMouseKeybind(String translationKey, int key, Category category) {
        Duve3utils.LOGGER.debug("Define Mousebind: {}, {}, {}", translationKey, key, category);
        return KeyMappingHelper.registerKeyMapping(new KeyMapping(
                translationKey,
                InputConstants.Type.MOUSE,
                key,
                category
        ));
    }
}
