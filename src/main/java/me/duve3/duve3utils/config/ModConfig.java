package me.duve3.duve3utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.Item;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ModConfig {
    // AntiDrop ----
    public boolean hotbarProtection = true;
    public boolean inventoryProtection = false;
    public boolean showMessage = true;
    public List<Item> blacklistedItems = new ArrayList<>();

    // AutoDisconnect ----
    public boolean autoDisconnectEnabled = true;
    public double minHealth = 2.0;
    public double safeDuration = 10.0;


    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "Duve3Utils.json");
    private static ModConfig INSTANCE = new ModConfig();

    public static void load() {
        try {
            if (FILE.exists()) {
                INSTANCE = GSON.fromJson(new FileReader(FILE), ModConfig.class);
            }
        } catch (Exception e) {
            System.err.println("Failed to load configuration!");
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (Exception e) {
            System.err.println("Failed to save configuration!");
        }
    }

    public static ModConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModConfig();
        }
        return INSTANCE;
    }
}