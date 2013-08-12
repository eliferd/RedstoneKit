package xdki113r.redstonekit.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRedstoneGrenade extends Item
{
	public ItemRedstoneGrenade(int par1)
	{
		super(par1);
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
    	if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        par2World.playSoundAtEntity(par3EntityPlayer, "random.fuse", 1.0F, 1.0F);
        if (!par2World.isRemote)
        {
            par2World.spawnEntityInWorld(new EntityRedGrenade(par2World, par3EntityPlayer));
        }

        return par1ItemStack;
    }
}
