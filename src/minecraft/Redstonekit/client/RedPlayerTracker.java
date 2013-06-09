package Redstonekit.client;

import Redstonekit.common.RedstoneKitMain;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;

public class RedPlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) 
	{
		if(!RedstoneKitMain.modLoaded && Minecraft.getMinecraft().running)
		{
			player.addChatMessage(RedstoneKitMain.MODNAME + " v" + RedstoneKitMain.MODVERSION + " loaded.");
			RedstoneKitMain.modLoaded = true;
		}else
		{
			RedstoneKitMain.modLoaded = true;
		}
	}
	@Override
	public void onPlayerLogout(EntityPlayer player) 
	{
		if(!RedstoneKitMain.modLoaded)
		{
			RedstoneKitMain.modLoaded = true;
		}
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

}
