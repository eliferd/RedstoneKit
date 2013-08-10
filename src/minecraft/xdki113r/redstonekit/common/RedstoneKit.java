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
import cpw.mods.fml.relauncher.ReflectionHelper;

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
			redAxe, redShovel, redHoe, redSword, redstoneStick;
	// redstoneStick is for project in future -> DO NOT USE AS TOOLS' STICKS
	public static Block redstoneGlass, redstoneProtection, redstoneMobHead,
			redstoneFenceIdle, redstoneFenceActive, redstoneMicrowaveIdle,
			redstoneMicrowaveActive, redstonePoweredBlockIdle,
			redstonePoweredBlockActive;

	public int redstoneGunID, redstoneIngotID, redstoneRafinedIngotID,
			redstoneBulletID, redstoneGrenadeID, redstoneHelmetID,
			redstoneChestplateID, redstoneLeggingsID, redstoneBootsID,
			redPickID, redAxeID, redShovelID, redHoeID, redSwordID,
			redstoneStickID, redstoneGlassID, redstoneProtectionID,
			redstoneMobHeadID, redstoneFenceIdleID, redstoneFenceActiveID,
			redstoneMicrowaveIdleID, redstoneMicrowaveActiveID,
			redstonePoweredBlockIdleID, redstonePoweredBlockActiveID;
	
	static EnumArmorMaterial RedstoneArmor = EnumHelper.addArmorMaterial("Redstone", 35, new int[]{ 4, 10, 8, 5 }, 30);
	static EnumToolMaterial RedstoneTool = EnumHelper.addToolMaterial("Redstone", 150, 2634, 13F, 4, 4);
	
	public int redstoneBulletEntityID;

	public static int redstoneBulletDamage;

	public String microwaveTileEntityID = "Microwave";

	public static boolean modLoaded;

	public static CreativeTabs redTab = new RedstoneKitCreativeTabs("redKitTab");
	
	public static EntityPigZombie pZombieInstance; //for a java reflexion test, see more in preLoad

	@EventHandler
	public void preLoad(FMLPreInitializationEvent event)
	{
		modLoaded = false;
		Configuration cfg = new RedstoneKitConfiguration(event.getSuggestedConfigurationFile());
		try
		{
			cfg.load();

			Property redstoneBulletProp = cfg.get(cfg.CATEGORY_GENERAL, "Redstone Bullet Entity ID", 50);
			redstoneBulletProp.comment = "Entity ID for compatibility purposes";
			redstoneBulletEntityID = redstoneBulletProp.getInt();
			
			Property redstoneBulletDamageProp = cfg.get(cfg.CATEGORY_GENERAL, "Redstone Bullet Damage", 5);
			redstoneBulletDamageProp.comment = "Damage of the bullet, 1 = 0.5 hearts. Default=5";
			redstoneBulletDamage = redstoneBulletDamageProp.getInt();
			
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

			redstoneGlassID = cfg.getBlock("Redstone Glass Block ID", 1200).getInt();
			redstoneProtectionID = cfg.getBlock("Redstone Protection Block ID", 1201).getInt();
			redstoneMobHeadID = cfg.getBlock("Redstone MobHead Block ID", 1202).getInt();
			redstoneFenceIdleID = cfg.getBlock("Redstone FenceIdle Block ID", 1203).getInt();
			redstoneFenceActiveID = cfg.getBlock("Redstone FenceActive Block ID", 1204).getInt();
			redstoneMicrowaveIdleID = cfg.getBlock("Redstone MicrowaveIdle Block ID", 1205).getInt();
			redstoneMicrowaveActiveID = cfg.getBlock("Redstone MicrowaveActive Block ID", 1206).getInt();
			redstonePoweredBlockIdleID = cfg.getBlock("Redstone PoweredBlockIdle Block ID", 1207).getInt();
			redstonePoweredBlockActiveID = cfg.getBlock("Redstone PoweredBlockActive Block ID", 1208).getInt();
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
		
		redstoneGlass = new BlockRedstoneGlass(redstoneGlassID, Material.glass).setCreativeTab(redTab).setStepSound(Block.soundGlassFootstep).setLightValue(1F).setLightOpacity(0).setHardness(0.3F).setUnlocalizedName("redGlass").func_111022_d("redstonekit:RedstoneGlass");
		redstoneProtection = new BlockRedstoneProtection(redstoneProtectionID).setCreativeTab(redTab).setStepSound(Block.soundStoneFootstep).setHardness(5F).setUnlocalizedName("redProtection");
		redstoneMobHead = new Block/* RedstoneMobHead */(redstoneMobHeadID, Material.rock).setCreativeTab(redTab).setStepSound(Block.soundStoneFootstep).setHardness(1F).setUnlocalizedName("redSkull");
		redstoneFenceIdle = new BlockRedstoneFence(redstoneFenceIdleID, false).setCreativeTab(redTab).setStepSound(Block.soundWoodFootstep).setHardness(0.5F).setUnlocalizedName("redFenceIdle").func_111022_d("redstone_block");
		redstoneFenceActive = new BlockRedstoneFence(redstoneFenceActiveID, true).setStepSound(Block.soundWoodFootstep).setHardness(0.5F).setLightValue(0.2F).setUnlocalizedName("redFenceActive").func_111022_d("redstone_block");
		redstoneMicrowaveIdle = new BlockRedstoneMicrowave(redstoneMicrowaveIdleID, Material.rock, false).setCreativeTab(redTab).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("redMicrowaveIdle");
		redstoneMicrowaveActive = new BlockRedstoneMicrowave(redstoneMicrowaveActiveID, Material.rock, true).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setLightValue(0.3F).setUnlocalizedName("redMicrowaveActive");
		redstonePoweredBlockIdle = new BlockRedstonePoweredBlock(redstonePoweredBlockIdleID, Material.rock, false).setCreativeTab(redTab).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("redPowerBlockIdle").func_111022_d("redstone_block");
		redstonePoweredBlockActive = new BlockRedstonePoweredBlock(redstonePoweredBlockActiveID, Material.rock, true).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setLightValue(0.7167F).setUnlocalizedName("redPowerBlockActive").func_111022_d("redstone_block");

		redstoneIngot = new Item(redstoneIngotID).setCreativeTab(redTab).setUnlocalizedName("redIngot").func_111206_d("redstonekit:RedstoneIngot");
		redstoneRafinedIngot = new Item(redstoneRafinedIngotID).setCreativeTab(redTab).setUnlocalizedName("redstoneRafinedIngot").func_111206_d("redstonekit:RafinedRedIngot");
		redstoneBullet = new Item(redstoneBulletID).setCreativeTab(redTab).setUnlocalizedName("redBullet").func_111206_d("redstonekit:RedGunBullNorm");
		redstoneGun = new ItemRedstoneGun(redstoneGunID).setCreativeTab(redTab).setMaxStackSize(1).setMaxDamage(127).setUnlocalizedName("redGun").func_111206_d("redstonekit:RedGun").setFull3D();
		redstoneGrenade = new ItemRedstoneGrenade(redstoneGrenadeID).setCreativeTab(redTab).setMaxStackSize(16).setUnlocalizedName("redGrenade").func_111206_d("redstonekit:RedGrenade").setFull3D();//TODO make it work
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
		
		// a test with java reflection (tried to unimmune to fire the zombie pigmen)
		//ReflectionHelper.setPrivateValue(EntityPigZombie.class, pZombieInstance, 56, "angerLevel");
	}
	
	public static EntityPigZombie getInstanceOfEntity()
	{
		return pZombieInstance;
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		registerBlocks();
		registerItems();
		registerCrafts();
		
		GameRegistry.registerTileEntity(TileEntityMicrowave.class, microwaveTileEntityID);
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
			"III", "IRI", "III", Character.valueOf('R'), Item.diamond, Character.valueOf('I'), redstoneIngot
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
			"RR ", "RR ", "  S", Character.valueOf('R'), redstoneRafinedIngot, Character.valueOf('S'), Item.stick
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneBullet, 5), new Object[]{
			"RRR", Character.valueOf('R'), redstoneRafinedIngot
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneGrenade, 2), new Object[]{
			"TRT", "RTR", "TRT", Character.valueOf('T'), Item.gunpowder, Character.valueOf('R'), redstoneIngot
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneFenceIdle, 1), new Object[]{
			"SSS", "SSS", Character.valueOf('S'), redstoneStick
		});
	   GameRegistry.addShapedRecipe(new ItemStack(redstoneStick, 1), new Object[]{
			"R", "R", Character.valueOf('R'), redstoneIngot
		});
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
