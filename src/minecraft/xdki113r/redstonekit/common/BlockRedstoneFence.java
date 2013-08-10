package xdki113r.redstonekit.common;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneFence extends BlockFence
{
	private boolean isActive;
	
	public BlockRedstoneFence(int par1, boolean active)
	{
		super(par1, "redstone_block", Material.rock);
		this.isActive = active;
	}
	
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return RedstoneKit.redstoneFenceIdle.blockID;
	}
	
	public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return RedstoneKit.redstoneFenceIdle.blockID;
	}
	
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
    {
        boolean flag = this.canConnectFenceTo(par1World, par2, par3, par4 - 1);
        boolean flag1 = this.canConnectFenceTo(par1World, par2, par3, par4 + 1);
        boolean flag2 = this.canConnectFenceTo(par1World, par2 - 1, par3, par4);
        boolean flag3 = this.canConnectFenceTo(par1World, par2 + 1, par3, par4);
        float f = 0.375F;
        float f1 = 0.625F;
        float f2 = 0.375F;
        float f3 = 0.625F;

        if (flag)
        {
            f2 = 0.0F;
        }

        if (flag1)
        {
            f3 = 1.0F;
        }

        if (flag || flag1)
        {
            this.setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }

        f2 = 0.375F;
        f3 = 0.625F;

        if (flag2)
        {
            f = 0.0F;
        }

        if (flag3)
        {
            f1 = 1.0F;
        }

        if (flag2 || flag3 || !flag && !flag1)
        {
            this.setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
            super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
        }

        if (flag)
        {
            f2 = 0.0F;
        }

        if (flag1)
        {
            f3 = 1.0F;
        }

        this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        boolean flag = this.canConnectFenceTo(par1IBlockAccess, par2, par3, par4 - 1);
        boolean flag1 = this.canConnectFenceTo(par1IBlockAccess, par2, par3, par4 + 1);
        boolean flag2 = this.canConnectFenceTo(par1IBlockAccess, par2 - 1, par3, par4);
        boolean flag3 = this.canConnectFenceTo(par1IBlockAccess, par2 + 1, par3, par4);
        float f = 0.375F;
        float f1 = 0.625F;
        float f2 = 0.375F;
        float f3 = 0.625F;

        if (flag)
        {
            f2 = 0.0F;
        }

        if (flag1)
        {
            f3 = 1.0F;
        }

        if (flag2)
        {
            f = 0.0F;
        }

        if (flag3)
        {
            f1 = 1.0F;
        }

        this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return false;
    }
    
    public int getRenderType()
    {
        return 11;
    }
    
    public boolean canConnectFenceTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int l = par1IBlockAccess.getBlockId(par2, par3, par4);

        if (l != this.blockID && l != Block.fenceGate.blockID)
        {
            Block block = Block.blocksList[l];
            return block != null && block.blockMaterial.isOpaque() && block.renderAsNormalBlock() ? block.blockMaterial != Material.pumpkin : false;
        }
        else
        {
            return true;
        }
    }

    public static boolean isIdAFence(int par0)
    {
        return par0 == Block.fence.blockID || par0 == Block.netherFence.blockID;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return true;
    }
	
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if(!par1World.isRemote && this.isActive && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
		{
			par1World.setBlock(par2, par3, par4, RedstoneKit.redstoneFenceIdle.blockID, 0, 2);
		}
	}
	
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if(par1World.isRemote)
			return;
		if(par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) && !this.isActive)
		{
			par1World.setBlock(par2, par3, par4, RedstoneKit.redstoneFenceActive.blockID, 0, 2);
		}
		else if(!par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) && this.isActive)
		{
			par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 4);
		}
	}
	
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		if(par1World.isRemote)
			return;
		if(par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) && !this.isActive)
		{
			par1World.setBlock(par2, par3, par4, RedstoneKit.redstoneFenceActive.blockID, 0, 2);
		}
		else if(!par1World.isBlockIndirectlyGettingPowered(par2, par3, par4) && this.isActive)
		{
			par1World.scheduleBlockUpdate(par2, par3, par4, this.blockID, 4);
		}
	}
	
	public void onEntityCollidedWithBlock(World world, int par3, int par4, int par5, Entity entity) {
		if(this.isActive) {
			if(entity instanceof EntityLiving) {
				entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLiving)(entity)), 2);
			} else if(entity instanceof EntityItem) {
				((EntityItem)(entity)).setDead();
			}
		}
	}
}