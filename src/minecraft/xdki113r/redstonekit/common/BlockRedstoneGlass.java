package xdki113r.redstonekit.common;

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
}
