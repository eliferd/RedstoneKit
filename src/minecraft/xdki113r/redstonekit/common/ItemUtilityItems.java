package xdki113r.redstonekit.common;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;

public class ItemUtilityItems extends Item
{
	private String[] subtypes = {"utilityItems", "compressedRedstonePlate", "explosionDetonator", "superCompressedRedstonePlate", "compressedGlass"};
	@SideOnly(Side.CLIENT)
	private Icon[] subIcons = new Icon[5];
	
	public ItemUtilityItems(int id)
	{
		super(id);
		
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(RedstoneKit.redTab);
	}
	
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName() + "." + subtypes[MathHelper.clamp_int(stack.getItemDamage(), 0, subtypes.length)];
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister)
	{
		subIcons[0] = iconRegister.registerIcon("redstonekit:UtilityItems");
		subIcons[1] = iconRegister.registerIcon("redstonekit:CompressedRedstonePlate");
		subIcons[2] = iconRegister.registerIcon("redstonekit:ExplosionDetonator");
		subIcons[3] = iconRegister.registerIcon("redstonekit:SuperCompressedRedstonePlate");
		subIcons[4] = iconRegister.registerIcon("redstonekit:CompressedGlassPlate");
	}
	
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTabs, List list)
	{
		for(int i = 0; i < subtypes.length; i++)
		{
			list.add(new ItemStack(id, 1, i));
		}
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par1)
	{
		return subIcons[MathHelper.clamp_int(par1, 0, subtypes.length)];
	}
}
