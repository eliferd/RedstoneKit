package xdki113r.redstonekit.common;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRedstoneCompressor extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	private Icon blockIconTop, blockIconFront;
	
	private Random rand = new Random();
	private boolean keepInventory;
	
	protected BlockRedstoneCompressor(int id, Material material)
	{
		super(id, material);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityCompressor();
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if(world.isRemote)
		{
			return true;
		}
		else
		{
			TileEntityCompressor compressor = (TileEntityCompressor) world.getBlockTileEntity(x, y, z);
			
			if(compressor != null)
			{
				player.openGui(RedstoneKit.instance, 0, world, x, y, z);
			}
			
			return true;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister)
	{
		blockIconTop = iconRegister.registerIcon("redstonekit:compressorTop");
		blockIconFront = iconRegister.registerIcon("redstonekit:compressorFront");
		this.blockIcon = iconRegister.registerIcon("redstonekit:compressorSides");
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata)
	{
		return side == 1 ? this.blockIconTop : (side == 0 ? this.blockIconTop : (side == metadata ? this.blockIconFront : (side == 3 && metadata == 0 ? this.blockIconFront : this.blockIcon)));
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase placer, ItemStack placed)
	{
		int l = MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360F) + 0.5D) & 3;
		
		if (l == 0)
        {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 1)
        {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (l == 2)
        {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3)
        {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
		
		if(placed.hasDisplayName())
			((TileEntityCompressor)world.getBlockTileEntity(x, y, z)).setGuiDisplayName(placed.getDisplayName());
	}
	
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!keepInventory)
        {
            TileEntityMicrowave tileentityfurnace = (TileEntityMicrowave)par1World.getBlockTileEntity(par2, par3, par4);

            if (tileentityfurnace != null)
            {
                for (int j1 = 0; j1 < tileentityfurnace.getSizeInventory(); ++j1)
                {
                    ItemStack itemstack = tileentityfurnace.getStackInSlot(j1);

                    if (itemstack != null)
                    {
                        float f = this.rand.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int k1 = this.rand.nextInt(21) + 10;

                            if (k1 > itemstack.stackSize)
                            {
                                k1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= k1;
                            EntityItem entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (double)((float)this.rand.nextGaussian() * f3);
                            entityitem.motionY = (double)((float)this.rand.nextGaussian() * f3 + 0.2F);
                            entityitem.motionZ = (double)((float)this.rand.nextGaussian() * f3);
                            par1World.spawnEntityInWorld(entityitem);
                        }
                    }
                }

                par1World.func_96440_m(par2, par3, par4, par5);
            }
        }

        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
}
