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

import org.spout.api.inventory.Inventory;
import org.spout.api.inventory.ItemStack;
import org.spout.api.inventory.special.InventorySlot;

import org.spout.vanilla.inventory.VanillaInventory;

/**
* Represents the four armor slots on a {@link PlayerInventory}.
*/
public class ArmorInventory extends Inventory implements VanillaInventory {
	private static final long serialVersionUID = 1L;
	private final InventorySlot helmet;
	private final InventorySlot chestPlate;
	private final InventorySlot leggings;
	private final InventorySlot boots;

	public ArmorInventory() {
		super(4);
		this.helmet = new InventorySlot(this, 0);
		this.chestPlate = new InventorySlot(this, 1);
		this.leggings = new InventorySlot(this, 2);
		this.boots = new InventorySlot(this, 3);
	}

	/**
	 * Gets the {@link ItemStack} in the boots slot.
	 * @return boots
	 */
	public InventorySlot getHelmet() {
		return this.helmet;
	}

	/**
	 * Sets the {@link ItemStack} in the boots slot.
	 */
	public InventorySlot getChestPlate() {
		return this.chestPlate;
	}

	/**
	 * Gets the {@link ItemStack} in the leggings slot.
	 * @return leggings
	 */
	public InventorySlot getLeggings() {
		return this.leggings;
	}

	/**
	 * Sets the {@link ItemStack} in the leggings slot.
	 */
	public InventorySlot getBoots() {
		return this.boots;
	}
}
