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
package org.spout.vanilla.inventory.window;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a window type that is permitted to render on the Notchian client.
 */
public enum WindowType {
	PLAYER(-1),
	CHEST(0),
	WORKBENCH(1),
	FURNACE(2),
	DISPENSER(3),
	ENCHANTMENT_TABLE(4),
	BREWING_STAND(5);
	private final int id;
	private static final Map<Integer, WindowType> idLookup = new HashMap<Integer, WindowType>();

	static {
		for (WindowType type : WindowType.values()) {
			idLookup.put(type.getId(), type);
		}
	}

	private WindowType(int id) {
		this.id = id;
	}

	/**
	 * Gets the id of the window type.
	 * @return window type id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets a type by id.
	 * @param id to lookup
	 * @return window type of given id
	 */
	public static WindowType get(int id) {
		return idLookup.get(id);
	}
}
