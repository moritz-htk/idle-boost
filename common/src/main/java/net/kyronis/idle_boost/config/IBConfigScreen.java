/*
 * Copyright (C) 2026 Kyronis
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as published
 * by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.kyronis.idle_boost.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Screen for configuring the Idle Boost mod.
 */
public class IBConfigScreen extends OptionsSubScreen {
    private OptionsList list;

    public IBConfigScreen(Screen screen) {
        super(screen, Minecraft.getInstance().options, Component.translatable("options.idle_boost.title"));
    }

    @Override
    protected void init() {
        this.list = this.addRenderableWidget(new OptionsList(this.minecraft, this.width, this.height, this));
        this.addOptions();
        super.init();
    }

    private void addOptions() {
        IBConfig config = IBConfigManager.getConfig();

        this.list.addBig(OptionInstance.createBoolean(
                "options.idle_boost.modToggle",
                OptionInstance.cachedConstantTooltip(Component.translatable("options.idle_boost.modToggle.tooltip")),
                config.modToggle,
                enabled -> config.modToggle = enabled
        ));

        this.list.addSmall(
                OptionInstance.createBoolean(
                        "options.framerateLimit",
                        OptionInstance.cachedConstantTooltip(Component.translatable("options.idle_boost.framerateLimitToggle.tooltip")),
                        config.framerateLimitToggle,
                        enabled -> config.framerateLimitToggle = enabled
                ),
                new OptionInstance<>(
                        "options.framerateLimit",
                        OptionInstance.cachedConstantTooltip(Component.translatable("options.idle_boost.framerateLimit.tooltip")),
                        (label, value) -> Options.genericValueLabel(label, Component.translatable("options.framerate", value)),
                        new OptionInstance.IntRange(10, 120),
                        config.framerateLimit,
                        value -> config.framerateLimit = value
                )
        );

        this.list.addSmall(
                OptionInstance.createBoolean(
                        "options.renderDistance",
                        OptionInstance.cachedConstantTooltip(Component.translatable("options.idle_boost.renderDistanceToggle.tooltip")),
                        config.renderDistanceToggle,
                        enabled -> config.renderDistanceToggle = enabled
                ),
                new OptionInstance<>(
                        "options.renderDistance",
                        OptionInstance.cachedConstantTooltip(Component.translatable("options.idle_boost.renderDistance.tooltip")),
                        (label, value) -> Options.genericValueLabel(label, Component.translatable("options.chunks", value)),
                        new OptionInstance.IntRange(2, 64),
                        config.renderDistance,
                        value -> config.renderDistance = value
                )
        );

        this.list.addBig(new OptionInstance<>(
                "options.idle_boost.backgroundDelay",
                OptionInstance.cachedConstantTooltip(Component.translatable("options.idle_boost.backgroundDelay.tooltip")),
                (label, value) -> Options.genericValueLabel(label, value == 0 ? Component.translatable("options.idle_boost.backgroundDelay.immediate") : Component.translatable("options.idle_boost.backgroundDelay.seconds", value)),
                new OptionInstance.IntRange(0, 60),
                config.backgroundDelaySeconds,
                value -> config.backgroundDelaySeconds = value
        ));

        this.list.addBig(OptionInstance.createBoolean(
                "options.idle_boost.debugModeToggle",
                OptionInstance.cachedConstantTooltip(Component.translatable("options.idle_boost.debugModeToggle.tooltip")),
                config.debugModeToggle,
                enabled -> config.debugModeToggle = enabled
        ));
    }

    @Override
    protected void repositionElements() {
        super.repositionElements();
        this.list.updateSize(this.width, this.layout);
    }

    @Override
    public void removed() {
        super.removed();
        IBConfigManager.saveConfig();
    }
}