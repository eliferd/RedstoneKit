package xdki113r.redstonekit.common;

import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemRedstoneArmor extends ItemArmor
{

	public ItemRedstoneArmor(int id, EnumArmorMaterial par2EnumArmorMaterial, int type, int layer)
	{
		super(id, par2EnumArmorMaterial, type, layer);
	}

	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer)
	{
		if(layer == 2)
		{
			return "redstonekit:textures/armor/redstone_2.png";
		} else
		{
			return "redstonekit:textures/armor/redstone_1.png";
		}
	}
}
