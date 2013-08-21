package xdki113r.redstonekit.client;

import java.util.logging.Logger;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class RedSoundEvent
{
	@ForgeSubscribe
	public void onSound(SoundLoadEvent event)
	{
		try
		{
			
		} catch(Exception e)
		{
			Logger.getLogger("RedstoneKit").warning("Failed to register sounds.");
		}
	}
}
