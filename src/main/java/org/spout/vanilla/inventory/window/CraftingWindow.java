/*
 * This file is part of Vanilla (http://www.spout.org/).
 *
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
 * the MIT license and the SpoutDev license version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */
package org.spout.vanilla.inventory.window;

import java.util.ArrayList;
import java.util.List;

import org.spout.api.Spout;
import org.spout.api.inventory.Inventory;
import org.spout.api.inventory.ItemStack;
import org.spout.api.inventory.Recipe;
import org.spout.api.inventory.RecipeManager;
import org.spout.api.material.Material;

import org.spout.vanilla.controller.living.player.VanillaPlayer;
import org.spout.vanilla.inventory.CraftingGrid;
import org.spout.vanilla.inventory.Parsable;

/**
 * Represents a window where some grid is present as well as an output to update.
 * @param <T>
 */
public abstract class CraftingWindow<T extends Parsable> extends Window<T> {
	protected final CraftingGrid grid;

	public CraftingWindow(T container, CraftingGrid grid, VanillaPlayer owner, WindowType type, String title, int size) {
		super(container, owner, type, title, size);
		this.grid = grid;
	}

	public CraftingGrid getCraftingGrid() {
		return grid;
	}

	private boolean updateOutput() {
		RecipeManager recipeManager = Spout.getEngine().getRecipeManager();
		Inventory inventory = grid.getGridInventory();
		int[] gridArray = grid.getGridArray();
		int rowSize = grid.getRowSize();
		List<List<Material>> materials = new ArrayList<List<Material>>();
		List<Material> current = new ArrayList<Material>();
		List<Material> shapeless = new ArrayList<Material>();
		int cntr = 0;
		for (int slot : gridArray) {
			cntr++;
			ItemStack item = inventory.getItem(slot);
			Material mat = null;
			if (item != null) {
				mat = item.getMaterial();
			}
			current.add(mat);
			if (mat != null) {
				shapeless.add(mat);
			}
			if (cntr >= rowSize) {
				materials.add(current);
				current = new ArrayList<Material>();
				cntr = 0;
			}
		}
		Recipe recipe = null;
		recipe = recipeManager.matchShapedRecipe(materials);
		if (recipe == null) {
			recipe = recipeManager.matchShapelessRecipe(shapeless);
		}
		if (recipe != null) {
			int outputSlot = grid.getOutputSlot();
			if (inventory.getItem(outputSlot) == null) {
				inventory.setItem(outputSlot, recipe.getResult());
			}
			return true;
		}
		return false;
	}
}
