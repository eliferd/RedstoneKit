package xdki113r.redstonekit.common;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockRedstoneGlass extends Block
{

	public BlockRedstoneGlass(int id, Material material)
	{
		super(id, material);
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
