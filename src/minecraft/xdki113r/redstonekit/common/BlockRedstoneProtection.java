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
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockRedstoneProtection extends Block
{
	@SideOnly(Side.CLIENT)
	private Icon protectionIconFront;
	
	protected BlockRedstoneProtection(int par1)
	{
		super(par1, Material.rock);
	}
	
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return RedstoneKit.redstoneProtection.blockID;
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
		return side != metadata ? this.blockIcon : this.protectionIconFront;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon("redstone_block");
		this.protectionIconFront = par1IconRegister.registerIcon("redstonekit:RedstoneProtection");
	}
	
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5Player, int par6, float par7, float par8, float par9)
	{
		if(!par1World.isRemote)
		{
			int i = par2;
			int j = par3;
			int k = par4;
			int glass = RedstoneKit.redstoneGlass.blockID;
			int block = Block.blockRedstone.blockID;
            int size = 10;
            int size2 = 5;
            int metadata = par1World.getBlockMetadata(i, j, k);
            
            if(metadata == 2)//SOUTH
            {
            	//destruction des blocks dans la zone de la protection
                for (int i1 = 0; i1 < size; i1++)
                {
               	 for (int j1 = 0; j1 < size; j1++)
               	 {
               		for (int k1 = 0; k1 < size; k1++)
               	 	{
               		 	par1World.setBlock(i+i1,j+j1,k+k1,0);
               	 	}
                	}
                }
                
                //construction des murs, du plancher et du plafond
                for (int i1 = 0; i1 < size; i1++)
                {
                        for (int j1 = 0; j1 < size2; j1++)
                        {
                                //Walls 
                                // i == ++ k == 0
                                par1World.setBlock(i+i1,j+j1,k,block);
                                // i == ++ k == 4
                                par1World.setBlock(i+i1,j+j1,k+(size-1),block);
                                // i == 0 k == ++
                                par1World.setBlock(i,j+j1,k+i1,block);
                                // i == 4 k == ++
                                par1World.setBlock(i+(size-1),j+j1,k+i1,block);
                        }
                        for (int j1 = 0; j1 < size; j1++)
                        {
                       	 	 //Roof
                       	 	 par1World.setBlock(i+i1,j+(size/2),k+j1,block);

                       	 	 //Floor
                       	 	 par1World.setBlock(i+i1,j,k+j1,block);
                        }
                }
                
                //Door
                par1World.setBlock(i+1,j+2,k,0);
                par1World.setBlock(i+1,j+1,k,0);

                //Windows
                int offset = size % 2 == 0 ? (size / 2) - 1 : (size / 2);
                par1World.setBlock(i,((j+offset)-2),k+offset,glass);
                par1World.setBlock(i+offset,((j+offset)-2),k,glass);
                par1World.setBlock(i+(size-1),((j+offset)-2),k+offset,glass);
                par1World.setBlock(i+offset,((j+offset)-2),k+(size-1),glass);
                par1World.setBlock(i+5,j+(size/2),k+4,glass);

                //Utilities
                par1World.setBlock(i+1,j+1,k+(size-2),Block.chest.blockID);
                par1World.setBlock(i+1,j+1,k+(size-3),Block.chest.blockID);
                par1World.setBlock(i+1,j+1,k+(size-4),Block.workbench.blockID);
                par1World.setBlock(i+1,j+1,k+(size-5),Block.furnaceIdle.blockID);
                par1World.setBlock(i+1,j+1,k+(size-6),RedstoneKit.redstoneMicrowaveIdle.blockID);
                par1World.setBlock(i+(size-2), j+1, k+2, Block.bed.blockID, 0 + 8, 2);
                par1World.setBlock(i+(size-2), j+1, k+1, Block.bed.blockID, 0, 2);
                ItemDoor.placeDoorBlock(par1World, i+1, j+1, k, 1, Block.doorWood);
            }
            else if(metadata == 3)// NORTH
            {
            	//destruction des blocks dans la zone de la protection
                for (int i1 = 0; i1 < size; i1++)
                {
               	 for (int j1 = 0; j1 < size; j1++)
               	 {
               		for (int k1 = 0; k1 < size; k1++)
               	 	{
               		 	par1World.setBlock(i-i1,j+j1,k-k1,0);
               	 	}
                	}
                }
                
                //construction des murs, du plancher et du plafond
                for (int i1 = 0; i1 < size; i1++)
                {
                        for (int j1 = 0; j1 < size2; j1++)
                        {
                                //Walls 
                                // i == ++ k == 0
                                par1World.setBlock(i-i1,j+j1,k,block);
                                // i == ++ k == 4
                                par1World.setBlock(i-i1,j+j1,k-(size-1),block);
                                // i == 0 k == ++
                                par1World.setBlock(i,j+j1,k-i1,block);
                                // i == 4 k == ++
                                par1World.setBlock(i-(size-1),j+j1,k-i1,block);
                        }
                        for (int j1 = 0; j1 < size; j1++)
                        {
                       	 	 //Roof
                       	 	 par1World.setBlock(i-i1,j+(size/2),k-j1,block);

                       	 	 //Floor
                       	 	 par1World.setBlock(i-i1,j,k-j1,block);
                        }
                }
                
                //Door
                par1World.setBlock(i-1,j+2,k,0);
                par1World.setBlock(i-1,j+1,k,0);

                //Windows
                int offset = size % 2 == 0 ? (size / 2) - 1 : (size / 2);
                par1World.setBlock(i,((j+offset)-2),k-offset,glass);
                par1World.setBlock(i-offset,((j+offset)-2),k,glass);
                par1World.setBlock(i-(size-1),((j+offset)-2),k-offset,glass);
                par1World.setBlock(i-offset,((j+offset)-2),k-(size-1),glass);
                par1World.setBlock(i-5,j+(size/2),k-4,glass);

                //Utilities
                par1World.setBlock(i-1,j+1,k-(size-2),Block.chest.blockID);
                par1World.setBlock(i-1,j+1,k-(size-3),Block.chest.blockID);
                par1World.setBlock(i-1,j+1,k-(size-4),Block.workbench.blockID);
                par1World.setBlock(i-1,j+1,k-(size-5),Block.furnaceIdle.blockID);
                par1World.setBlockMetadataWithNotify(i-1, j+1, k-(size-5), 4, 2);
                par1World.setBlock(i-1,j+1,k-(size-6),RedstoneKit.redstoneMicrowaveIdle.blockID);
                par1World.setBlockMetadataWithNotify(i-1, j+1, k-(size-6), 4, 2);
                par1World.setBlock(i-(size-2), j+1, k-2, Block.bed.blockID, 2 + 8, 2);
                par1World.setBlock(i-(size-2), j+1, k-1, Block.bed.blockID, 2, 2);
                ItemDoor.placeDoorBlock(par1World, i-1, j+1, k, 3, Block.doorWood);
            }
            else if(metadata == 4) //EAST
            {
            	//destruction des blocks dans la zone de la protection
                for (int i1 = 0; i1 < size; i1++)
                {
               	 for (int j1 = 0; j1 < size; j1++)
               	 {
               		for (int k1 = 0; k1 < size; k1++)
               	 	{
               		 	par1World.setBlock(i+i1,j+j1,k-k1,0);
               	 	}
                	}
                }
                
                //construction des murs, du plancher et du plafond
                for (int i1 = 0; i1 < size; i1++)
                {
                        for (int j1 = 0; j1 < size2; j1++)
                        {
                                //Walls 
                                // i == ++ k == 0
                                par1World.setBlock(i+i1,j+j1,k,block);
                                // i == ++ k == 4
                                par1World.setBlock(i+i1,j+j1,k-(size-1),block);
                                // i == 0 k == ++
                                par1World.setBlock(i,j+j1,k-i1,block);
                                // i == 4 k == ++
                                par1World.setBlock(i+(size-1),j+j1,k-i1,block);
                        }
                        for (int j1 = 0; j1 < size; j1++)
                        {
                       	 	 //Roof
                       	 	 par1World.setBlock(i+i1,j+(size/2),k-j1,block);

                       	 	 //Floor
                       	 	 par1World.setBlock(i+i1,j,k-j1,block);
                        }
                }
                
                //Door
                par1World.setBlock(i,j+2,k-1,0);
                par1World.setBlock(i,j+1,k-1,0);

                //Windows
                int offset = size % 2 == 0 ? (size / 2) - 1 : (size / 2);
                par1World.setBlock(i,((j+offset)-2),k-offset,glass);
                par1World.setBlock(i+offset,((j+offset)-2),k,glass);
                par1World.setBlock(i+(size-1),((j+offset)-2),k-offset,glass);
                par1World.setBlock(i+offset,((j+offset)-2),k-(size-1),glass);
                par1World.setBlock(i+5,j+(size/2),k-4,glass);

                //Utilities
                par1World.setBlock(i+(size-2),j+1,k-1,Block.chest.blockID);
                par1World.setBlock(i+(size-3),j+1,k-1,Block.chest.blockID);
                par1World.setBlock(i+(size-4),j+1,k-1,Block.workbench.blockID);
                par1World.setBlock(i+(size-5), j+1, k-1, Block.furnaceIdle.blockID);
                par1World.setBlockMetadataWithNotify(i+(size-5), j+1, k-1, 2, 2);
                par1World.setBlock(i+(size-6), j+1, k-1, RedstoneKit.redstoneMicrowaveIdle.blockID);
                par1World.setBlockMetadataWithNotify(i+(size-6), j+1, k-1, 2, 2);
                par1World.setBlock(i+2, j+1, k-(size-2), Block.bed.blockID, 3 + 8, 2);
                par1World.setBlock(i+1, j+1, k-(size-2), Block.bed.blockID, 3, 2);
                ItemDoor.placeDoorBlock(par1World, i, j+1, k-1, 0, Block.doorWood);
            }
            else if(metadata == 5)//WEST
            {
            	//destruction des blocks dans la zone de la protection
                for (int i1 = 0; i1 < size; i1++)
                {
               	 for (int j1 = 0; j1 < size; j1++)
               	 {
               		for (int k1 = 0; k1 < size; k1++)
               	 	{
               		 	par1World.setBlock(i-i1,j+j1,k+k1,0);
               	 	}
                	}
                }
                
                //construction des murs, du plancher et du plafond
                for (int i1 = 0; i1 < size; i1++)
                {
                        for (int j1 = 0; j1 < size2; j1++)
                        {
                                //Walls 
                                // i == ++ k == 0
                                par1World.setBlock(i-i1,j+j1,k,block);
                                // i == ++ k == 4
                                par1World.setBlock(i-i1,j+j1,k+(size-1),block);
                                // i == 0 k == ++
                                par1World.setBlock(i,j+j1,k+i1,block);
                                // i == 4 k == ++
                                par1World.setBlock(i-(size-1),j+j1,k+i1,block);
                        }
                        for (int j1 = 0; j1 < size; j1++)
                        {
                       	 	 //Roof
                       	 	 par1World.setBlock(i-i1,j+(size/2),k+j1,block);

                       	 	 //Floor
                       	 	 par1World.setBlock(i-i1,j,k+j1,block);
                        }
                }
                
                //Door
                par1World.setBlock(i,j+2,k+1,0);
                par1World.setBlock(i,j+1,k+1,0);

                //Windows
                int offset = size % 2 == 0 ? (size / 2) - 1 : (size / 2);
                par1World.setBlock(i,((j+offset)-2),k+offset,glass);
                par1World.setBlock(i-offset,((j+offset)-2),k,glass);
                par1World.setBlock(i-(size-1),((j+offset)-2),k+offset,glass);
                par1World.setBlock(i-offset,((j+offset)-2),k+(size-1),glass);
                par1World.setBlock(i-5,j+(size/2),k+4,glass);

                //Utilities
                par1World.setBlock(i-(size-2),j+1,k+1,Block.chest.blockID);
                par1World.setBlock(i-(size-3),j+1,k+1,Block.chest.blockID);
                par1World.setBlock(i-(size-4),j+1,k+1,Block.workbench.blockID);
                par1World.setBlock(i-(size-5),j+1,k+1,Block.furnaceIdle.blockID);
                par1World.setBlockMetadataWithNotify(i-(size-5), j+1, k+1, 3, 2);
                par1World.setBlock(i-(size-6),j+1,k+1,RedstoneKit.redstoneMicrowaveIdle.blockID);
                par1World.setBlockMetadataWithNotify(i-(size-6), j+1, k+1, 3, 2);
                par1World.setBlock(i-2, j+1, k+(size-2), Block.bed.blockID, 1 + 8, 2);
                par1World.setBlock(i-1, j+1, k+(size-2), Block.bed.blockID, 1, 2);
                ItemDoor.placeDoorBlock(par1World, i, j+1, k+1, 2, Block.doorWood);
            }
		}
		
		return true;
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
	
	@SideOnly(Side.CLIENT)
	public int idPicked(World par1World, int par2, int par3, int par4)
	{
		return RedstoneKit.redstoneProtection.blockID;
	}
}
