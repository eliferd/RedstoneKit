package xdki113r.redstonekit.client;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import xdki113r.redstonekit.common.CommonProxy;

public class ClientProxy extends CommonProxy{

	@Override
	public Minecraft getMinecraftInstance()
	{
		return Minecraft.getMinecraft();
	}
	
	@Override
	public Minecraft getClientMinecraft()
	{
		return FMLClientHandler.instance().getClient();
	}
	
	@Override
	public void render()
	{
		
	}
	
}
