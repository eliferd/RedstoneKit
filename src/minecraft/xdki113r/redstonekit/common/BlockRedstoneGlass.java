package xdki113r.redstonekit.common;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;

public class BlockRedstoneGlass extends BlockBreakable
{

	public BlockRedstoneGlass(int id, Material material)
	{
		super(id, "redstonekit:RedstoneGlass", material, false);
	}
	
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return 0;
	}
	
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	public boolean canSilkHarvest()
	{
		return true;
	}
	
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}
}
