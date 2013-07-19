package xdki113r.redstonekit.common;

import net.minecraft.entity.Entity;
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

	@Override
	public void onBossLivingUpdate()
	{
		if(!start && startCounter > 0)
		{
			startCounter--;
		}
		if(startCounter == 0)
		{
			start = true;
			phase = 1;
		}
		if(phase == 1)
		{
			//premiere phase ici, coming soon, ne pas toucher svp merci
		}
		super.onBossLivingUpdate();
	}
	
	public void onBossUpdate()
	{
		super.onBossUpdate();
	}
	
    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
    	return true;
    }
}
