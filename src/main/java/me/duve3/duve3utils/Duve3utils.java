package me.duve3.duve3utils;

import me.duve3.duve3utils.antiDrop.AntiDrop;
import me.duve3.duve3utils.autoDisconnect.AutoDisconnect;
import me.duve3.duve3utils.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping.Category;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public class Duve3utils implements ClientModInitializer {
    public static final String MOD_ID = "duve3utils";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    //private final Display display = new Display(this);

    @Override
    public void onInitializeClient() {
        ModConfig.load();

        LOGGER.info("Initializing CLIENT SIDE Duve3utils!");

        Category UtilCategory = Category.register(Identifier.fromNamespaceAndPath(Duve3utils.MOD_ID, "utility"));

        //new chatMacros(this); -- rest in peace <33333, use: use: https://modrinth.com/mod/chat-tools instead
        //new perfectElytra(this, UtilCategory); -- rest in peace perfectElytra <3333333, use: https://modrinth.com/mod/elytrapitch instead

        new AutoDisconnect(this);
        new AntiDrop(this, UtilCategory);
        new NightVision(this, UtilCategory); // prolly a mod for this too, but i like my implementation!!
    }
}
