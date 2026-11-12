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

/**
 * Configuration class for Idle Boost mod.
 */
public class IBConfig {
    public boolean modToggle = true;
    public int backgroundDelaySeconds = 2;
    public boolean framerateLimitToggle = true;
    public int framerateLimit = 10;
    public boolean renderDistanceToggle = true;
    public int renderDistance = 2;
    public boolean debugModeToggle = false;
}