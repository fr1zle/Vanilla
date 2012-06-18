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
package org.spout.vanilla.inventory.player;

import java.io.Serializable;

import org.spout.api.inventory.Inventory;
import org.spout.api.inventory.InventoryBase;

import org.spout.vanilla.controller.living.player.VanillaPlayer;
import org.spout.vanilla.inventory.Convertable;
import org.spout.vanilla.inventory.Parsable;
import org.spout.vanilla.inventory.VanillaInventory;
import org.spout.vanilla.inventory.window.Window;
import org.spout.vanilla.util.SlotIndexMap;

public class PlayerInventory implements Serializable, VanillaInventory, Parsable, Convertable {
	public static final SlotIndexMap MAIN_SLOTS = new SlotIndexMap("36-44, 27-35, 18-26, 9-17");
	public static final SlotIndexMap ARMOR_SLOTS = new SlotIndexMap("8, 7, 6, 5");
	public static final SlotIndexMap CRAFTING_GRID_SLOTS = new SlotIndexMap("3-4, 0, 1-2");
	public static final int MAIN_START = 8, MAIN_END = 45;
	public static final int ARMOR_START = 4, ARMOR_END = 9;
	public static final int CRAFTING_GRID_START = 0, CRAFTING_GRID_END = 5;
	public static final int MAIN_SIZE = 36;
	public static final int CRAFTING_GRID_SIZE = 5;
	public static final int ARMOR_SIZE = 4;
	public static final int TOTAL_SIZE = MAIN_SIZE + CRAFTING_GRID_SIZE + ARMOR_SIZE;
	private static final long serialVersionUID = 1L;
	private final MainInventory main = new MainInventory();
	private final CraftingGridInventory craftingGrid = new CraftingGridInventory();
	private final ArmorInventory armor = new ArmorInventory();

	public MainInventory getMain() {
		return main;
	}

	public CraftingGridInventory getCraftingGrid() {
		return craftingGrid;
	}

	public ArmorInventory getArmor() {
		return armor;
	}

	@Override
	public Inventory parse(Window window, VanillaPlayer player, int slot) {
		if (slot > MAIN_START && slot < MAIN_END) {
			return main;
		} else if (slot > ARMOR_START && slot < ARMOR_END) {
			return armor;
		} else if (slot >= CRAFTING_GRID_START && slot < CRAFTING_GRID_END) {
			return craftingGrid;
		}
		return null;
	}

	@Override
	public int convert(Window window, InventoryBase inventory, int i) {
		if (inventory instanceof MainInventory) {
			return MAIN_SLOTS.getSpoutSlot(i);
		} else if (inventory instanceof ArmorInventory) {
			return ARMOR_SLOTS.getSpoutSlot(i);
		} else if (inventory instanceof CraftingGridInventory) {
			return CRAFTING_GRID_SLOTS.getSpoutSlot(i);
		}
		return -1;
	}

	@Override
	public int revert(Window window, InventoryBase inventory, int i) {
		if (inventory instanceof MainInventory) {
			return MAIN_SLOTS.getMinecraftSlot(i);
		} else if (inventory instanceof ArmorInventory) {
			return ARMOR_SLOTS.getMinecraftSlot(i);
		} else if (inventory instanceof CraftingGridInventory) {
			return CRAFTING_GRID_SLOTS.getMinecraftSlot(i);
		}
		return -1;
	}
}
