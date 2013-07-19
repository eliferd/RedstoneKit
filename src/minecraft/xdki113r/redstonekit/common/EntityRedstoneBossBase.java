package xdki113r.redstonekit.common;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityRedstoneBossBase extends EntityMob{

	public EntityRedstoneBossBase(World par1World) {
		super(par1World);
		this.experienceValue = 95;
	}
	
	public void onBossLivingUpdate()
	{
		super.onLivingUpdate();
	}
	
	public void onBossUpdate()
	{
		super.onUpdate();
	}
	
}
