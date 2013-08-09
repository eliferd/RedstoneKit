package xdki113r.redstonekit.common;

import java.util.Random;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockRedstoneMicrowave extends BlockContainer
{

	private final Random microwaveRand = new Random();
	
	private final boolean active;
	
	private static boolean keepMicrowaveInventory;
	
	@SideOnly(Side.CLIENT)
	private Icon microwaveIcon;
	@SideOnly(Side.CLIENT)
	private Icon microwaveIconFront;
	
	protected BlockRedstoneMicrowave(int par1, Material par2Material, boolean isActive)
	{
		super(par1, par2Material);
		this.active = isActive;
	}
	
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return RedstoneKit.redstoneMicrowaveIdle.blockID;
	}
	
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);
		this.setDefaultDirection(par1World, par2, par3, par4);
	}
	
	private void setDefaultDirection(World par1World, int par2, int par3, int par4)
	{
		if(!par1World.isRemote)
		{
			int l = par1World.getBlockId(par2, par3, par4-1);
			int i1 = par1World.getBlockId(par2, par3, par4+1);
			int j1 = par1World.getBlockId(par2-1, par3, par4);
			int k1 = par1World.getBlockId(par2+1, par3, par4);
			byte b0 = 3;
			
			if(Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
			{
				b0 = 3;
			}
			if(Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
            {
                b0 = 2;
            }
            if(Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
            {
                b0 = 5;
            }
            if(Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
            {
                b0 = 4;
            }
            
            par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata)
	{
		return side != metadata ? this.blockIcon : this.microwaveIconFront;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon("redstonekit:MicrowaveSides");
		this.microwaveIconFront = par1IconRegister.registerIcon(this.active ? "redstonekit:MicrowaveFrontOn" : "redstonekit:MicrowaveFront");
	}
	
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5Player, int par6, float par7, float par8, float par9)
	{
		if(par1World.isRemote)
		{
			return true;
		}
		else
		{
			TileEntityMicrowave tileentitymicrowave = (TileEntityMicrowave)par1World.getBlockTileEntity(par2, par3, par4);
			
			if(tileentitymicrowave != null)
			{
				FMLClientHandler.instance().getClient().displayGuiScreen(new GUIMicrowave(par5Player.inventory, tileentitymicrowave));
			}
			
			return true;
		}
	}
	
	public static void updateMicrowaveBlockState(boolean par0, World par1World, int par2, int par3, int par4)
	{
		int l = par1World.getBlockMetadata(par2, par3, par4);
		TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);
		keepMicrowaveInventory = true;
		
		if(par0)
		{
			par1World.setBlock(par2, par3, par4, RedstoneKit.redstoneMicrowaveActive.blockID);
		}
		else
		{
			par1World.setBlock(par2, par3, par4, RedstoneKit.redstoneMicrowaveIdle.blockID);
		}
		
		keepMicrowaveInventory = false;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
		
		if(tileentity != null)
		{
			tileentity.validate();
			par1World.setBlockTileEntity(par2, par3, par4, tileentity);
		}
	}

	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityMicrowave();
	}
	
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5Entity, ItemStack par6ItemStack)
	{
		int l = MathHelper.floor_double((double)(par5Entity.rotationYaw * 4.0F / 360.0F) + 0.3D) & 3;
		
		if(l == 0)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
		}
		
		if(l == 1)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
		}
		
		if(l == 2)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
		}
		
		if(l == 3)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
		}
		
		if(par6ItemStack.hasDisplayName())
		{
			((TileEntityMicrowave)par1World.getBlockTileEntity(par2, par3, par4)).setGuiDisplayName(par6ItemStack.getDisplayName());
		}
	}
	
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		if(!keepMicrowaveInventory)
		{
			TileEntityMicrowave tileentitymicrowave = (TileEntityMicrowave)par1World.getBlockTileEntity(par2, par3, par4);
			
			if(tileentitymicrowave != null)
			{
				for(int j1 = 0; j1 < tileentitymicrowave.getSizeInventory(); ++j1)
				{
					ItemStack itemstack = tileentitymicrowave.getStackInSlot(j1);
					
					if(itemstack != null)
					{
						float f = this.microwaveRand.nextFloat() * 0.8F + 0.1F;
						float f1 = this.microwaveRand.nextFloat() * 0.8F + 0.1F;
						float f2 = this.microwaveRand.nextFloat() * 0.8F + 0.1F;
						
						while(itemstack.stackSize > 0)
						{
							int k1 = this.microwaveRand.nextInt(21) + 10;
							
							if(k1 > itemstack.stackSize)
							{
								k1 = itemstack.stackSize;
							}
							
							itemstack.stackSize -= k1;
							EntityItem entityitem = new EntityItem(par1World, (double)((float)par2+f), (double)((float)par3+f1), (double)((float)par4+f2), new ItemStack(itemstack.itemID, k1, itemstack.getItemDamage()));
							
							if(itemstack.hasTagCompound())
							{
								entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
							}
							
							float f3 = 0.05F;
							entityitem.motionX = (double)((float)this.microwaveRand.nextGaussian() * f3);
							entityitem.motionY = (double)((float)this.microwaveRand.nextGaussian() * f3 + 0.2F);
							entityitem.motionZ = (double)((float)this.microwaveRand.nextGaussian() * f3);
							par1World.spawnEntityInWorld(entityitem);
						}
					}
				}
				
				par1World.func_96440_m(par2, par3, par4, par5);
			}
		}
		
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}
	
	public boolean hasComparatorInputOverride()
	{
		return true;
	}
	
	public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		return Container.calcRedstoneFromInventory((IInventory)par1World.getBlockTileEntity(par2, par3, par4));
	}
	
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return RedstoneKit.redstoneMicrowaveIdle.blockID;
	}
}
