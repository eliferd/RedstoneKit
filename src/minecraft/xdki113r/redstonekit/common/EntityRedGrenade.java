package xdki113r.redstonekit.common;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
 
public class EntityRedGrenade extends EntityThrowable
{
	private EntityPlayer thrower;
	
	public EntityRedGrenade(World par1World)
    {
        super(par1World);
    }

    public EntityRedGrenade(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }
    
    public EntityRedGrenade(World par1World, EntityPlayer par2Player)
    {
        super(par1World);
        
        stopped = false;
        collided = false;
        exploded = false;
        fuse = 50;
        bounceFactor = 0.1F;
        explosionForce = 5F;
        
        this.thrower = par2Player;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        if (par1MovingObjectPosition.entityHit != null)
        {
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.thrower), 1);
            explode();
        }

        for (int var5 = 0; var5 < 8; ++var5)
        {
            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }
    
    protected boolean canTriggerWalking()
    {
        return false;
    }
    
    public void onUpdate()
    {
        if(fuse-- <= 0)
        {
            explode();
        }
        if(!(stopped) && !(exploded))
        {
                double prevVelX = motionX;
                double prevVelY = motionY;
                double prevVelZ = motionZ;
                prevPosX = posX;
                prevPosY = posY;
                prevPosZ = posZ;
                moveEntity(motionX, motionY, motionZ);
         
                boolean collided = false;
                        
                if(motionX != prevVelX)
                {
                        motionX = -prevVelX;
                        collided = true;
                }
                if(motionZ != prevVelZ)
                {
                        motionZ = -prevVelZ;
                }
         
                if(motionY != prevVelY)
                {
                        motionY = -prevVelY;
                        collided = true;
                }
                else
                {
                        motionY -= 0.2;
                }
         
                if(collided)
                {
                        motionX *= bounceFactor;
                        motionY *= bounceFactor;
                        motionZ *= bounceFactor;
                }
         
                motionX *= 0.99;
                motionY *= 0.99;
                motionZ *= 0.99;
                if(onGround && (motionX * motionX + motionY * motionY + motionZ * motionZ) < 0.02)
                {
                        stopped = true;
                        motionX = 0;
                        motionY = 0;
                        motionZ = 0;
                }
        }
    }
    
    protected void explode()
    {
        if(!exploded)
        {
        	worldObj.createExplosion(getThrower(), this.posX, this.posY, this.posZ, this.explosionForce, true);
        	exploded = true;
        }
    }
    
    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
        
    }
    
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setByte("Fuse", (byte)fuse);
    }
 
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        fuse = nbttagcompound.getByte("Fuse");
    }
    
    double bounceFactor;
    int fuse;
    boolean exploded;
    boolean collided;
    boolean stopped;
    boolean SupahNade;
    float explosionForce;
}