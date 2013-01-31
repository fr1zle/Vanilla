/*
 * This file is part of Vanilla.
 *
 * Copyright (c) 2011-2012, Spout LLC <http://www.spout.org/>
 * Vanilla is licensed under the Spout License Version 1.
 *
 * Vanilla is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the Spout License Version 1.
 *
 * Vanilla is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the Spout License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://spout.in/licensev1> for the full license, including
 * the MIT license.
 */
package org.spout.vanilla.plugin.component.living.neutral;

import com.bulletphysics.collision.shapes.BoxShape;

import org.spout.api.material.Material;
import org.spout.api.util.Parameter;
import org.spout.api.component.impl.SceneComponent;

import org.spout.vanilla.api.component.Neutral;
import org.spout.vanilla.api.data.Difficulty;
import org.spout.vanilla.api.material.VanillaMaterial;

import org.spout.vanilla.plugin.VanillaPlugin;
import org.spout.vanilla.plugin.component.living.Living;
import org.spout.vanilla.plugin.component.misc.DamageComponent;
import org.spout.vanilla.plugin.component.misc.HealthComponent;
import org.spout.vanilla.plugin.data.VanillaData;
import org.spout.vanilla.plugin.material.VanillaMaterials;
import org.spout.vanilla.plugin.protocol.entity.creature.EndermanEntityProtocol;

/**
 * A component that identifies the entity as an Enderman.
 */
public class Enderman extends Living implements Neutral {
	@Override
	public void onAttached() {
		super.onAttached();
		getOwner().getNetwork().setEntityProtocol(VanillaPlugin.VANILLA_PROTOCOL_ID, new EndermanEntityProtocol());
		SceneComponent scene = getOwner().getScene();
		scene.setShape(7f, new BoxShape(1F, 3F, 1F));
		scene.setFriction(10f);
		scene.setRestitution(0f);

		if (getAttachedCount() == 1) {
			getOwner().add(HealthComponent.class).setSpawnHealth(40);
		}

		DamageComponent damage = getOwner().add(DamageComponent.class);
		damage.getDamageLevel(Difficulty.EASY).setAmount(4);
		damage.getDamageLevel(Difficulty.NORMAL).setAmount(7);
		damage.getDamageLevel(Difficulty.HARD).setAmount(10);
		damage.getDamageLevel(Difficulty.HARDCORE).setAmount(damage.getDamageLevel(Difficulty.HARD).getAmount());
	}

	public Material getHeldMaterial() {
		return VanillaMaterials.getMaterial(getData().get(VanillaData.HELD_MATERIAL));
	}

	public void setHeldMaterial(VanillaMaterial mat) {
		byte id = (byte) mat.getMinecraftId();
		getData().put(VanillaData.HELD_MATERIAL, id);
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 16, id));
	}

	public byte getHeldMaterialData() {
		return getData().get(VanillaData.HELD_MATERIAL_DATA);
	}

	public void setHeldMaterialData(byte data) {
		getData().put(VanillaData.HELD_MATERIAL_DATA, data);
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 17, data));
	}

	public boolean isAggressive() {
		return getData().get(VanillaData.AGGRESSIVE);
	}

	public void setAggressive(boolean aggro) {
		getData().put(VanillaData.AGGRESSIVE, aggro);
		setMetadata(new Parameter<Byte>(Parameter.TYPE_BYTE, 18, aggro ? (byte) 1 : 0));
	}
}
