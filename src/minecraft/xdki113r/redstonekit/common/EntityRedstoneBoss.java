package xdki113r.redstonekit.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityRedstoneBoss extends EntityRedstoneBossBase {

	public int phase;
	public boolean start;
	public static int startCounter;
	
	public EntityRedstoneBoss(World par1World) 
	{
		super(par1World);
		phase = 0;
		start = false;
		startCounter = rand.nextInt(4200) + 20;
	}

	public void onBossLivingUpdate(Entity entity)
	{
		EntityPlayer player = (EntityPlayer)entity;
		if(!start && startCounter > 0)
		{
			startCounter--;
		}
		if(startCounter <= 0)
		{
			start = true;
			phase = 1;
		}
		if(phase == 1)
		{
			//TODO the end of this phase
			motionX = player.motionX;
			motionY = player.motionY; 
			motionZ = player.motionZ;
			moveEntity(motionX, motionY, motionZ);
			for(int i = 0; i < player.motionX && i < player.motionZ; i++)
			{
				launchFireball(player);
			}
			if(rand.nextInt(1820) == 0)
			{
				phase = 2;
			}
		}
		if(phase == 2)
		{
			
		}
		if(phase == 3)
		{
			//TODO the end of this phase
			worldObj.addWeatherEffect(new EntityLightningBolt(worldObj, this.chunkCoordX, this.chunkCoordY, this.chunkCoordZ));
			RedstoneKit.proxy.getClientMinecraft().thePlayer.setInPortal();
		}
		super.onBossLivingUpdate();
	}
	
	public void launchFireball(EntityLivingBase entityTarget)
	{
		EntityLargeFireball fireball = new EntityLargeFireball(worldObj, entityTarget, this.posX, this.posY + 1, this.posZ);
		worldObj.spawnEntityInWorld(fireball);
		
	}
	
	public void onBossUpdate()
	{
		super.onBossUpdate();
	}
	
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource sourceOfDamage, float par2)
    {
    	Entity entity = sourceOfDamage.getEntity();
    	EntityPlayer player = (EntityPlayer)entity;
    	ItemStack itemstack = player.getCurrentEquippedItem();
    	
    	if(entity != null && itemstack != null)
    	{
    		if(itemstack.itemID == Item.swordDiamond.itemID)
    		{
    			
    		}
    	}
    	
    	return true;
    }
}
