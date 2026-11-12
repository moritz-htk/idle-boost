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

package net.kyronis.idle_boost.neoforge;

import net.kyronis.idle_boost.IdleBoost;
import net.kyronis.idle_boost.config.IBConfigScreen;
import net.kyronis.idle_boost.activity.IBWindowActivityHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = IdleBoost.MOD_ID, dist = Dist.CLIENT)
public class IBNeoForge {
    public IBNeoForge() {
        IdleBoost.init();
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> ((client, parent) -> new IBConfigScreen(parent)));
        NeoForge.EVENT_BUS.addListener(this::onClientTick);
    }

    public void onClientTick(ClientTickEvent.Pre event) {
        IBWindowActivityHandler.handleClientTick();
    }
}