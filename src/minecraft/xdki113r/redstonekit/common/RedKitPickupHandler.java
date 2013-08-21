package xdki113r.redstonekit.common;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import cpw.mods.fml.common.IPickupNotifier;

public class RedKitPickupHandler implements IPickupNotifier
{
	@Override
	public void notifyPickup(EntityItem item, EntityPlayer player)
	{
		if(item.getEntityItem().itemID == Item.redstone.itemID)
		{
			player.addStat(RedstoneKit.gettingRedstone, 1);
		}
	}
}
