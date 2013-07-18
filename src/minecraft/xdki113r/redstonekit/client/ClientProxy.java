package xdki113r.redstonekit.client;

import net.minecraft.client.Minecraft;
import xdki113r.redstonekit.common.CommonProxy;
import cpw.mods.fml.client.FMLClientHandler;

public class ClientProxy extends CommonProxy
{

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
