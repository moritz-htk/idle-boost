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

package net.kyronis.idle_boost.forge;

import net.kyronis.idle_boost.IdleBoost;
import net.kyronis.idle_boost.activity.IBWindowActivityHandler;
import net.kyronis.idle_boost.config.IBConfigScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(IdleBoost.MOD_ID)
public class IBForge {
    public IBForge(FMLJavaModLoadingContext context) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            Client.init(context);
        }
    }

    private static final class Client {
        private Client() {
        }

        private static void init(FMLJavaModLoadingContext context) {
            IdleBoost.init();
            context.registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new IBConfigScreen(screen)));
            MinecraftForge.EVENT_BUS.addListener(Client::onClientTick);
        }

        private static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.Phase.START) {
                IBWindowActivityHandler.handleClientTick();
            }
        }
    }
}