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

import org.spout.api.UnsafeMethod;
import org.spout.api.inventory.Inventory;
import org.spout.api.inventory.InventoryBase;
import org.spout.api.inventory.InventoryViewer;
import org.spout.api.inventory.ItemStack;

import org.spout.vanilla.controller.living.player.VanillaPlayer;
import org.spout.vanilla.inventory.Convertable;
import org.spout.vanilla.inventory.Parsable;
import org.spout.vanilla.protocol.msg.CloseWindowMessage;
import org.spout.vanilla.protocol.msg.OpenWindowMessage;
import org.spout.vanilla.util.InventoryUtil;
import org.spout.vanilla.util.ItemUtil;

/**
 * Represents a window.
 * @param <T>
 */
public abstract class Window<T extends Parsable> implements InventoryViewer {
	protected final T container;
	protected final VanillaPlayer owner;
	protected WindowType type;
	protected String title;
	protected int size;
	protected ItemStack itemOnCursor;
	protected final int instanceId = InventoryUtil.nextWindowId();
	protected boolean sendMessage;

	public Window(T container, VanillaPlayer owner, WindowType type, String title, int size, boolean sendMessage) {
		this.container = container;
		this.owner = owner;
		this.type = type;
		this.title = title;
		this.size = size;
		this.sendMessage = sendMessage;
	}

	public Window(T container, VanillaPlayer owner, WindowType type, String title, int size) {
		this(container, owner, type, title, size, true);
	}

	/**
	 * Gets the {@link Parsable} that the Window uses to parse it's clicks.
	 * @return the container
	 */
	public T getContainer() {
		return container;
	}

	/**
	 * Gets the final instanceId of the Window. Used for communicating with the client.
	 * @return instanceId
	 */
	public int getInstanceId() {
		return instanceId;
	}

	/**
	 * Gets the owner of a window.
	 * @return {@link VanillaPlayer} owner
	 */
	public VanillaPlayer getOwner() {
		return owner;
	}

	/**
	 * Gets the {@link WindowType} of window rendered.
	 * @return window type
	 */
	public WindowType getType() {
		return type;
	}

	/**
	 * Sets the {@link WindowType} of the window rendered.
	 * If the window is opened, changes will not be staged until the window is closed and reopened.
	 * @param type of window
	 */
	public void setType(WindowType type) {
		this.type = type;
	}

	/**
	 * Gets the title of the window rendered.
	 * @return title of the window
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the window rendered.
	 * If the window is opened, changes will not be staged until the window is closed and reopened.
	 * @param title of window
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the amount of slots the window will render.
	 * @return amount of slots
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets the amount of slot the window will render.
	 * If the window is opened, changes will not be staged until the window is closed and reopened.
	 * @param size to render
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Whether or not the player has an {@link ItemStack} on his cursor.
	 * @return true if item is on cursor
	 */
	public boolean hasItemOnCursor() {
		return itemOnCursor != null;
	}

	/**
	 * Gets the {@link ItemStack} currently on the players cursor. Can be null.
	 * @return item on cursor
	 */
	public ItemStack getItemOnCursor() {
		return itemOnCursor;
	}

	/**
	 * Sets the {@link ItemStack} on the cursor.
	 * This method is marked as unsafe due to the fact that this will <b>not</b> update the item on cursor
	 * @param item to set
	 */
	@UnsafeMethod
	public void setItemOnCursor(ItemStack item) {
		itemOnCursor = item;
	}

	/**
	 * Opens the window.
	 */
	public void open() {
		owner.setActiveWindow(this);
		if (sendMessage) {
			owner.getPlayer().getSession().send(new OpenWindowMessage(instanceId, type.getId(), title, size));
		}
		if (container instanceof Inventory) {
			Inventory inventory = (Inventory) container;
			inventory.addViewer(this);
			updateAll(inventory, inventory.getContents());
		}
	}

	/**
	 * Closes the window.
	 */
	public void close() {
		if (container instanceof Inventory) {
			((Inventory) container).removeViewer(this);
		}
		if (sendMessage) {
			owner.getPlayer().getSession().send(new CloseWindowMessage(instanceId));
		}
		owner.setActiveWindow(owner.getDefaultWindow());
	}

	/**
	 * Simulates a click on the window with the slot <b>natively</b> sent from the client. Cannot be overridden.
	 * Parses the container to obtain the {@link Inventory} clicked and converts the slot if the parsable is
	 * and instance of a {@link Convertable}.
	 * This called {@link Window#onClick(org.spout.api.inventory.Inventory, int, boolean, boolean)}
	 * @param slot sent from client
	 * @param rightClick if the player right clicked.
	 * @param shift if the player "shift-clicked".
	 * @return true if the click is permitted and if the client should allow the transaction to occur.
	 */
	public final boolean click(int slot, boolean rightClick, boolean shift) {
		Inventory inventory = container.parse(this, owner, slot);
		if (container instanceof Convertable) {
			slot = ((Convertable) container).convert(this, inventory, slot);
		}
		return onClick(inventory, slot, rightClick, shift);
	}

	/**
	 * Simulates a click on the window with the slot <b>fully converted</b> if need be.
	 * @param inventory being handled
	 * @param clickedSlot the slot clicked on the window
	 * @param rightClick if it was a right click
	 * @param shift if it was a "shift-click"
	 * @return true if the click is permitted and if the client should allow the transaction to occur.
	 */
	public boolean onClick(Inventory inventory, int clickedSlot, boolean rightClick, boolean shift) {
		owner.getPlayer().sendMessage(Integer.toString(clickedSlot));
		boolean result;
		if (rightClick) {
			result = onRightClick(inventory, clickedSlot, shift);
		} else {
			result = onLeftClick(inventory, clickedSlot, shift);
		}
		return result;
	}

	/**
	 * Called when the player right-clicks on a slot in this window
	 * @param clickedSlot
	 * @param shift whether shift was pressed
	 * @return True to notify that the operation was allowed
	 */
	public boolean onRightClick(Inventory inventory, int clickedSlot, boolean shift) {
		if (shift) {
			return onShiftClick(inventory, clickedSlot);
		}
		return onRightClick(inventory, clickedSlot);
	}

	/**
	 * Called when the player right-clicks on a slot without holding shift
	 * @param clickedSlot
	 * @return True to notify that the operation was allowed
	 */
	public boolean onRightClick(Inventory inventory, int clickedSlot) {

		ItemStack clickedItem = inventory.getItem(clickedSlot);
		if (clickedItem == null) {
			if (itemOnCursor != null) {
				// cursor > clicked item
				ItemStack cursorItem = itemOnCursor;
				clickedItem = cursorItem.clone();
				clickedItem.setAmount(1);
				cursorItem.setAmount(cursorItem.getAmount() - clickedItem.getAmount());
				inventory.setItem(clickedSlot, clickedItem);
				itemOnCursor = cursorItem.getAmount() <= 0 ? null : cursorItem;
				return true;
			}

			return true;
		}

		if (itemOnCursor != null) {
			// clicked item + cursor
			ItemStack cursorItem = itemOnCursor;
			if (!cursorItem.equalsIgnoreSize(clickedItem)) {
				// swap
				itemOnCursor = clickedItem;
				inventory.setItem(clickedSlot, cursorItem);
				return true;
			}

			if (clickedItem.getAmount() >= clickedItem.getMaxStackSize()) {
				return false;
			}

			// transfer one item
			clickedItem.setAmount(clickedItem.getAmount() + 1);
			cursorItem.setAmount(cursorItem.getAmount() - 1);
			inventory.setItem(clickedSlot, clickedItem);
			itemOnCursor = cursorItem.getAmount() <= 0 ? null : cursorItem;
			return true;
		}

		// 1/2 clicked item > cursor
		ItemStack newItem = clickedItem.clone();
		newItem.setAmount(newItem.getAmount() / 2);
		clickedItem.setAmount(clickedItem.getAmount() - newItem.getAmount());
		inventory.setItem(clickedSlot, newItem.getAmount() <= 0 ? null : newItem);
		itemOnCursor = clickedItem.getAmount() <= 0 ? null : clickedItem;
		return true;
	}

	/**
	 * Called when the player left-clicks on a slot in this window
	 * @param clickedSlot
	 * @param shift whether shift was pressed
	 * @return True to notify that the operation was allowed
	 */
	public boolean onLeftClick(Inventory inventory, int clickedSlot, boolean shift) {
		if (shift) {
			return onShiftClick(inventory, clickedSlot);
		}
		return onLeftClick(inventory, clickedSlot);
	}

	/**
	 * Called when the player left-clicks on a slot without holding shift
	 * @param clickedSlot
	 * @return True to notify that the operation was allowed
	 */
	public boolean onLeftClick(Inventory inventory, int clickedSlot) {
		ItemStack clickedItem = inventory.getItem(clickedSlot);
		if (clickedItem == null) {
			if (itemOnCursor != null) {
				// cursor > clicked item
				inventory.setItem(clickedSlot, this.getItemOnCursor());
				itemOnCursor = null;
				return true;
			}

			return true;
		}

		if (itemOnCursor == null) {
			// clicked item > cursor
			itemOnCursor = clickedItem;
			inventory.setItem(clickedSlot, null);
			return true;
		}

		// clicked item + cursor
		ItemStack cursorItem = itemOnCursor;
		if (cursorItem.equalsIgnoreSize(clickedItem)) {
			// stack
			clickedItem.stack(cursorItem);
			inventory.setItem(clickedSlot, clickedItem);
			itemOnCursor = cursorItem.getAmount() <= 0 ? null : cursorItem;
			return true;
		}

		// swap
		itemOnCursor = clickedItem;
		inventory.setItem(clickedSlot, cursorItem);
		return true;
	}

	/**
	 * Called when the player left or right clicks on an item while holding shift
	 * @param clickedSlot
	 * @return True to notify that the operation was allowed
	 */
	public boolean onShiftClick(Inventory inventory, int clickedSlot) {
		return false; //TODO: Implement shift-transferring
	}

	public void dropItemOnCursor() {
		if (this.hasItemOnCursor()) {
			ItemUtil.dropItemNaturally(this.getOwner().getParent().getPosition(), this.getItemOnCursor());
			this.setItemOnCursor(null);
		}
	}

	/**
	 * Called when the player clicks outside the window
	 * @return True to notify that the operation was allowed
	 */
	public boolean onOutsideClick() {
		this.dropItemOnCursor();
		return true;
	}

	@Override
	public void onSlotSet(InventoryBase inventoryBase, int i, ItemStack itemStack) {
		owner.getPlayer().getNetworkSynchronizer().onSlotSet(inventoryBase, i, itemStack);
	}

	@Override
	public void updateAll(InventoryBase inventoryBase, ItemStack[] itemStacks) {
		owner.getPlayer().getNetworkSynchronizer().updateAll(inventoryBase, itemStacks);
	}
}
