package Redstonekit.common;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
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
	
    public static int rafinedRedIngotID,
    				  redstoneIngotID,
    				  redstoneStickID,
   				   	  redPickID,
   				   	  redAxeID,
   				   	  redShovelID,
   				   	  redHoeID,
   				   	  redSwordID;
    
    
    public static int blocksID;
    
    
    public static Item rafinedRedIngot,
    				   redstoneIngot,
    				   redstoneStick,
    				   redPick,
    				   redAxe,
    				   redShovel,
    				   redHoe,
    				   redSword;
    				   
	static EnumToolMaterial materielRedstone = EnumHelper.addToolMaterial("materielRedstone", 150, 2634, 13F, 4, 4);
	static EnumArmorMaterial armureRedstone = EnumHelper.addArmorMaterial("armureRedstone", 35, new int[]{ 4, 10, 8, 5 }, 30);

	
	public static Item addItem(int id, String textureName, String itemName)
	{
		Item item = (new Item(id)).setCreativeTab(redCreativeTab).setUnlocalizedName("RedstoneKit:" + textureName);
		GameRegistry.registerItem(item, textureName, MODID);
		LanguageRegistry.addName(item, itemName);
		return item;
	}
	
	public static Block addBlock(int id, Material material, CreativeTabs tab, String textureName, String blockName)
	{
		Block block = (new Block(id, material)).setCreativeTab(tab).setUnlocalizedName("RedstoneKit:" + textureName);
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
    
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		// boolean a ne pas toucher !! C'est pour l'affichage de la version dans le tchat du jeu
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
			
			rafinedRedIngotID = config.getItem("Rafined Redstone Ingot ID", 20000).getInt();
		    redstoneIngotID = config.getItem("Redstone Ingot ID", 20001).getInt();
			redstoneStickID = config.getItem("Redstone Stick ID", 20002).getInt();
			redPickID = config.getItem("Redstone Pickaxe ID", 20003).getInt();
			redAxeID = config.getItem("Redstone Axe ID", 20004).getInt();
			redShovelID = config.getItem("Redstone Shovel ID", 20005).getInt();
			redHoeID = config.getItem("Redstone Hoe ID", 20006).getInt();
			redSwordID = config.getItem("Redstone Sword ID", 20007).getInt();
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
		rafinedRedIngot = addItem(rafinedRedIngotID, "RafinedRedIngot", "Rafined Redstone Ingot");
		redstoneIngot = addItem(redstoneIngotID, "RedstoneIngot", "Redstone Ingot");
		redstoneStick = addItem(redstoneStickID, "RedstoneStick", "Redstone Stick");
		redPick = new ItemPickaxe(redPickID, this.materielRedstone).setCreativeTab(redCreativeTab).setUnlocalizedName("RedstoneKit:RedstonePickaxe");
		redAxe = new ItemAxe(redAxeID, this.materielRedstone).setCreativeTab(redCreativeTab).setUnlocalizedName("RedstoneKit:RedstoneAxe");
	    redShovel = new ItemSpade(redShovelID, this.materielRedstone).setCreativeTab(redCreativeTab).setUnlocalizedName("RedstoneKit:RedstoneShovel");
		redHoe = new ItemHoe(redHoeID, this.materielRedstone).setCreativeTab(redCreativeTab).setUnlocalizedName("RedstoneKit:RedstoneHoe");
		redSword = new ItemSword(redSwordID, this.materielRedstone).setCreativeTab(redCreativeTab).setUnlocalizedName("RedstoneKit:RedstoneSword");
		GameRegistry.registerPlayerTracker(new RedPlayerTracker());
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		   GameRegistry.addRecipe(new ItemStack(Block.blockRedstone), new Object[] {
			   "XXX", "XXX", "XXX", 'X', Item.redstone
			   });
	   GameRegistry.addRecipe(new ItemStack(redPick, 1), new Object[]{
			"RRR", " S ", " S ", Character.valueOf('R'), redstoneIngot, Character.valueOf('S'), Item.stick
		});
	   GameRegistry.addRecipe(new ItemStack(redAxe, 1), new Object[]{
			"RR ", "RS ", " S ", Character.valueOf('R'), redstoneIngot, Character.valueOf('S'), Item.stick
		});
	   GameRegistry.addRecipe(new ItemStack(redShovel, 1), new Object[]{
			" R ", " S ", " S ", Character.valueOf('R'), redstoneIngot, Character.valueOf('S'), Item.stick
		});
	   GameRegistry.addRecipe(new ItemStack(redHoe, 1), new Object[]{
			" RR", " S ", " S ", Character.valueOf('R'), redstoneIngot, Character.valueOf('S'), Item.stick
		});
	   GameRegistry.addRecipe(new ItemStack(redSword, 1), new Object[]{
			" R ", " R ", " S ", Character.valueOf('R'), redstoneIngot, Character.valueOf('S'), Item.stick
		});
	   /*GameRegistry.addRecipe(new ItemStack(RedstoneProtection, 1), new Object []{
			"III", "IRI", "III", Character.valueOf('R'), Item.diamond, Character.valueOf('I'), redstoneIngot
		});
	   GameRegistry.addRecipe(new ItemStack(RedstoneGlass, 1), new Object []{
			"RRR", "RGR", "RRR", Character.valueOf('R'), Block.blockRedstone, Character.valueOf('G'), Block.glass
		});*/
	   GameRegistry.addShapelessRecipe(new ItemStack(Item.redstone, 9), new Object []{
			new ItemStack(Block.blockRedstone)
		});
	   /*GameRegistry.addRecipe(new ItemStack(RedstonePoweredBlockIdle, 1), new Object []{
			"RRR", "RTR", "RRR", Character.valueOf('R'), Block.blockRedstone, Character.valueOf('T'), Block.torchRedstoneActive
		});*/
	   
	   /**
	   GameRegistry.addRecipe(new ItemStack(plate, 1), new Object[]{
			"* *", "***", "***", Character.valueOf('*'), redstoneIngot
		});
	   GameRegistry.addRecipe(new ItemStack(leggings, 1), new Object[]{
			"***", "* *", "* *", Character.valueOf('*'), redstoneIngot
		});
	   GameRegistry.addRecipe(new ItemStack(helmet, 1), new Object[]{
			"***", "* *", Character.valueOf('*'), redstoneIngot
		});
	   GameRegistry.addRecipe(new ItemStack(boots, 1), new Object[]{
			"* *", "* *", Character.valueOf('*'), redstoneIngot
		}); */
	   
	   /*GameRegistry.addRecipe(new ItemStack(RedstoneMicroWaveIdle, 1), new Object[]{
			"BBB", "BFB", "BBB", Character.valueOf('B'), Block.blockRedstone, Character.valueOf('F'), Block.furnaceIdle
		});
	   GameRegistry.addRecipe(new ItemStack(RedstoneGun, 1), new Object[]{
			"RR ", "RR ", "  S", Character.valueOf('R'), rafinedRedIngot, Character.valueOf('S'), Item.stick
		});*/
		LanguageRegistry.instance().addStringLocalization("itemGroup.redCreativeTab", "RedstoneKit Tab");
		LanguageRegistry.addName(redPick, "Redstone Pickaxe");
		LanguageRegistry.addName(redAxe, "Redstone Axe");
		LanguageRegistry.addName(redShovel, "Redstone Shovel");
		LanguageRegistry.addName(redHoe, "Redstone Hoe");
		LanguageRegistry.addName(redSword, "Redstone Sword");
	}
}
