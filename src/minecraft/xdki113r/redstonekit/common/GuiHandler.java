package xdki113r.redstonekit.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		if(tileentity instanceof TileEntityMicrowave)
		{
			return new ContainerRedstoneMicrowave(player.inventory, (TileEntityMicrowave) tileentity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		
		if(tileentity instanceof TileEntityMicrowave)
		{
			return new GuiMicrowave(player.inventory, (TileEntityMicrowave) tileentity);
		}
		return null;
	}
}
