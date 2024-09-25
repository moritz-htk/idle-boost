package net.moritz_htk.idle_boost.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

/**
 * Screen for configuring the Idle Boost mod.
 */
public class IBConfigScreen extends OptionsSubScreen {

    /**
     * Constructs a new IBConfigScreen.
     *
     * @param screen the parent screen
     */
    public IBConfigScreen(Screen screen) {
        super(screen, Minecraft.getInstance().options, Component.translatable("options.idle_boost.title"));
    }

    /**
     * Adds the configuration options to the screen.
     */
    @Override
    protected void init() {
        OptionsList list = this.addRenderableWidget(new OptionsList(this.minecraft, this.width, this.height, this));

        list.addBig(OptionInstance.createBoolean(
                I18n.get("options.idle_boost.modToggle"),
                OptionInstance.cachedConstantTooltip(Component.translatable("options.idle_boost.modToggle.tooltip")),
                IBConfigManager.getConfig().modToggle,
                aBoolean -> IBConfigManager.getConfig().modToggle = !IBConfigManager.getConfig().modToggle
        ));

        list.addSmall(
                OptionInstance.createBoolean(
                        I18n.get("options.framerateLimit"),
                        OptionInstance.cachedConstantTooltip(Component.translatable("options.idle_boost.framerateLimitToggle.tooltip")),
                        IBConfigManager.getConfig().framerateLimitToggle,
                        aBoolean -> IBConfigManager.getConfig().framerateLimitToggle = !IBConfigManager.getConfig().framerateLimitToggle
                ),
                new OptionInstance<>(
                        I18n.get("options.framerateLimit"),
                        OptionInstance.cachedConstantTooltip(Component.translatable("options.idle_boost.framerateLimit.tooltip")),
                        (component, integer) -> Options.genericValueLabel(component, Component.literal(I18n.get("options.framerate", integer))),
                        new OptionInstance.IntRange(10, 120),
                        IBConfigManager.getConfig().framerateLimit,
                        integer -> IBConfigManager.getConfig().framerateLimit = integer
                )
        );

        list.addSmall(
                OptionInstance.createBoolean(
                        I18n.get("options.renderDistance"),
                        OptionInstance.cachedConstantTooltip(Component.translatable("options.idle_boost.renderDistanceToggle.tooltip")),
                        IBConfigManager.getConfig().renderDistanceToggle,
                        aBoolean -> IBConfigManager.getConfig().renderDistanceToggle = !IBConfigManager.getConfig().renderDistanceToggle
                ),
                new OptionInstance<>(
                        I18n.get("options.renderDistance"),
                        OptionInstance.cachedConstantTooltip(Component.translatable("options.idle_boost.renderDistance.tooltip")),
                        (component, integer) -> Options.genericValueLabel(component, Component.literal(I18n.get("options.chunks", integer))),
                        new OptionInstance.IntRange(2, 64),
                        IBConfigManager.getConfig().renderDistance,
                        integer -> IBConfigManager.getConfig().renderDistance = integer
                )
        );

        list.addBig(OptionInstance.createBoolean(
                I18n.get("options.idle_boost.debugModeToggle"),
                OptionInstance.cachedConstantTooltip(Component.translatable("options.idle_boost.debugModeToggle.tooltip")),
                IBConfigManager.getConfig().debugModeToggle,
                aBoolean -> IBConfigManager.getConfig().debugModeToggle = !IBConfigManager.getConfig().debugModeToggle
        ));

        super.init();
    }

    /**
     * Called when the screen is removed, saving the configuration.
     */
    @Override
    public void removed() {
        IBConfigManager.saveConfig();
    }
}