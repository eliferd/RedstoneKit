package Redstonekit.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RedCreativeTab extends CreativeTabs {

	public RedCreativeTab(String label) {
		super(label);
	}
	
	@Override
	public ItemStack getIconItemStack()
	{
		return new ItemStack(Item.redstone);
	}
	
}
