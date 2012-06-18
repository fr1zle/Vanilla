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
 * Represents an inventory of a {@link org.spout.vanilla.material.block.controlled.CraftingTable}. This is not cached in any form.
 */
public class CraftingTableInventory extends Inventory implements Parsable, Convertable, VanillaInventory {
	public static final SlotIndexMap MAIN_SLOTS = new SlotIndexMap("37-45, 28-36, 19-27, 10-18");
	public static final SlotIndexMap SLOTS = new SlotIndexMap("7-9, 4-6, 1-3, 0");
	public static final int MAIN_START = 9, MAIN_END = 46;
	public static final int START = 0, END = 10;
	private static final long serialVersionUID = 1L;

	public CraftingTableInventory() {
		super(10);
	}

	@Override
	public Inventory parse(Window window, VanillaPlayer player, int slot) {
		if (slot > MAIN_START && slot < MAIN_END) {
			return player.getInventory().getMain();
		} else if (slot >= START && slot < END) {
			return this;
		}
		return null;
	}

	@Override
	public int convert(Window window, InventoryBase inventory, int i) {
		if (inventory instanceof MainInventory) {
			return MAIN_SLOTS.getSpoutSlot(i);
		} else if (inventory instanceof CraftingTableInventory) {
			return SLOTS.getSpoutSlot(i);
		}
		return -1;
	}

	@Override
	public int revert(Window window, InventoryBase inventory, int i) {
		if (inventory instanceof MainInventory) {
			return MAIN_SLOTS.getMinecraftSlot(i);
		} else if (inventory instanceof CraftingTableInventory) {
			return SLOTS.getMinecraftSlot(i);
		}
		return -1;
	}
}
