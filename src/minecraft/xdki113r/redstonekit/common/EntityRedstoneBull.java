package xdki113r.redstonekit.common;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityRedstoneBull extends Entity {

    private int xTile;
    private int yTile;
    private int zTile;
    private int inTile;
    public int shake;
    public EntityLivingBase shootingEntity;
    private int ticksAlive;
    private int ticksInAir;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;
	private static int bullDamage = 0;
    
	public EntityRedstoneBull(World par1World) {
		super(par1World);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        onGround = false;
        shake = 0;
        ticksInAir = 0;
        setSize(0.1F, 0.1F);
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub

	}

	@Override
    public boolean isInRangeToRenderDist(double d)
    {
        double d1 = boundingBox.getAverageEdgeLength() * 4D;
        d1 *= 64D;
        return d < d1 * d1;
    }
	
	public EntityRedstoneBull(World world, EntityLivingBase entityliving) {
        super(world);
        xTile = -1;
        yTile = -1;
        zTile = -1;
        inTile = 0;
        onGround = false;
        shake = 0;
        ticksInAir = 0;
        shootingEntity = entityliving;
        setSize(0.1F, 0.1F);
        setLocationAndAngles(entityliving.posX, entityliving.posY + (double)entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        posX -= MathHelper.cos((rotationYaw / 180F) * 3.141593F) * 0.16F;
        posY -= 0.10000000149011612D;
        posZ -= MathHelper.sin((rotationYaw / 180F) * 3.141593F) * 0.16F;
        setPosition(posX, posY, posZ);
        yOffset = 0.0F;
        float f = 0.4F;
        accelerationX = -MathHelper.sin((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        accelerationZ = MathHelper.cos((rotationYaw / 180F) * 3.141593F) * MathHelper.cos((rotationPitch / 180F) * 3.141593F) * f;
        accelerationY = -MathHelper.sin((rotationPitch / 180F) * 3.141593F) * f;
	}
	
    public void onUpdate()
    {
        super.onUpdate();
        if(shake > 0)
        {
            shake--;
        }
        if(onGround)
        {
            int i = worldObj.getBlockId(xTile, yTile, zTile);
            if(i != inTile)
            {
                onGround = false;
                motionX *= rand.nextFloat() * 0.2F;
                motionY *= rand.nextFloat() * 0.2F;
                motionZ *= rand.nextFloat() * 0.2F;
                ticksAlive = 0;
                ticksInAir = 0;
            } else
            {
                ticksAlive++;
                if(ticksAlive == 1200)
                {
                    setDead();
                }
                return;
            }
        } else
        {
            ticksInAir++;
        }
        Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
        Vec3 vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks_do_do(vec3, vec31, false, true);
        vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX, this.posY, this.posZ);
        vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (movingobjectposition != null)
        {
            vec31 = this.worldObj.getWorldVec3Pool().getVecFromPool(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }
        
        Entity entity = null;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
        double d = 0.0D;
        
        for(int j = 0; j < list.size(); j++)
        {
            Entity entity1 = (Entity)list.get(j);
            if(!entity1.canBeCollidedWith() || entity1 == shootingEntity && ticksInAir < 25)
            {
                continue;
            }
            float f2 = 0.3F;
            AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f2, f2, f2);
            MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);
            if(movingobjectposition1 == null)
            {
                continue;
            }
            double d1 = vec3.distanceTo(movingobjectposition1.hitVec);
            if(d1 < d || d == 0.0D)
            {
                entity = entity1;
                d = d1;
            }
        }
        
        if (entity != null)
        {
            movingobjectposition = new MovingObjectPosition(entity);
        }
      
        if (movingobjectposition != null && !worldObj.isRemote && movingobjectposition.entityHit != null)
        {
        	if(!movingobjectposition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, shootingEntity), getBullDamage()));
        	setDead();
        }
        posX += motionX;
        posY += motionY;
        posZ += motionZ;
        float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = (float)((Math.atan2(motionX, motionZ) * 180D) / 3.1415927410125732D);
        for(rotationPitch = (float)((Math.atan2(motionY, f) * 180D) / 3.1415927410125732D); rotationPitch - prevRotationPitch < -180F; prevRotationPitch -= 360F) { }
        for(; rotationPitch - prevRotationPitch >= 180F; prevRotationPitch += 360F) { }
        for(; rotationYaw - prevRotationYaw < -180F; prevRotationYaw -= 360F) { }
        for(; rotationYaw - prevRotationYaw >= 180F; prevRotationYaw += 360F) { }
        rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
        rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;
        float f1 = 0.95F;
        if(isInWater())
        {
            for(int k = 0; k < 4; k++)
            {
                float f3 = 0.25F;
                worldObj.spawnParticle("bubble", posX - motionX * (double)f3, posY - motionY * (double)f3, posZ - motionZ * (double)f3, motionX, motionY, motionZ);
            }

            f1 = 0.8F;
        }
        motionX += accelerationX;
        motionY += accelerationY;
        motionZ += accelerationZ;
        motionX *= f1;
        motionY *= f1;
        motionZ *= f1;
        setPosition(posX, posY, posZ);
    }
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        xTile = nbttagcompound.getShort("xTile");
        yTile = nbttagcompound.getShort("yTile");
        zTile = nbttagcompound.getShort("zTile");
        inTile = nbttagcompound.getByte("inTile") & 0xff;
        shake = nbttagcompound.getByte("shake") & 0xff;
        onGround = nbttagcompound.getByte("onGround") == 1;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound.setShort("xTile", (short)xTile);
        nbttagcompound.setShort("yTile", (short)yTile);
        nbttagcompound.setShort("zTile", (short)zTile);
        nbttagcompound.setByte("inTile", (byte)inTile);
        nbttagcompound.setByte("shake", (byte)shake);
        nbttagcompound.setByte("onGround", (byte)(onGround ? 1 : 0));
	}
	
	@Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    public float getCollisionBorderSize()
    {
        return 1.0F;
    }
    
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        setBeenAttacked();
        if(damagesource.getEntity() != null)
        {
            Vec3 vec3d = damagesource.getEntity().getLookVec();
            if(vec3d != null)
            {
                motionX = vec3d.xCoord;
                motionY = vec3d.yCoord;
                motionZ = vec3d.zCoord;
                accelerationX = motionX * 0.10000000000000001D;
                accelerationY = motionY * 0.10000000000000001D;
                accelerationZ = motionZ * 0.10000000000000001D;
            }
            return true;
        } else
        {
            return false;
        }
    }
	public static int getBullDamage()
	{
		if(bullDamage == 0)
			bullDamage = RedstoneKit.getBulletDamage();
		return bullDamage;
	}
    public float getShadowSize()
    {
        return 0.0F;
    }

}
