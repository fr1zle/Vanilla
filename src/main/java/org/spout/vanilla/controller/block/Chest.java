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
package org.spout.vanilla.controller.block;

import org.spout.api.geo.cuboid.Block;
import org.spout.api.inventory.ItemStack;

import org.spout.vanilla.controller.Container;
import org.spout.vanilla.controller.VanillaControllerTypes;
import org.spout.vanilla.controller.WindowController;
import org.spout.vanilla.controller.living.player.VanillaPlayer;
import org.spout.vanilla.inventory.block.ChestInventory;
import org.spout.vanilla.inventory.window.Window;
import org.spout.vanilla.inventory.window.block.ChestWindow;
import org.spout.vanilla.material.VanillaMaterials;
import org.spout.vanilla.util.VanillaNetworkUtil;

public class Chest extends WindowController implements Container {
	private final ChestInventory inventory = new ChestInventory(isDouble() ? 27 * 2 : 27);
	private boolean opened = false;

	public Chest() {
		super(VanillaControllerTypes.CHEST, VanillaMaterials.CHEST);
	}

	@Override
	public void onAttached() {
		System.out.print("Attached!");
		if (this.data().containsKey("items")) {
			this.inventory.setContents((ItemStack[]) this.data().get("items"));
		}
	}

	@Override
	public void onSave() {
		this.data().put("items", this.inventory.getContents());
	}

	@Override
	public ChestInventory getInventory() {
		return inventory;
	}

	public void setOpened(boolean opened) {
		if (this.opened != opened) {
			this.opened = opened;
			byte data = opened ? (byte) 1 : 0;
			VanillaNetworkUtil.playBlockAction(getBlock(), (byte) 1, data);
		}
	}

	public Chest getOtherHalf() {
		Block other = VanillaMaterials.CHEST.getOtherHalf(this.getBlock());
		if (other == null) {
			return null;
		}
		return VanillaMaterials.CHEST.getController(other);
	}

	public boolean isDouble() {
		return VanillaMaterials.CHEST.isDouble(this.getBlock());
	}

	public boolean isOpened() {
		return opened;
	}

	@Override
	public Window createWindow(VanillaPlayer player) {
		return new ChestWindow(inventory, player);
	}
}
