package xdki113r.redstonekit.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRedstoneGun extends Item
{
	public ItemRedstoneGun(int id)
	{
		super(id);
		maxStackSize = 1;
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
	{
		int i;
		ItemStack invItemStackInSlot;
		int invItemID = 0;
		int bulletNorm = RedstoneKit.redstoneBullet.itemID;
		
    	for(i = 0; i <= 36; i++)
    	{
        	invItemStackInSlot = player.inventory.getStackInSlot(i);
        	
        	if(invItemStackInSlot != null)
        	{
        		invItemID = invItemStackInSlot.itemID;
        	}
        	
    		if(invItemID == bulletNorm)
    		{
    			if(invItemStackInSlot != null)
    			{
    				player.inventory.mainInventory[i].stackSize--;
    				
    				if(!world.isRemote)
    		        {
    		            world.spawnEntityInWorld(new EntityRedstoneBull(world, player));
    		        }
    			}
    		}
    	}
		
		return itemstack;
	}
}
