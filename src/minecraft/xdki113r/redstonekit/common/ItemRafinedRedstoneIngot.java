package xdki113r.redstonekit.common;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRafinedRedstoneIngot extends Item {

	public ItemRafinedRedstoneIngot(int par1) {
		super(par1);
		// TODO Auto-generated constructor stub
	}
	
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack itemstack)
	{
		return true;
	}
}
