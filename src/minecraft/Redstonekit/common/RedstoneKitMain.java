package Redstonekit.common;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import Redstonekit.client.RedPlayerTracker;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = RedstoneKitMain.MODID, name = RedstoneKitMain.MODNAME, version = RedstoneKitMain.MODVERSION)
@NetworkMod(clientSideRequired=true, serverSideRequired=false)

public class RedstoneKitMain {

	public static final String MODID = "redkit";
	public static final String MODNAME = "Redstone Kit";
	public static final String MODVERSION = "1.0";
	public static boolean modLoaded;
	@Instance(MODID)
	public static RedstoneKitMain instance;
	
	@SidedProxy(clientSide = "Redstonekit.client.ClientProxy", serverSide = "Redstonekit.common.CommonProxy")
	public static CommonProxy proxy;
	public static Logger logger = Logger.getLogger("RedstoneKit");
    public static CreativeTabs redCreativeTab = new RedCreativeTab("redCreativeTab");
	
    public static int itemsID;
    public static int blocksID;
    
    
    public static Item rafinedRedIngot,
    				   redstoneIngot,
    				   redstoneStick;
    
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		modLoaded = false;
    	try{
        	MinecraftForge.EVENT_BUS.register(new RedstoneKit_EventSounds());
    	}catch(RuntimeException e)
    	{
    		logger.info("Unable to activate sounds.");
    		e.printStackTrace();
    	}
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		try 
		{
			config.load();
			blocksID = config.getBlock("All blocks ID start with", 1000).getInt();
			itemsID = config.getItem("All items ID start with", 20030).getInt();
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
		
		GameRegistry.registerPlayerTracker(new RedPlayerTracker());
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
	public static Item addItem(int id, CreativeTabs tab, String textureName, String itemName)
	{
		Item item = new Item(id).setCreativeTab(tab).setUnlocalizedName("RedstoneKit:" + textureName);
		GameRegistry.registerItem(item, textureName, MODID);
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
    public void addEntity(Class <? extends Entity> entityClass, String name, int id)
    {
        EntityRegistry.registerModEntity(entityClass, name, id, this, 20, 1, true);
    }
    
    public void addMobWithoutSpawn(Class <? extends EntityLiving> entityClass, String name, int id, int backgroundEggColour, int foregroundEggColour)
    {
    	EntityRegistry.registerGlobalEntityID(entityClass, name, EntityRegistry.findGlobalUniqueEntityId(), backgroundEggColour, foregroundEggColour);
    	EntityRegistry.registerModEntity(entityClass, name, id, this, 20, 1, true);
    }
    
    public void addMobInOverworld(Class <? extends EntityLiving > entityClass, String name, int id, int backgroundColor, int foregroundColor, int minSpawn, int maxSpawn, EnumCreatureType creatureType, BiomeGenBase... biomes)
    {
    	EntityRegistry.registerGlobalEntityID(entityClass, name, EntityRegistry.findGlobalUniqueEntityId(), backgroundColor, foregroundColor);
        EntityRegistry.registerModEntity(entityClass, name, id, this, 20, 1, true);
        EntityRegistry.addSpawn(entityClass, 8, minSpawn, maxSpawn, creatureType, biomes);
    }
    
    public static void addSmeltingWithMetadata(int input, int metadata, ItemStack output, float xp)
    {
        FurnaceRecipes.smelting().addSmelting(input, metadata, output, xp);
    }
}

//Add someone stuff TODO here.
//TODO
//TODO
