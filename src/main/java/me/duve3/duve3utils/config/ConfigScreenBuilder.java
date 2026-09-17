package me.duve3.duve3utils.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.StringListListEntry.StringListCell;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import java.util.List;
import java.util.Optional;

public class ConfigScreenBuilder {

    public static Screen build(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Duve3utils configuration"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory antidrop = builder.getOrCreateCategory(Component.literal("Antidrop"));

        antidrop.addEntry(entryBuilder.startBooleanToggle(
                        Component.literal("Hotbar drop protection"),
                        ModConfig.getInstance().isHotbarProtection()
                )
                .setDefaultValue(true)
                .setTooltip(Component.literal("Whether or not to prevent drops from hotbar"))
                .setSaveConsumer(ModConfig.getInstance()::setHotbarProtection)
                .build());

        antidrop.addEntry(entryBuilder.startBooleanToggle(
                        Component.literal("Inventory drop protection"),
                        ModConfig.getInstance().isInventoryProtection()
                )
                .setDefaultValue(false)
                .setTooltip(Component.literal("Whether or not to prevent drops from inventory"))
                .setSaveConsumer(ModConfig.getInstance()::setInventoryProtection)
                .build());

        antidrop.addEntry(entryBuilder.startBooleanToggle(
                        Component.literal("Show actionbar message"),
                        ModConfig.getInstance().isShowMessage()
                )
                .setDefaultValue(true)
                .setTooltip(Component.literal("Changes whether or not to show messages on a cancelled drop"))
                .setSaveConsumer(ModConfig.getInstance()::setShowMessage)
                .build());

        List<String> itemIdsList = ModConfig.getInstance().getBlacklistedItemsRaw();


        antidrop.addEntry(entryBuilder.startStrList(
                        Component.literal("Allowed Minecraft Items"),
                        itemIdsList
                )
                .setTooltip(Component.literal("Allowed Minecraft Items to be thrown out"))
                .setDefaultValue(List.of("minecraft:ender_pearl"))
                .setCreateNewInstance(entry -> new StringListCell("minecraft:air", entry))
                .setExpanded(true)

                // Optional: Validate that the entered string is actually a valid Minecraft item ID
                .setErrorSupplier(currentList -> {
                    for (String currentValue : currentList) {
                        // skip empty strings
                        if (currentValue.isEmpty()) {
                            continue;
                        }
                        Identifier id = Identifier.tryParse(currentValue);

                        if (id == null || !BuiltInRegistries.ITEM.containsKey(id)) {
                            return Optional.of(Component.literal("Invalid Minecraft Item ID!"));
                        }
                    }
                    return Optional.empty();
                })

                // Unwrap and extract actual Item instances into your active list
                .setSaveConsumer(newStrings -> {
                    ModConfig.getInstance().setBlacklistedItems(newStrings);
                })
                .build());

        ConfigCategory autoDisconnect = builder.getOrCreateCategory(Component.literal("AutoDisconnect"));

        autoDisconnect.addEntry(
                entryBuilder.startBooleanToggle(
                                Component.literal("Enable AutoDisconnect?"),
                                ModConfig.getInstance().isAutoDisconnectEnabled()
                        )
                        .setDefaultValue(true)
                        .setTooltip(Component.literal("Whether or not to enable AutoDisconnect"))
                        .setSaveConsumer(ModConfig.getInstance()::setAutoDisconnectEnabled)
                        .build()
        );

        autoDisconnect.addEntry(
                entryBuilder.startDoubleField(
                                Component.literal("Lowest minimum health"),
                                ModConfig.getInstance().getMinHealth()
                        )
                        .setDefaultValue(2.0)
                        .setTooltip(Component.literal("The lowest health to be at before automatically disconnecting"))
                        .setSaveConsumer(newValue -> ModConfig.getInstance().setMinHealth(newValue))
                        .build()
        );

        autoDisconnect.addEntry(
                entryBuilder.startDoubleField(
                                Component.literal("Safe time on join"),
                                ModConfig.getInstance().getSafeDuration()
                        )
                        .setDefaultValue(10.0)
                        .setTooltip(Component.literal("How long to wait before allowing autoDisconnect due to any other reason"))
                        .setSaveConsumer(ModConfig.getInstance()::setSafeDuration)
                        .build()
        );

        builder.setSavingRunnable(ModConfig::save);

        return builder.build();
    }
}