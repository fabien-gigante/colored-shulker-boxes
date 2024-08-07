package com.fabien_gigante;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

public interface IDecoratedShulkerBox {
	public static final String NBTKEY_SECONDARY_COLOR = "SecondaryColor";
	public static final String NBTKEY_DISPLAYED_ITEM = "DisplayedItem";

	public default boolean hasDecorations() { return hasSecondaryColor() || hasDisplayedItem(); }

	public boolean hasSecondaryColor();
	public DyeColor getSecondaryColor();
	public void setSecondaryColor(DyeColor color);

	public static DyeColor getNbtSecondaryColor(NbtCompound nbt) {
		if (nbt == null || !nbt.contains(NBTKEY_SECONDARY_COLOR)) return null;
		return DyeColor.byId(nbt.getInt(NBTKEY_SECONDARY_COLOR));
	}
	public static void putNbtSecondaryColor(NbtCompound nbt, DyeColor color) {
		if (color != null) nbt.putInt(NBTKEY_SECONDARY_COLOR, color.getId());
		else nbt.remove(NBTKEY_SECONDARY_COLOR);
	}

	public boolean hasDisplayedItem();
	public ItemStack getDisplayedItem();
	public void setDisplayedItem(ItemStack stack);

	public static ItemStack getNbtDisplayedItem(NbtCompound nbt) {
		if (nbt == null || !nbt.contains(NBTKEY_DISPLAYED_ITEM)) return null;
		var id = Identifier.of(nbt.getString(NBTKEY_DISPLAYED_ITEM));
		return new ItemStack(Registries.ITEM.get(id), 1);
	}
	public static void putNbtDisplayedItem(NbtCompound nbt, ItemStack stack) {
		if (stack != null) nbt.putString(NBTKEY_DISPLAYED_ITEM, Registries.ITEM.getId(stack.getItem()).toString());
		else nbt.remove(NBTKEY_DISPLAYED_ITEM);
	}

	public default void setDecorations(IDecoratedShulkerBox source) { 
		if (source == null) return;
		setSecondaryColor(source.getSecondaryColor());
		setDisplayedItem(source.getDisplayedItem());
	}
}
