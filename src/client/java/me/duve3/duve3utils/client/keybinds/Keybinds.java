package me.duve3.duve3utils.client.keybinds;

import me.duve3.duve3utils.client.Duve3utilsClient;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class Keybinds {
    public static KeyBinding DefineKeyboardKeybind(String translationKey, int key, String category) {
        Duve3utilsClient.LOGGER.info("Define Keybind: {}", translationKey);
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                translationKey, // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                key, // The keycode of the key
                category // The translation key of the keybinding's category.
        ));
    }

    public static KeyBinding DefineMouseKeybind(String translationKey, int key, String category) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                translationKey, // The translation key of the keybinding's name
                InputUtil.Type.MOUSE, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                key, // The keycode of the key
                category // The translation key of the keybinding's category.
        ));
    }
}
