/*
 * This file is part of Vanilla.
 *
 * Copyright (c) 2011-2012, SpoutDev <http://www.spout.org/>
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
package org.spout.vanilla.material.block.other;

import org.spout.api.entity.Entity;
import org.spout.api.event.player.PlayerInteractEvent.Action;
import org.spout.api.geo.cuboid.Block;
import org.spout.api.material.block.BlockFace;
import org.spout.api.material.block.BlockFaces;
import org.spout.vanilla.material.VanillaBlockMaterial;
import org.spout.vanilla.material.block.Openable;
import org.spout.vanilla.util.VanillaPlayerUtil;

public class FenceGate extends VanillaBlockMaterial implements Openable {

	public FenceGate(String name, int id) {
		super(name, id);
	}

	@Override
	public boolean isPlacementSuppressed() {
		return true;
	}

	@Override
	public void onInteractBy(Entity entity, Block block, Action action, BlockFace clickedFace) {
		super.onInteractBy(entity, block, action, clickedFace);
		if (action == Action.LEFT_CLICK && VanillaPlayerUtil.isCreative(block.getSource())) {
			return;
		}
		this.toggleOpen(block);
	}

	public BlockFace getFacing(Block block) {
		return BlockFaces.WNES.get(block.getData() & 0x3);
	}

	public void setFacing(Block block, BlockFace facing) {
		short data = (short) (block.getData() & ~0x3);
		data += BlockFaces.WNES.indexOf(facing, 0);
		block.setData(data);
	}

	@Override
	public void toggleOpen(Block block) {
		this.setOpen(block, !this.isOpen(block));
	}

	@Override
	public void setOpen(Block block, boolean open) {
		short data = block.getData();
		if (open) {
			data |= 0x4;
		} else {
			data &= ~0x4;
		}
		block.setData(data);
	}

	@Override
	public boolean onPlacement(Block block, short data, BlockFace against, boolean isClickedBlock) {
		block.setMaterial(this);
		this.setFacing(block, VanillaPlayerUtil.getFacing(block.getSource()));
		return true;
	}

	@Override
	public boolean isOpen(Block block) {
		return (block.getData() & 0x4) == 0x4;
	}
}
