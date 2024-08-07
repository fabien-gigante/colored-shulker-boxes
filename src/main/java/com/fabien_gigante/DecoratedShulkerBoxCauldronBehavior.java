package com.fabien_gigante;

import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ActionResult;
import net.minecraft.block.cauldron.CauldronBehavior;

public class DecoratedShulkerBoxCauldronBehavior {
	public static void init() {
		// Override cauldron behavior to also clean the secondary color of shulker boxes
		CauldronBehavior CLEAN_SHULKER_BOX = ((state, world, pos, player, hand, stack) -> {
			ItemActionResult ret = CauldronBehavior.CLEAN_SHULKER_BOX.interact(state, world, pos, player, hand, stack);
			if (ret.toActionResult() != ActionResult.PASS && !world.isClient) {
				ItemStack itemStack = player.getStackInHand(hand);
                NbtCompound nbt = BlockEntityExt.getBlockEntityNbt(itemStack);
				if (nbt != null) IDecoratedShulkerBox.putNbtSecondaryColor(nbt, null);
			}
			return ret;
		});
		// Replace vanilla behavior by overriden behavior
		Map<Item, CauldronBehavior> map = CauldronBehavior.WATER_CAULDRON_BEHAVIOR.map();
		map.replaceAll((item, behavior) -> behavior == CauldronBehavior.CLEAN_SHULKER_BOX ? CLEAN_SHULKER_BOX : behavior);
	}
}