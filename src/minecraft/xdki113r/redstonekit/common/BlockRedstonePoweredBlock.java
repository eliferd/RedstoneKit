package xdki113r.redstonekit.common;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockRedstonePoweredBlock extends Block
{
	private final boolean active;
	
	public BlockRedstonePoweredBlock(int par1, Material par2Material, boolean isActive)
	{
		super(par1, par2Material);
		this.active = isActive;
	}
	
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return RedstoneKit.redstonePoweredBlockIdle.blockID;
	}
	
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return RedstoneKit.redstonePoweredBlockIdle.blockID;
	}
	
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(!par1World.isRemote && this.active && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
		{
			par1World.setBlock(par2, par3, par4, RedstoneKit.redstonePoweredBlockIdle.blockID, 0, 2);
		}
	}
	
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(par1World.isRemote)
			return;
		if(par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) && !this.active)
		{
			par1World.setBlock(par2, par3, par4, RedstoneKit.redstonePoweredBlockActive.blockID, 0, 2);
		}
		else if(!par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) && this.active)
		{
			par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 4);
		}
	}
	
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		if(par1World.isRemote)
			return;
		if(par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) && !this.active)
		{
			par1World.setBlock(par2, par3, par4, RedstoneKit.redstonePoweredBlockActive.blockID, 0, 2);
		}
		else if(!par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) && this.active)
		{
			par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 4);
		}
	}
}
