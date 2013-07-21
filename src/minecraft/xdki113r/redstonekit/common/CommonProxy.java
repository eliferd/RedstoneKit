package xdki113r.redstonekit.common;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import cpw.mods.fml.common.registry.EntityRegistry;

public class CommonProxy
{
	public Minecraft getMinecraftInstance()
	{
		return Minecraft.getMinecraft();
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
	
	public static void addMobEntity(Class <? extends EntityLiving> entityClass, String entityName, int id, Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int bgEggColor, int fgEggColor, int weightedProb, int min, int max, EnumCreatureType typeOfCreature)
	{
		EntityRegistry.registerGlobalEntityID(entityClass, entityName, EntityRegistry.findGlobalUniqueEntityId(), bgEggColor, fgEggColor);
		EntityRegistry.registerModEntity(entityClass, entityName, id, mod, trackingRange, updateFrequency, sendsVelocityUpdates);
		EntityRegistry.addSpawn(entityClass, weightedProb, min, max, typeOfCreature);
	}
	
}
