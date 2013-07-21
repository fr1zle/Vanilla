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
package org.spout.vanilla.protocol;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import org.spout.api.inventory.ItemStack;
import org.spout.api.util.Parameter;
import org.spout.vanilla.EngineFaker;
import org.spout.vanilla.material.VanillaMaterials;

import static org.spout.vanilla.protocol.VanillaChannelBufferUtils.getExpandedHeight;
import static org.spout.vanilla.protocol.VanillaChannelBufferUtils.getShifts;

public class VanillaChannelBufferUtilsTest {
	public static final List<Parameter<?>> TEST_PARAMS = new ArrayList<Parameter<?>>();

	static {
		TEST_PARAMS.add(new Parameter<Byte>(Parameter.TYPE_BYTE, 1, (byte) 33));
		TEST_PARAMS.add(new Parameter<Short>(Parameter.TYPE_SHORT, 2, (short) 333));
		TEST_PARAMS.add(new Parameter<Integer>(Parameter.TYPE_INT, 3, 22));
		TEST_PARAMS.add(new Parameter<Float>(Parameter.TYPE_FLOAT, 4, 1.23F));
		TEST_PARAMS.add(new Parameter<String>(Parameter.TYPE_STRING, 5, "Hello World"));
		TEST_PARAMS.add(new Parameter<ItemStack>(Parameter.TYPE_ITEM, 6, new ItemStack(VanillaMaterials.BEDROCK, 5)));
	}
	@Test
	public void testShifts() {
		for (int i = 2; i < 12; ++i) {
			final int origHeight = (int) Math.pow(2, i);
			assertEquals(getExpandedHeight(getShifts(origHeight) - 1), origHeight);
		}
	}
}