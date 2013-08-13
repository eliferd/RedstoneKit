package xdki113r.redstonekit.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRedstoneGun extends Item
{
	public float muzzleVelocity;
    public int damage;
    public float headshotMultiplier;
    public float spread;
    public float recoil;

	public ItemRedstoneGun(int id)
	{
		super(id);
        damage = 0;
        headshotMultiplier = 2.0F;
        muzzleVelocity = 1.5F;
        spread = 1.0F;
        recoil = 1.0F;
		maxStackSize = 1;
	}

    public int getDamageVsEntity(Entity entity)
    {
        return 4;
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
    				if(!player.capabilities.isCreativeMode)
    				{
    					player.inventory.consumeInventoryItem(invItemID);
    				}
    				
    				if(!world.isRemote)
    		        {
    		            world.spawnEntityInWorld(new EntityRedstoneBull(world, player, this, 0.1F, 1.0F, 1F, 0f, 0f));
    		        }
    				
    				itemstack.damageItem(1, player);
    				
    				break;
    			}
    		}
    	}
    	
    	if(player.capabilities.isCreativeMode)
    	{
    		if(!world.isRemote)
	        {
	            world.spawnEntityInWorld(new EntityRedstoneBull(world, player, this, 0.1F, 1.0F, 1F, 0F, 0F));
	        }
    	}
		
		return itemstack;
	}

}
