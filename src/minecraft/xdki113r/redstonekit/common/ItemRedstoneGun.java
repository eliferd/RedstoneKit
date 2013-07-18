package xdki113r.redstonekit.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRedstoneGun extends Item{

	public ItemRedstoneGun(int par1) {
		super(par1);
		maxStackSize = 1;
	}

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player)
    {
    	int i;
    	ItemStack invItemStackInSlot;
    	int invItemID = 0;
    	
        return itemstack;
    } 
	
}
