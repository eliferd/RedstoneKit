package xdki113r.redstonekit.client;

import net.minecraft.entity.player.EntityPlayer;
import xdki113r.redstonekit.common.ModUtils;
import xdki113r.redstonekit.common.RedstoneKit;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.IPlayerTracker;

public class RedstoneKitPlayerTracker implements IPlayerTracker
{

	@Override
	public void onPlayerLogin(EntityPlayer player)
	{
		if(!RedstoneKit.modLoaded)
		{
			player.addChatMessage("RedstoneKit v" + ModUtils.mod_version + " loaded !");
			if(RedstoneKit.anotherModLoadedDetectionByID("MEe"))
			{
				player.addChatMessage("[RedstoneKit] More Endermans Mod by elias54 detected !");
			}
			RedstoneKit.modLoaded = true;
		}
	}

	@Override
	public void onPlayerLogout(EntityPlayer player)
	{
		if(!RedstoneKit.modLoaded)
		{
			RedstoneKit.modLoaded = true;
		}
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player)
	{

	}

	@Override
	public void onPlayerRespawn(EntityPlayer player)
	{

	}

}
