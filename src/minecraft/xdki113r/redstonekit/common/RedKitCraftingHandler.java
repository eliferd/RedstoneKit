package xdki113r.redstonekit.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.ICraftingHandler;

public class RedKitCraftingHandler implements ICraftingHandler
{

	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix)
	{
		int id = item.getItem().itemID;
		int redPickID = RedstoneKit.redPick.itemID;
		int redGrenadeID = RedstoneKit.redstoneGrenade.itemID;
		int redGlassID = RedstoneKit.redstoneGlass.blockID;
		int microwaveID = RedstoneKit.redstoneMicrowaveIdle.blockID;
		if(id == redPickID)
		{
			player.addStat(RedstoneKit.craftingRedPick, 1);
		} else if(id == redGrenadeID)
		{
			player.addStat(RedstoneKit.craftingRedGrenade, 1);
		} else if(id == redGlassID)
		{
			player.addStat(RedstoneKit.craftingRedGlass, 1);
		} else if(id == microwaveID)
		{
			player.addStat(RedstoneKit.craftingMicrowave, 1);
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item)
	{
		int id = item.getItem().itemID;
		int redstoneID = RedstoneKit.redstoneIngot.itemID;
		if(id == redstoneID)
		{
			player.addStat(RedstoneKit.smeltingRedstone, 1);
		}
	}

}
