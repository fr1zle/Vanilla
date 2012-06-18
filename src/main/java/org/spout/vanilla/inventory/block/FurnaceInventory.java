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
import org.spout.api.inventory.ItemStack;

import org.spout.vanilla.controller.living.player.VanillaPlayer;
import org.spout.vanilla.inventory.Convertable;
import org.spout.vanilla.inventory.Parsable;
import org.spout.vanilla.inventory.VanillaInventory;
import org.spout.vanilla.inventory.player.MainInventory;
import org.spout.vanilla.inventory.window.Window;
import org.spout.vanilla.material.Fuel;
import org.spout.vanilla.material.TimedCraftable;
import org.spout.vanilla.util.SlotIndexMap;

public class FurnaceInventory extends Inventory implements Parsable, Convertable, VanillaInventory {
	public static final SlotIndexMap MAIN_SLOTS = new SlotIndexMap("30-38, 21-29, 12-20, 3-11");
	public static final SlotIndexMap SLOTS = new SlotIndexMap("1, 2, 0");
	public static final int MAIN_START = 2, MAIN_END = 39;
	public static final int START = 0, END = 3;
	private static final long serialVersionUID = 1L;

	public FurnaceInventory() {
		super(3);
	}

	/**
	 * Returns the {@link org.spout.api.inventory.ItemStack} in the output slot (slot 37); can return null.
	 * @return output item stack
	 */
	public ItemStack getOutput() {
		return getItem(1);
	}

	/**
	 * Sets the output of the inventory.
	 * @param output
	 */
	public void setOutput(ItemStack output) {
		setItem(1, output);
	}

	/**
	 * Returns the {@link ItemStack} in the fuel slot (slot 35); can return null.
	 * @return fuel item stack
	 */
	public ItemStack getFuel() {
		return getItem(0);
	}

	/**
	 * Sets the fuel slot of the inventory
	 * @param fuel
	 */
	public void setFuel(ItemStack fuel) {
		setItem(0, fuel);
	}

	/**
	 * Returns the {@link ItemStack} in the ingredient slot (slot 38); can return null.
	 * @return ingredient item stack
	 */
	public ItemStack getIngredient() {
		return getItem(2);
	}

	/**
	 * Sets the {@link ItemStack} in the ingredient slot (slot 39); can return null;
	 * @param ingredient
	 */
	public void setIngredient(ItemStack ingredient) {
		setItem(2, ingredient);
	}

	/**
	 * Whether or not the inventory is fueled and ready to go!
	 * @return true if has fuel in slot.
	 */
	public boolean hasFuel() {
		return getFuel() != null && getFuel().getMaterial() instanceof Fuel;
	}

	/**
	 * Whether or not the inventory has an ingredient and ready to cook!
	 * @return true if has ingredient in slot.
	 */
	public boolean hasIngredient() {
		return getIngredient() != null && getIngredient().getMaterial() instanceof TimedCraftable;
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
		} else if (inventory instanceof FurnaceInventory) {
			return SLOTS.getSpoutSlot(i);
		}
		return -1;
	}

	@Override
	public int revert(Window window, InventoryBase inventory, int i) {
		if (inventory instanceof MainInventory) {
			return MAIN_SLOTS.getSpoutSlot(i);
		} else if (inventory instanceof FurnaceInventory) {
			return SLOTS.getSpoutSlot(i);
		}
		return -1;
	}
}
