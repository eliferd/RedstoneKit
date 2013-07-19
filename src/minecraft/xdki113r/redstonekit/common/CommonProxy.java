package xdki113r.redstonekit.common;

import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public class CommonProxy
{
	public Minecraft getMinecraftInstance()
	{
		return null;
	}

	public Minecraft getClientMinecraft()
	{
		return null;
	}

	public void render()
	{

	}
	
	public static void addNonMobEntity(Class <? extends Entity> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.registerModEntity(entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
	
}
