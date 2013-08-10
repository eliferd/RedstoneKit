package xdki113r.redstonekit.client;

import net.minecraft.client.Minecraft;
import xdki113r.redstonekit.common.CommonProxy;
import xdki113r.redstonekit.common.EntityRedGrenade;
import xdki113r.redstonekit.common.EntityRedstoneBoss;
import xdki113r.redstonekit.common.EntityRedstoneBull;
import xdki113r.redstonekit.common.RedstoneKit;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

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
		RenderingRegistry.registerEntityRenderingHandler(EntityRedstoneBoss.class, new RenderRedstoneBoss(new ModelRedstoneBoss(), 6F));
		RenderingRegistry.registerEntityRenderingHandler(EntityRedstoneBull.class, new RenderRedstoneBull());
		RenderingRegistry.registerEntityRenderingHandler(EntityRedGrenade.class, new RenderRedstoneGrenade(RedstoneKit.redstoneGrenade));
	}

}
