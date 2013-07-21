package xdki113r.redstonekit.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.ResourceLocation;
import xdki113r.redstonekit.common.EntityRedstoneBoss;
import xdki113r.redstonekit.common.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRedstoneBoss extends RenderLiving{
	
	private static final ResourceLocation texture = new ResourceLocation(ModUtils.mod_id + ":RedstoneBoss.png");
		
	public ModelBase theBoss;
    private float scale;

	public RenderRedstoneBoss(ModelBase base, float f) {
		super(base, 1.0F * f);
		theBoss = this.mainModel;
	    scale = f;
	}
	
    protected void preRenderScale(EntityRedstoneBoss par1EntityGiantZombie, float par2)
    {
        GL11.glScalef(this.scale, this.scale, this.scale);
    }
    
    protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
    {
        this.preRenderScale((EntityRedstoneBoss)par1EntityLivingBase, par2);
    }
	
	public ResourceLocation getTexture(EntityRedstoneBoss boss)
	{
		return texture;
	} 
	@Override
	protected ResourceLocation func_110775_a(Entity entity) {
		// TODO Auto-generated method stub
		return getTexture((EntityRedstoneBoss)entity);
	}

}
