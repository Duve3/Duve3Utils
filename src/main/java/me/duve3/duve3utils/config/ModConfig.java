package me.duve3.duve3utils.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ModConfig {
    // AntiDrop ----
    private boolean hotbarProtection = true;
    private boolean inventoryProtection = false;
    private boolean showMessage = true;
    private List<String> blacklistedItems = new ArrayList<>();

    // AutoDisconnect ----
    private boolean autoDisconnectEnabled = true;
    private double minHealth = 2.0;
    private double safeDuration = 10.0;

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "Duve3Utils.json");
    private static ModConfig INSTANCE = new ModConfig();

    /// getters n setters
    public double getMinHealth() {
        return minHealth;
    }

    public void setMinHealth(double minHealth) {
        this.minHealth = minHealth;

        // save();
    }

    public double getSafeDuration() {
        return safeDuration;
    }

    public void setSafeDuration(double safeDuration) {
        this.safeDuration = safeDuration;

        // save();
    }

    public void setBlacklistedItems(List<String> blacklistedItems) {
        this.blacklistedItems = blacklistedItems;
    }

    public List<Item> getBlacklistedItems() {
        return blacklistedItems.stream()
                .map(Identifier::tryParse)
                .filter(Objects::nonNull)
                .map(BuiltInRegistries.ITEM::getOptional)
                .flatMap(Optional::stream)
                .toList();
    }

    public List<String> getBlacklistedItemsRaw() {
        return blacklistedItems;
    }

    public boolean isAutoDisconnectEnabled() {
        return autoDisconnectEnabled;
    }

    public void setAutoDisconnectEnabled(boolean autoDisconnectEnabled) {
        this.autoDisconnectEnabled = autoDisconnectEnabled;

        // save();
    }

    public boolean isShowMessage() {
        return showMessage;
    }

    public void setShowMessage(boolean showMessage) {
        this.showMessage = showMessage;

        // save();
    }

    public boolean isInventoryProtection() {
        return inventoryProtection;
    }

    public void setInventoryProtection(boolean inventoryProtection) {
        this.inventoryProtection = inventoryProtection;

        // save();
    }

    public boolean isHotbarProtection() {
        return hotbarProtection;
    }

    public void setHotbarProtection(boolean hotbarProtection) {
        this.hotbarProtection = hotbarProtection;

        // save();
    }

    public static void load() {
        try {
            if (FILE.exists()) {
                INSTANCE = GSON.fromJson(new FileReader(FILE), ModConfig.class);
            }
        } catch (Exception e) {
            System.err.println("Failed to load configuration!" + e.getLocalizedMessage() + e.getCause() + e.getMessage());
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (Exception e) {
            System.err.println("Failed to save configuration! " + e.getLocalizedMessage() + e.getCause() + e.getMessage());
        }
    }

    public static ModConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModConfig();
        }
        return INSTANCE;
    }
}