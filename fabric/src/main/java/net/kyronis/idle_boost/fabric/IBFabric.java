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

package net.kyronis.idle_boost.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.kyronis.idle_boost.IdleBoost;
import net.kyronis.idle_boost.activity.IBWindowActivityHandler;

public class IBFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        IdleBoost.init();
        ClientTickEvents.START_CLIENT_TICK.register(client -> IBWindowActivityHandler.handleClientTick());
    }
}