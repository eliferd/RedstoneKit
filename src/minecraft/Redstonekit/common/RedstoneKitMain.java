package Redstonekit.common;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = RedstoneKitMain.MODID, name = RedstoneKitMain.MODNAME, version = RedstoneKitMain.MODVERSION)

public class RedstoneKitMain {

	public static final String MODID = "redkit";
	public static final String MODNAME = "Redstone Kit";
	public static final String MODVERSION = "1.0";
	
	@Instance(MODID)
	public static RedstoneKitMain instance;
	
	@SidedProxy(clientSide = "Redstonekit.client.ClientProxy", serverSide = "Redstonekit.common.CommonProxy")
	public static CommonProxy proxy;
	public static Logger logger = Logger.getLogger("RedstoneKit");
    public static CreativeTabs redCreativeTab = new RedCreativeTab("redCreativeTab");
	
    
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		//TODO Config file
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		try 
		{
			
		} finally 
		{
			if(config.hasChanged())
			{
				config.save();
			}
		}
	}
	
	@Init
	public void init(FMLInitializationEvent event)
	{
		
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
	public static Item addItem(int id, CreativeTabs tab, String textureName, String itemName)
	{
		Item item = new Item(id).setCreativeTab(tab).setUnlocalizedName("RedstoneKit:" + textureName);
		LanguageRegistry.addName(item, itemName);
		return item;
	}
	
	public static Block addBlock(int id, Material material, CreativeTabs tab, String textureName, String blockName)
	{
		Block block = new Block(id, material).setCreativeTab(tab).setUnlocalizedName("RedstoneKit:" + textureName);
		GameRegistry.registerBlock(block, textureName);
		LanguageRegistry.addName(block, blockName);
		return block;
	}
	
}
