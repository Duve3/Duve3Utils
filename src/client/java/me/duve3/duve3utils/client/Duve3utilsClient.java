package me.duve3.duve3utils.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Duve3utilsClient implements ClientModInitializer {
    public static final String MOD_ID = "duve3utils";

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    //private final Display display = new Display(this);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing CLIENT SIDE Duve3utils!");

        //display.onInitializeClient();

        //new chatMacros(this);
        //new antiDrop(this);
        new perfectElytra(this);
        new nightVision(this);
    }
}
