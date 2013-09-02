package xdki113r.redstonekit.common;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ForgeDummyContainer;

public class TileEntityCompressor extends TileEntity implements ISidedInventory
{
    private static final int[] slots_top = new int[] {0};
    private static final int[] slots_bottom = new int[] {2, 1};
    private static final int[] slots_sides = new int[] {1};

    private ItemStack[] compressorItemStacks = new ItemStack[3];
    
    public int compressorBurnTime;

    public int currentItemBurnTime;

    public int compressorCookTime;
    private String displayName;
    
    private final int cookTime = 100;
    
    public int getSizeInventory()
    {
        return this.compressorItemStacks.length;
    }

    public ItemStack getStackInSlot(int par1)
    {
        return this.compressorItemStacks[par1];
    }

    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.compressorItemStacks[par1] != null)
        {
            ItemStack itemstack;

            if (this.compressorItemStacks[par1].stackSize <= par2)
            {
                itemstack = this.compressorItemStacks[par1];
                this.compressorItemStacks[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.compressorItemStacks[par1].splitStack(par2);

                if (this.compressorItemStacks[par1].stackSize == 0)
                {
                    this.compressorItemStacks[par1] = null;
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
        if (this.compressorItemStacks[par1] != null)
        {
            ItemStack itemstack = this.compressorItemStacks[par1];
            this.compressorItemStacks[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.compressorItemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    public String getInvName()
    {
        return this.isInvNameLocalized() ? this.displayName : "container.compressor";
    }

    public boolean isInvNameLocalized()
    {
        return this.displayName != null && this.displayName.length() > 0;
    }

    public void setGuiDisplayName(String par1Str)
    {
        this.displayName = par1Str;
    }

    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
        this.compressorItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.compressorItemStacks.length)
            {
                this.compressorItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.compressorBurnTime = par1NBTTagCompound.getShort("BurnTime");
        this.compressorCookTime = par1NBTTagCompound.getShort("CookTime");
        this.currentItemBurnTime = getItemBurnTime(this.compressorItemStacks[1]);

        if (par1NBTTagCompound.hasKey("CustomName"))
        {
            this.displayName = par1NBTTagCompound.getString("CustomName");
        }
    }

    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("BurnTime", (short)this.compressorBurnTime);
        par1NBTTagCompound.setShort("CookTime", (short)this.compressorCookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.compressorItemStacks.length; ++i)
        {
            if (this.compressorItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.compressorItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);

        if (this.isInvNameLocalized())
        {
            par1NBTTagCompound.setString("CustomName", this.displayName);
        }
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int par1)
    {
        return this.compressorCookTime * par1 / cookTime;
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int par1)
    {
        if (this.currentItemBurnTime == 0)
        {
            this.currentItemBurnTime = cookTime;
        }

        return this.compressorBurnTime * par1 / this.currentItemBurnTime;
    }

    public boolean isBurning()
    {
        return this.compressorBurnTime > 0;
    }

    public void updateEntity()
    {
        boolean flag = this.compressorBurnTime > 0;
        boolean flag1 = false;

        if (this.compressorBurnTime > 0)
        {
            --this.compressorBurnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.compressorBurnTime == 0 && this.canSmelt())
            {
                this.currentItemBurnTime = this.compressorBurnTime = getItemBurnTime(this.compressorItemStacks[1]);

                if (this.compressorBurnTime > 0)
                {
                    flag1 = true;

                    if (this.compressorItemStacks[1] != null)
                    {
                        --this.compressorItemStacks[1].stackSize;

                        if (this.compressorItemStacks[1].stackSize == 0)
                        {
                            this.compressorItemStacks[1] = this.compressorItemStacks[1].getItem().getContainerItemStack(compressorItemStacks[1]);
                        }
                    }
                }
            }

            if (this.isBurning() && this.canSmelt())
            {
                ++this.compressorCookTime;

                if (this.compressorCookTime == cookTime)
                {
                    this.compressorCookTime = 0;
                    this.smeltItem();
                    flag1 = true;
                }
            }
            else
            {
                this.compressorCookTime = 0;
            }

            if (flag != this.compressorBurnTime > 0)
            {
                flag1 = true;
            }
        }

        if (flag1)
        {
            this.onInventoryChanged();
        }
    }

    private boolean canSmelt()
    {
        if (this.compressorItemStacks[0] == null)
        {
            return false;
        }
        else
        {
            ItemStack itemstack = CompressorRecipes.smelting().getSmeltingResult(this.compressorItemStacks[0]);
            if (itemstack == null) return false;
            if (this.compressorItemStacks[0].stackSize < CompressorRecipes.smelting().getAmount(this.compressorItemStacks[0])) return false;
            if (this.compressorItemStacks[2] == null) return true;
            if (!this.compressorItemStacks[2].isItemEqual(itemstack)) return false;
            int result = compressorItemStacks[2].stackSize + itemstack.stackSize;
            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
        }
    }

    public void smeltItem()
    {
        if (this.canSmelt())
        {
            ItemStack itemstack = CompressorRecipes.smelting().getSmeltingResult(this.compressorItemStacks[0]);

            if (this.compressorItemStacks[2] == null)
            {
                this.compressorItemStacks[2] = itemstack.copy();
            }
            else if (this.compressorItemStacks[2].isItemEqual(itemstack))
            {
                compressorItemStacks[2].stackSize += itemstack.stackSize;
            }

            this.compressorItemStacks[0].stackSize -= CompressorRecipes.smelting().getAmount(this.compressorItemStacks[0]);

            if (this.compressorItemStacks[0].stackSize <= 0)
            {
                this.compressorItemStacks[0] = null;
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
            
            if (i == Item.redstone.itemID) return 100;
            if (i == Block.blockRedstone.blockID) return 900;
            return 0;
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
