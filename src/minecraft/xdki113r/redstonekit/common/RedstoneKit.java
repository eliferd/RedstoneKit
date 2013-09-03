package xdki113r.redstonekit.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import xdki113r.redstonekit.client.RedSoundEvent;
import xdki113r.redstonekit.client.RedstoneKitPlayerTracker;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = ModUtils.mod_id, name = ModUtils.mod_name, version = ModUtils.mod_version)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class RedstoneKit
{
	@SidedProxy(clientSide = "xdki113r.redstonekit.client.ClientProxy", serverSide = "xdki113r.redstonekit.common.CommonProxy")
	public static CommonProxy proxy;

	@Instance("RedstoneKit")
	public static RedstoneKit instance;
//  TODO prankstone
	public static Item redstoneGun, redstoneIngot, redstoneRafinedIngot,
			redstoneBullet, redstoneGrenade, redstoneHelmet,
			redstoneChestplate, redstoneLeggings, redstoneBoots, redPick,
			redAxe, redShovel, redHoe, redSword, redstoneStick, utilityItem;
	// redstoneStick is for project in future -> DO NOT USE AS TOOLS' STICKS
	public static Block redstoneGlass, redstoneProtection, redstoneMobHead,
			redstoneFenceIdle, redstoneFenceActive, redstoneMicrowaveIdle,
			redstoneMicrowaveActive, redstonePoweredBlockIdle,
			redstonePoweredBlockActive, redstoneCompressor;

	public int redstoneGunID, redstoneIngotID, redstoneRafinedIngotID,
			redstoneBulletID, redstoneGrenadeID, redstoneHelmetID,
			redstoneChestplateID, redstoneLeggingsID, redstoneBootsID,
			redPickID, redAxeID, redShovelID, redHoeID, redSwordID,
			redstoneStickID, utilityItemsID, redstoneGlassID, redstoneProtectionID,
			redstoneMobHeadID, redstoneFenceIdleID, redstoneFenceActiveID,
			redstoneMicrowaveIdleID, redstoneMicrowaveActiveID,
			redstonePoweredBlockIdleID, redstonePoweredBlockActiveID, redstoneCompressorID;
	
	static EnumArmorMaterial RedstoneArmor = EnumHelper.addArmorMaterial("Redstone", 35, new int[]{ 4, 10, 8, 5 }, 100);
	static EnumToolMaterial RedstoneTool = EnumHelper.addToolMaterial("Redstone", 25, 168, 13F, 1, 5);
	
	public static Achievement gettingRedstone, craftingMicrowave, craftingRedGlass, craftingRedGrenade, craftingRedPick, smeltingRedstone;
	
	public int redstoneBulletEntityID;

	public static int redstoneBulletDamage;

	public String microwaveTileEntityID = "Microwave", compressorTileEntityID = "Compressor";

	public static boolean modLoaded;
	
	// for the config ? :D by default I turn this to true -elias
	public static boolean grenadeExplode = true;

	public static CreativeTabs redTab = new RedstoneKitCreativeTabs("redKitTab");

	public static boolean redstoneHelmetEquipped, redstoneChestplateEquipped, redstoneLeggingsEquipped, redstoneBootsEquipped;
	
	@EventHandler
	public void preLoad(FMLPreInitializationEvent event)
	{
		modLoaded = false;
		Configuration cfg = new RedstoneKitConfiguration(event.getSuggestedConfigurationFile());
		try
		{
			cfg.load();

			redstoneBulletEntityID = cfg.get(cfg.CATEGORY_GENERAL, "Redstone Bullet Entity ID", 50, "Entity ID for compatibility purposes. Default=50").getInt();
			
			redstoneBulletDamage = cfg.get(cfg.CATEGORY_GENERAL, "Redstone Bullet Damage", 5, "Damage of the bullet, 1 = 0.5 hearts. Default=5").getInt();
			
			grenadeExplode = cfg.get(cfg.CATEGORY_GENERAL, "Grenade Explosion Drops", true, "If the redstone grenade should drop. \"true\" will make them drop, false won't. Default=true").getBoolean(true);
			
			redstoneGunID = cfg.getItem("Redstone Shotgun Item ID", 12500).getInt();
			redstoneIngotID = cfg.getItem("Redstone Ingot Item ID", 12501).getInt();
			redstoneRafinedIngotID = cfg.getItem("Redstone Rafined Ingot Item ID", 12502).getInt();
			redstoneBulletID = cfg.getItem("Redstone Bullet Item ID", 12503).getInt();
			redstoneGrenadeID = cfg.getItem("Redstone Grenade Item ID", 12504).getInt();
			redstoneHelmetID = cfg.getItem("Redstone Helmet Item ID", 12505).getInt();
			redstoneChestplateID = cfg.getItem("Redstone Chestplate Item ID", 12506).getInt();
			redstoneLeggingsID = cfg.getItem("Redstone Leggings Item ID", 12507).getInt();
			redstoneBootsID = cfg.getItem("Redstone Boots Item ID", 12508).getInt();
			redPickID = cfg.getItem("Redstone Pickaxe Item ID", 12509).getInt();
			redAxeID = cfg.getItem("Redstone Axe Item ID", 12510).getInt();
			redShovelID = cfg.getItem("Redstone Shovel Item ID", 12511).getInt();
			redHoeID = cfg.getItem("Redstone Hoe Item ID", 12512).getInt();
			redSwordID = cfg.getItem("Redstone Sword Item ID", 12513).getInt();
			redstoneStickID = cfg.getItem("Redstone Stick Item ID", 12514).getInt();
			utilityItemsID = cfg.getItem("Utility items", 12515, "Multiple on the same ID").getInt();

			redstoneGlassID = cfg.getBlock("Redstone Glass Block ID", 1200).getInt();
			redstoneProtectionID = cfg.getBlock("Redstone Protection Block ID", 1201).getInt();
			redstoneMobHeadID = cfg.getBlock("Redstone MobHead Block ID", 1202).getInt();
			redstoneFenceIdleID = cfg.getBlock("Redstone FenceIdle Block ID", 1203).getInt();
			redstoneFenceActiveID = cfg.getBlock("Redstone FenceActive Block ID", 1204).getInt();
			redstoneMicrowaveIdleID = cfg.getBlock("Redstone MicrowaveIdle Block ID", 1205).getInt();
			redstoneMicrowaveActiveID = cfg.getBlock("Redstone MicrowaveActive Block ID", 1206).getInt();
			redstonePoweredBlockIdleID = cfg.getBlock("Redstone PoweredBlockIdle Block ID", 1207).getInt();
			redstonePoweredBlockActiveID = cfg.getBlock("Redstone PoweredBlockActive Block ID", 1208).getInt();
			redstoneCompressorID = cfg.getBlock("Redstone Compressor Block ID", 1209).getInt();
		} finally
		{
			if(cfg.hasChanged())
			{
				cfg.save();
			}
		}
		
		if(event.getSide().isClient())
		{
			MinecraftForge.EVENT_BUS.register(new RedSoundEvent());
		}
		
		redstoneGlass = new BlockRedstoneGlass(redstoneGlassID, Material.glass).setCreativeTab(redTab).setStepSound(Block.soundGlassFootstep).setLightValue(1F).setLightOpacity(0).setHardness(0.3F).setUnlocalizedName("redGlass");
		redstoneProtection = new BlockRedstoneProtection(redstoneProtectionID).setCreativeTab(redTab).setStepSound(Block.soundStoneFootstep).setHardness(5F).setUnlocalizedName("redProtection");
		redstoneMobHead = new Block/* RedstoneMobHead */(redstoneMobHeadID, Material.rock).setCreativeTab(redTab).setStepSound(Block.soundStoneFootstep).setHardness(1F).setUnlocalizedName("redSkull");
		redstoneFenceIdle = new BlockRedstoneFence(redstoneFenceIdleID, false).setCreativeTab(redTab).setStepSound(Block.soundWoodFootstep).setHardness(0.5F).setUnlocalizedName("redFenceIdle").func_111022_d("redstone_block");
		redstoneFenceActive = new BlockRedstoneFence(redstoneFenceActiveID, true).setCreativeTab(null).setStepSound(Block.soundWoodFootstep).setHardness(0.5F).setLightValue(0.2F).setUnlocalizedName("redFenceActive").func_111022_d("redstone_block");
		redstoneMicrowaveIdle = new BlockRedstoneMicrowave(redstoneMicrowaveIdleID, false).setCreativeTab(redTab).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("redMicrowaveIdle");
		redstoneMicrowaveActive = new BlockRedstoneMicrowave(redstoneMicrowaveActiveID, true).setCreativeTab(null).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setLightValue(0.3F).setUnlocalizedName("redMicrowaveActive");
		redstonePoweredBlockIdle = new BlockRedstonePoweredBlock(redstonePoweredBlockIdleID, Material.rock, false).setCreativeTab(redTab).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("redPowerBlockIdle").func_111022_d("redstone_block");
		redstonePoweredBlockActive = new BlockRedstonePoweredBlock(redstonePoweredBlockActiveID, Material.rock, true).setCreativeTab(null).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setLightValue(0.7167F).setUnlocalizedName("redPowerBlockActive").func_111022_d("redstone_block");
		redstoneCompressor = new BlockRedstoneCompressor(redstoneCompressorID, Material.rock).setCreativeTab(redTab).setHardness(3.5F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("compressor");
		
		redstoneIngot = new Item(redstoneIngotID).setCreativeTab(redTab).setUnlocalizedName("redIngot").func_111206_d("redstonekit:RedstoneIngot");
		redstoneRafinedIngot = new ItemRafinedRedstoneIngot(redstoneRafinedIngotID).setCreativeTab(redTab).setUnlocalizedName("redstoneRafinedIngot").func_111206_d("redstonekit:RafinedRedIngot");
		redstoneBullet = new Item(redstoneBulletID).setCreativeTab(redTab).setUnlocalizedName("redBullet").func_111206_d("redstonekit:RedGunBullNorm");
		redstoneGun = new ItemRedstoneGun(redstoneGunID).setCreativeTab(redTab).setMaxStackSize(1).setMaxDamage(127).setUnlocalizedName("redGun").func_111206_d("redstonekit:RedGun").setFull3D();
		redstoneGrenade = new ItemRedstoneGrenade(redstoneGrenadeID).setCreativeTab(redTab).setMaxStackSize(16).setUnlocalizedName("redGrenade").func_111206_d("redstonekit:RedGrenade").setFull3D();
		redstoneStick = new Item(redstoneStickID).setCreativeTab(redTab).setUnlocalizedName("redStick").func_111206_d("redstonekit:RedstoneStick");
		redstoneHelmet = new ItemRedstoneArmor(redstoneHelmetID, RedstoneArmor, 0, 0).setCreativeTab(redTab).setUnlocalizedName("redHelmet").func_111206_d("redstonekit:Helmet");
		redstoneChestplate = new ItemRedstoneArmor(redstoneChestplateID, RedstoneArmor, 0, 1).setCreativeTab(redTab).setUnlocalizedName("redChestplate").func_111206_d("redstonekit:Chest");
		redstoneLeggings = new ItemRedstoneArmor(redstoneLeggingsID, RedstoneArmor, 0, 2).setCreativeTab(redTab).setUnlocalizedName("redLeggings").func_111206_d("redstonekit:Legs");
		redstoneBoots = new ItemRedstoneArmor(redstoneBootsID, RedstoneArmor, 0, 3).setCreativeTab(redTab).setUnlocalizedName("redBoots").func_111206_d("redstonekit:Boots");
		redPick = new ItemPickaxe(redPickID, RedstoneTool).setCreativeTab(redTab).setUnlocalizedName("redPick").func_111206_d("redstonekit:RedstonePickaxe");
		redAxe = new ItemAxe(redAxeID, RedstoneTool).setCreativeTab(redTab).setUnlocalizedName("redAxe").func_111206_d("redstonekit:RedstoneAxe");
		redShovel = new ItemSpade(redShovelID, RedstoneTool).setCreativeTab(redTab).setUnlocalizedName("redShovel").func_111206_d("redstonekit:RedstoneShovel");
		redHoe = new ItemHoe(redHoeID, RedstoneTool).setCreativeTab(redTab).setUnlocalizedName("redHoe").func_111206_d("redstonekit:RedstoneHoe");
		redSword = new ItemSword(redSwordID, RedstoneTool).setCreativeTab(redTab).setUnlocalizedName("redSword").func_111206_d("redstonekit:RedstoneSword");
		
		utilityItem = new ItemUtilityItems(utilityItemsID).setCreativeTab(redTab).setUnlocalizedName("utilityItems");
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		registerBlocks();
		registerItems();
		registerCrafts();
		
		initAchievements();
		
		GameRegistry.registerCraftingHandler(new RedKitCraftingHandler());
		GameRegistry.registerPickupHandler(new RedKitPickupHandler());
		
		GameRegistry.registerTileEntity(TileEntityMicrowave.class, microwaveTileEntityID);
		GameRegistry.registerTileEntity(TileEntityCompressor.class, compressorTileEntityID);
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		GameRegistry.registerPlayerTracker(new RedstoneKitPlayerTracker());
	}

	@EventHandler
	public void postLoad(FMLPostInitializationEvent event)
	{
		proxy.addNonMobEntity(EntityRedstoneBull.class, "RedstoneBullet", 500, this, 40, 1, true);
		proxy.addMobEntity(EntityRedstoneBoss.class, "RedstoneBoss", 501, this, 40, 1, true, 0, 0, 1, 1, 1, EnumCreatureType.monster);
		proxy.addNonMobEntity(EntityRedGrenade.class, "RedstoneGrenade", 502, this, 40, 1, true);
		proxy.render();
		TickRegistry.registerTickHandler(new TickCommonHandler(), Side.SERVER);
	}
	
	public void registerBlocks()
	{
		GameRegistry.registerBlock(redstoneGlass, "redGlass");
		GameRegistry.registerBlock(redstoneMicrowaveIdle, "redMicrowaveIdle");
		GameRegistry.registerBlock(redstoneMicrowaveActive, "redMicrowaveActive");
		GameRegistry.registerBlock(redstonePoweredBlockIdle, "redPowerBlockIdle");
		GameRegistry.registerBlock(redstonePoweredBlockActive, "redPowerBlockActive");
		GameRegistry.registerBlock(redstoneProtection, "redProtection");
		GameRegistry.registerBlock(redstoneFenceIdle, "redFenceIdle");
		GameRegistry.registerBlock(redstoneFenceActive, "redFenceActive");
		GameRegistry.registerBlock(redstoneCompressor, "compressor");
	}
	
	public void registerItems()
	{
		GameRegistry.registerItem(redstoneHelmet, "redHelmet", "RedstoneKit");
		GameRegistry.registerItem(redstoneChestplate, "redChestplate", "RedstoneKit");
		GameRegistry.registerItem(redstoneLeggings, "redLeggings", "RedstoneKit");
		GameRegistry.registerItem(redstoneBoots, "redBoots", "RedstoneKit");
		GameRegistry.registerItem(redAxe, "redAxe", "RedstoneKit");
		GameRegistry.registerItem(redShovel, "redShovel", "RedstoneKit");
		GameRegistry.registerItem(redPick, "redPick", "RedstoneKit");
		GameRegistry.registerItem(redSword, "redSword", "RedstoneKit");
		GameRegistry.registerItem(redHoe, "redHoe", "RedstoneKit");
		GameRegistry.registerItem(redstoneGrenade, redstoneGrenade.getUnlocalizedName(), "RedstoneKit");
		GameRegistry.registerItem(utilityItem, "utilityItems", "RedstoneKit");
	}
	
	public void registerCrafts()
	{
	   GameRegistry.addShapedRecipe(new ItemStack(redPick, 1), new Object[]{
			"RRR", " S ", " S ", Character.valueOf('R'), redstoneIngot, Character.valueOf('S'), Item.stick
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redAxe, 1), new Object[]{
			"RR ", "RS ", " S ", Character.valueOf('R'), redstoneIngot, Character.valueOf('S'), Item.stick
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redShovel, 1), new Object[]{
			" R ", " S ", " S ", Character.valueOf('R'), redstoneIngot, Character.valueOf('S'), Item.stick
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redHoe, 1), new Object[]{
			" RR", " S ", " S ", Character.valueOf('R'), redstoneIngot, Character.valueOf('S'), Item.stick
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redSword, 1), new Object[]{
			" R ", " R ", " S ", Character.valueOf('R'), redstoneIngot, Character.valueOf('S'), Item.stick
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneProtection, 1), new Object []{
			"IRI", "GEU", "IRI", Character.valueOf('E'), new ItemStack(utilityItem, 1, 2), Character.valueOf('U'), new ItemStack(utilityItem, 1, 0), Character.valueOf('G'), new ItemStack(utilityItem, 1, 4), Character.valueOf('I'), new ItemStack(utilityItem, 1, 3), Character.valueOf('R'), Block.blockRedstone
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneGlass, 8), new Object []{
			"RRR", "RGR", "RRR", Character.valueOf('R'), Block.blockRedstone, Character.valueOf('G'), Block.glass
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstonePoweredBlockIdle, 8), new Object []{
			"RRR", "RTR", "RRR", Character.valueOf('R'), Block.blockRedstone, Character.valueOf('T'), Block.torchRedstoneActive
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneChestplate, 1), new Object[]{
			"* *", "***", "***", Character.valueOf('*'), redstoneIngot
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneLeggings, 1), new Object[]{
			"***", "* *", "* *", Character.valueOf('*'), redstoneIngot
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneHelmet, 1), new Object[]{
			"***", "* *", Character.valueOf('*'), redstoneIngot
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneBoots, 1), new Object[]{
			"* *", "* *", Character.valueOf('*'), redstoneIngot
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneMicrowaveIdle, 1), new Object[]{
			"BBB", "BFB", "BBB", Character.valueOf('B'), Block.blockRedstone, Character.valueOf('F'), Block.furnaceIdle
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneGun, 1), new Object[]{
			"RC ", "CR ", "  S", Character.valueOf('R'), redstoneRafinedIngot, Character.valueOf('C'), new ItemStack(utilityItem, 1, 1), Character.valueOf('S'), redstoneStick
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneBullet, 5), new Object[]{
			"RRR", Character.valueOf('R'), redstoneRafinedIngot
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneGrenade, 2), new Object[]{
			"TRT", "RTR", "TRT", Character.valueOf('T'), Item.gunpowder, Character.valueOf('R'), redstoneIngot
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneFenceIdle, 2), new Object[]{
			"SSS", "SSS", Character.valueOf('S'), redstoneStick
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneStick, 1), new Object[]{
			"R", "R", Character.valueOf('R'), redstoneIngot
		});
	   GameRegistry.addShapelessRecipe(new ItemStack(utilityItem, 1, 0), new Object[]{//Utility
		   	Item.doorWood, redstoneMicrowaveIdle, Block.furnaceIdle, Item.bed, Block.chest, Block.chest, Block.workbench
	   });
	   GameRegistry.addShapelessRecipe(new ItemStack(Block.blockRedstone, 9), new Object[]{
		   	new ItemStack(utilityItem, 1, 1)
	   });
	   GameRegistry.addShapedRecipe(new ItemStack(utilityItem, 1, 2), new Object[]{
		   	" B ", "BTB", " B ", Character.valueOf('B'), Item.bucketWater, Character.valueOf('T'), Block.tnt
	   });
	   GameRegistry.addShapelessRecipe(new ItemStack(utilityItem, 9, 1), new Object[]{//336 Redstone blocks à placer
		   	new ItemStack(utilityItem, 1, 3)
	   });
	   GameRegistry.addShapelessRecipe(new ItemStack(redstoneGlass, 6), new Object[]{
		   	new ItemStack(utilityItem, 1, 4)
	   });
	   
	   GameRegistry.addSmelting(Item.redstone.itemID, new ItemStack(redstoneIngot), 2F);
	   GameRegistry.addSmelting(redstoneIngot.itemID, new ItemStack(redstoneRafinedIngot), 2F);
	}
	
	public void initAchievements() {
		gettingRedstone = new Achievement(AchievementList.achievementList.size() + 1, "gettingRedstone", 7, 3, Item.redstone, AchievementList.buildBetterPickaxe).registerAchievement();
		smeltingRedstone = new Achievement(AchievementList.achievementList.size() + 2, "smeltingRedstone", 6, 4, redstoneIngot, gettingRedstone).registerAchievement();
		craftingRedPick = new Achievement(AchievementList.achievementList.size() + 3, "craftingRedPick", 9, 4, redPick, smeltingRedstone).setSpecial().registerAchievement();
		craftingRedGrenade = new Achievement(AchievementList.achievementList.size() + 4, "craftingRedGrenade", 8, 5, redstoneGrenade, smeltingRedstone).registerAchievement();
		craftingMicrowave  = new Achievement(AchievementList.achievementList.size() + 5, "craftingMicrowave", 4, 8, redstoneMicrowaveIdle, AchievementList.buildFurnace).setSpecial().registerAchievement();
		craftingRedGlass = new Achievement(AchievementList.achievementList.size() + 6, "craftingRedGlass", 5, 6, redstoneGlass, AchievementList.buildFurnace).registerAchievement();
	}
	
	public static int getBulletDamage()
	{
		return redstoneBulletDamage;
	}

	public static boolean anotherModLoadedDetectionByID(String modID)
	{
		return Loader.isModLoaded(modID);
	}

}
