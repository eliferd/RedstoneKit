package xdki113r.redstonekit.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityMicrowave extends TileEntity implements ISidedInventory
{
	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {2, 1};
	private static final int[] slots_sides = new int[] {1};
	
	private ItemStack[] microwaveItemStacks = new ItemStack[3];
	
	public int microwaveBurnTime;
	public int currentItemBurnTime;
	public int microwaveCookTime;
	
	private String guiDisplayName;
	
	private final int cookSpeed = 100;
	
	public int getSizeInventory()
	{
		return this.microwaveItemStacks.length;
	}
	
	public ItemStack getStackInSlot(int slot)
	{
		return this.microwaveItemStacks[slot];
	}
	
	public ItemStack decrStackSize(int par1, int par2)
	{
		if(this.microwaveItemStacks[par1] != null)
		{
			ItemStack itemstack;
			
			if(this.microwaveItemStacks[par1].stackSize <= par2)
			{
				itemstack = this.microwaveItemStacks[par1];
				this.microwaveItemStacks[par1] = null;
				return itemstack;
			}
			else
			{
				itemstack = this.microwaveItemStacks[par1].splitStack(par2);
				
				if(this.microwaveItemStacks[par1].stackSize == 0)
				{
					this.microwaveItemStacks[par1] = null;
				}
				
				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}
	
	public ItemStack getStackInSlotOnClosing(int par1)
	{
		if(this.microwaveItemStacks[par1] != null)
		{
			ItemStack itemstack = this.microwaveItemStacks[par1];
			this.microwaveItemStacks[par1] = null;
			return itemstack;
		}
		else
		{
			return null;
		}
	}
	
	public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
	{
		this.microwaveItemStacks[par1] = par2ItemStack;
		
		if(par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
		{
			par2ItemStack.stackSize = this.getInventoryStackLimit();
		}
	}
	
	public String getInvName()
	{
		return this.isInvNameLocalized() ? this.guiDisplayName : "Microwave";
	}
	
	public boolean isInvNameLocalized()
	{
		return this.guiDisplayName != null && this.guiDisplayName.length() > 0;
	}
	
	public void setDisplayName(String par1Str)
	{
		this.guiDisplayName = par1Str;
	}
	
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.microwaveItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.microwaveItemStacks.length)
            {
                this.microwaveItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.microwaveBurnTime = par1NBTTagCompound.getShort("BurnTime");
        this.microwaveCookTime = par1NBTTagCompound.getShort("CookTime");
        this.currentItemBurnTime = getItemBurnTime(this.microwaveItemStacks[1]);

        if (par1NBTTagCompound.hasKey("CustomName"))
        {
            this.guiDisplayName = par1NBTTagCompound.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short)this.microwaveBurnTime);
        par1NBTTagCompound.setShort("CookTime", (short)this.microwaveCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.microwaveItemStacks.length; ++i)
        {
            if (this.microwaveItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.microwaveItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);

        if (this.isInvNameLocalized())
        {
            par1NBTTagCompound.setString("CustomName", this.guiDisplayName);
        }
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int par1)
    {
        return this.microwaveCookTime * par1 / cookSpeed;
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int par1)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = cookSpeed;
        }

        return this.microwaveBurnTime * par1 / this.currentItemBurnTime;
    }

    public boolean isBurning()
    {
        return this.microwaveBurnTime > 0;
    }

    public void updateEntity()
    {
        boolean flag = this.microwaveBurnTime > 0;
        boolean flag1 = false;

        if (this.microwaveBurnTime > 0)
        {
            --this.microwaveBurnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.microwaveBurnTime == 0 && this.canSmelt())
            {
                this.currentItemBurnTime = this.microwaveBurnTime = getItemBurnTime(this.microwaveItemStacks[1]);

                if (this.microwaveBurnTime > 0)
                {
                    flag1 = true;

                    if (this.microwaveItemStacks[1] != null)
                    {
                        --this.microwaveItemStacks[1].stackSize;

                        if (this.microwaveItemStacks[1].stackSize == 0)
                        {
                            this.microwaveItemStacks[1] = this.microwaveItemStacks[1].getItem().getContainerItemStack(microwaveItemStacks[1]);
                        }
                    }
                }
            }

            if (this.isBurning() && this.canSmelt())
            {
                ++this.microwaveCookTime;

                if (this.microwaveCookTime == cookSpeed)
                {
                    this.microwaveCookTime = 0;
                    this.smeltItem();
                    flag1 = true;
                }
            }
            else
            {
                this.microwaveCookTime = 0;
            }

            if (flag != this.microwaveBurnTime > 0)
            {
                flag1 = true;
                BlockRedstoneMicrowave.updateMicrowaveBlockState(this.microwaveBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (flag1)
        {
            this.onInventoryChanged();
        }
    }

    private boolean canSmelt()
    {
        if (this.microwaveItemStacks[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = MicrowaveRecipes.smelting().getSmeltingResult(this.microwaveItemStacks[0]);
            if (itemstack == null) return false;
            if (this.microwaveItemStacks[2] == null) return true;
            if (!this.microwaveItemStacks[2].isItemEqual(itemstack)) return false;
            int result = microwaveItemStacks[2].stackSize + itemstack.stackSize;
            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
        }
    }

    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = MicrowaveRecipes.smelting().getSmeltingResult(this.microwaveItemStacks[0]);

            if (this.microwaveItemStacks[2] == null)
            {
                this.microwaveItemStacks[2] = itemstack.copy();
            }
            else if (this.microwaveItemStacks[2].isItemEqual(itemstack))
            {
                microwaveItemStacks[2].stackSize += itemstack.stackSize;
            }

            --this.microwaveItemStacks[0].stackSize;

            if (this.microwaveItemStacks[0].stackSize <= 0)
            {
                this.microwaveItemStacks[0] = null;
            }
        }
    }

    public static int getItemBurnTime(ItemStack par0ItemStack)
    {
        if (par0ItemStack == null)
        {
            return 0;
        }
        else
        {
            int i = par0ItemStack.getItem().itemID;
            Item item = par0ItemStack.getItem();

            if(i == Item.redstone.itemID) return 100;
            if(i == Block.blockRedstone.blockID) return 900;
            return GameRegistry.getFuelValue(par0ItemStack);
        }
    }

    public static boolean isItemFuel(ItemStack par0ItemStack)
    {
        return getItemBurnTime(par0ItemStack) > 0;
    }

    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openChest() {}

    public void closeChest() {}

    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return par1 == 2 ? false : (par1 == 1 ? isItemFuel(par2ItemStack) : true);
    }

    public int[] getAccessibleSlotsFromSide(int par1)
    {
        return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
    }

    public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return this.isItemValidForSlot(par1, par2ItemStack);
    }
    
    public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3)
    {
        return par3 != 0 || par1 != 1 || par2ItemStack.itemID == Item.bucketEmpty.itemID;
    }
}
