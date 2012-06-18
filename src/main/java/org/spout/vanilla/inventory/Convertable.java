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
package org.spout.vanilla.inventory;

import org.spout.api.inventory.InventoryBase;

import org.spout.vanilla.inventory.window.Window;

/**
 * Represents a window system that needs a conversion from the native slot sent from the client.
 */
public interface Convertable {
	/**
	 * Converts the integer into the converted integer.
	 * @param inventory being handled.
	 * @param i integer to convert
	 * @return converted integer
	 */
	public int convert(Window window, InventoryBase inventory, int i);

	/**
	 * Reverts the integer into the reverted integer.
	 * @param inventory being handled
	 * @param i to revert into native slot
	 * @return reverted integer
	 */
	public int revert(Window window, InventoryBase inventory, int i);
}
