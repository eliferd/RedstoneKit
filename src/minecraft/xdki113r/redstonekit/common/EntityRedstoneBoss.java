package xdki113r.redstonekit.common;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityRedstoneBoss extends EntityMob {

	public int phase;
	public boolean start;
	public static int startCounter;
	
	public EntityRedstoneBoss(World par1World) 
	{
		super(par1World);
		phase = 0;
		start = false;
		startCounter = 1920;
		this.experienceValue = 915;
		this.yOffset *= 6.0F;
        this.setSize(this.width * 6.0F, this.height * 6.0F);
	}

    protected void func_110147_ax()
    {
        super.func_110147_ax();
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(950.0D);
        this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(40.0D);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.43000000417232513D);
    }
	
    @Override
	public void onLivingUpdate()
	{
    	for(int p = 0; p < 1; p++)
    	{
            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.posY - 0.20000000298023224D - (double)this.yOffset);
            int k = MathHelper.floor_double(this.posZ);
            int l = this.worldObj.getBlockId(i, j, k);

            if (l > 0)
            {
                this.worldObj.spawnParticle("tilecrack_" + l + "_" + this.worldObj.getBlockMetadata(i, j, k), this.posX + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, this.boundingBox.minY + 0.1D, this.posZ + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, 4.0D * ((double)this.rand.nextFloat() - 0.5D), 0.5D, ((double)this.rand.nextFloat() - 0.5D) * 4.0D);
            }
    	}
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			if(startCounter > 0)
			{
				
			}
			if(!start && startCounter > 0)
			{
				startCounter--;
			}
			if(startCounter <= 0 && !start)
			{
				start = true;
				EntityLightningBolt strike = new EntityLightningBolt(worldObj, posX, posY, posZ);
				strike.boltVertex = 12;
				worldObj.spawnEntityInWorld(strike);
				
				phase = 1;
			}
			if(phase == 1)
			{
				//TODO the end of this phase
				motionX = player.motionX;
				motionY = player.motionY; 
				motionZ = player.motionZ;
				moveEntity(motionX, motionY, motionZ);
				for(int i = 0; i < 5; i++)
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
		super.onLivingUpdate();
	}
	
	public void launchFireball(EntityLivingBase entityTarget)
	{
		EntityLargeFireball fireball = new EntityLargeFireball(worldObj, entityTarget, this.posX, this.posY + 1, this.posZ);
		worldObj.spawnEntityInWorld(fireball);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag)
	{
		super.writeEntityToNBT(tag);
		tag.setBoolean("hasStarted", start);
		tag.setInteger("Phase", phase);
		tag.setInteger("StartCounter", startCounter);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag)
	{
		super.readEntityFromNBT(tag);
		start = tag.getBoolean("hasStarted");
		phase = tag.getInteger("Phase");
		startCounter = tag.getInteger("StartCounter");
	}
	
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource sourceOfDamage, float par2)
    {
    	Entity entity = sourceOfDamage.getEntity();
    	if(entity instanceof EntityPlayer)
    	{
    		EntityPlayer player = (EntityPlayer)entity;
    		ItemStack itemstack = player.getCurrentEquippedItem();
        	if(entity != null && itemstack != null && phase > 0)
        	{
        		if(itemstack.itemID == Item.swordDiamond.itemID)
        		{
        			return super.attackEntityFrom(sourceOfDamage, 32F);
        		}
        		if(itemstack.itemID == RedstoneKit.redSword.itemID)
        		{
        			return super.attackEntityFrom(sourceOfDamage, 64F);
        		}
        	}
    	}
    	return false;
    }
}
