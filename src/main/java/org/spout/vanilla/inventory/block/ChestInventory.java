/*
 * This file is part of Vanilla.
 *
 * Copyright (c) 2011-2012, VanillaDev <http://www.spout.org/>
 * Vanilla is licensed under the SpoutDev License Version 1.
 *
 * Vanilla is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * Vanilla is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.vanilla.inventory.block;

import org.spout.api.inventory.Inventory;
import org.spout.api.inventory.InventoryBase;

import org.spout.vanilla.controller.living.player.VanillaPlayer;
import org.spout.vanilla.inventory.Convertable;
import org.spout.vanilla.inventory.Parsable;
import org.spout.vanilla.inventory.VanillaInventory;
import org.spout.vanilla.inventory.player.MainInventory;
import org.spout.vanilla.inventory.window.Window;
import org.spout.vanilla.util.SlotIndexMap;

/**
 * Represents an inventory of a {@link org.spout.vanilla.controller.block.Chest}.
 */
public class ChestInventory extends Inventory implements Parsable, Convertable, VanillaInventory {
	public static final SlotIndexMap SMALL_MAIN_SLOTS = new SlotIndexMap("54-62, 45-53, 36-44, 27-35");
	public static final SlotIndexMap SMALL_CHEST_SLOTS = new SlotIndexMap("18-26, 9-17, 0-8");
	public static final SlotIndexMap LARGE_MAIN_SLOTS = new SlotIndexMap("81-89, 72-80, 63-71, 54-62");
	public static final SlotIndexMap LARGE_CHEST_SLOTS = new SlotIndexMap("45-53, 36-44, 27-35, 18-26, 9-17, 0-8");
	private static final long serialVersionUID = 1L;

	public ChestInventory(int size) {
		super(size);
	}

	@Override
	public Inventory parse(Window window, VanillaPlayer player, int slot) {
		if (slot >= 0 && slot < getSize()) {
			return this;
		} else if (slot >= getSize()) {
			return player.getInventory().getMain();
		}
		return null;
	}

	@Override
	public int convert(Window window, InventoryBase inventory, int i) {
		int size = getSize();
		if (size == 27) {
			if (i >= 0 && i < size) {
				return SMALL_CHEST_SLOTS.getSpoutSlot(i);
			} else if (i >= size && i < 63) {
				return SMALL_MAIN_SLOTS.getSpoutSlot(i);
			}
		} else if (size == 27 * 2) {
			if (i >= 0 && i < size) {
				return LARGE_CHEST_SLOTS.getSpoutSlot(i);
			} else if (i >= size && i < 90) {
				return LARGE_MAIN_SLOTS.getSpoutSlot(i);
			}
		}
		return -1;
	}

	@Override
	public int revert(Window window, InventoryBase inventory, int i) {
		int size = getSize();
		if (size == 27) {
			if (i >= 0 && i < size) {
				return SMALL_CHEST_SLOTS.getMinecraftSlot(i);
			} else if (i >= size && i < 63) {
				return SMALL_MAIN_SLOTS.getMinecraftSlot(i);
			}
		} else if (size == 27 * 2) {
			if (i >= 0 && i < size) {
				return LARGE_CHEST_SLOTS.getMinecraftSlot(i);
			} else if (i >= size && i < 90) {
				return LARGE_MAIN_SLOTS.getMinecraftSlot(i);
			}
		}
		return -1;
	}
}
