package xdki113r.redstonekit.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RedstoneKitCreativeTabs extends CreativeTabs {

	public RedstoneKitCreativeTabs(String label) {
		super(label);
	}
	
	@Override
	public ItemStack getIconItemStack()
	{
		return (new ItemStack(Item.redstone));
	}

}
