package xdki113r.redstonekit.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
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
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = ModUtils.mod_id, name = ModUtils.mod_name, version = ModUtils.mod_version)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class RedstoneKit
{
	@SidedProxy(clientSide = "xdki113r.redstonekit.client.ClientProxy", serverSide = "xdki113r.redstonekit.common.CommonProxy")
	public static CommonProxy proxy;

	@Instance("RedstoneKit")
	public static RedstoneKit instance;

	public static Item redstoneGun, redstoneIngot, redstoneRafinedIngot,
			redstoneBullet, redstoneGrenade, redstoneHelmet,
			redstoneChestplate, redstoneLeggings, redstoneBoots, redPick,
			redAxe, redShovel, redHoe, redSword, redstoneStick;
	// redstoneStick is for project in future -> DO NOT USE AS TOOLS' STICKS
	// Credits for redstoneDistributor to Jerome15
	public static Block redstoneGlass, redstoneProtection, redstoneMobHead,
			redstoneFenceIdle, redstoneFenceActive, redstoneMicrowaveIdle,
			redstoneMicrowaveActive, redstonePoweredBlockIdle,
			redstonePoweredBlockActive, redstoneDistributor;

	public int redstoneGunID, redstoneIngotID, redstoneRafinedIngotID,
			redstoneBulletID, redstoneGrenadeID, redstoneHelmetID,
			redstoneChestplateID, redstoneLeggingsID, redstoneBootsID,
			redPickID, redAxeID, redShovelID, redHoeID, redSwordID,
			redstoneStickID, redstoneGlassID, redstoneProtectionID,
			redstoneMobHeadID, redstoneFenceIdleID, redstoneFenceActiveID,
			redstoneMicrowaveIdleID, redstoneMicrowaveActiveID,
			redstonePoweredBlockIdleID, redstonePoweredBlockActiveID,
			redstoneDistributorID; // Distributor -> 2 slots, 1 for input other
									// for output -> input = stone/cobble ->
									// output = redstone w/ random

	public int redstoneBulletEntityID;

	public static boolean modLoaded;

	public static CreativeTabs redTab = new RedstoneKitCreativeTabs("redKitTab");

	@EventHandler
	public void preLoad(FMLPreInitializationEvent event)
	{
		modLoaded = false;
		Configuration cfg = new RedstoneKitConfiguration(event.getSuggestedConfigurationFile());
		try
		{
			cfg.load();

			redstoneBulletEntityID = cfg.get(cfg.CATEGORY_GENERAL, "Redstone Bullet Entity ID", 50).getInt();
			
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
			redstoneDistributorID = cfg.getBlock("Redstone Distributor Block ID", 1209).getInt();
		} finally
		{
			if(cfg.hasChanged())
			{
				cfg.save();
			}
		}
		// TODO All redstoneBlock relative texture must use Vanilla Redstone Block Texture
		// TODO multiple texture blocks
		// TODO special Item/Blocks classes
		redstoneGlass = new Block(redstoneGlassID, Material.glass).setCreativeTab(redTab).setStepSound(Block.soundGlassFootstep).setLightValue(1F).setLightOpacity(0).setHardness(0.3F).setUnlocalizedName("redGlass").func_111022_d("redstonekit:redglass");
		redstoneProtection = new Block/* RedstoneProtection */(redstoneProtectionID, Material.rock).setCreativeTab(redTab).setStepSound(Block.soundStoneFootstep).setHardness(5F).setUnlocalizedName("redProtection").func_111022_d("redstonekit:redprotection");
		redstoneMobHead = new Block/* RedstoneMobHead */(redstoneMobHeadID, Material.rock).setCreativeTab(redTab).setStepSound(Block.soundStoneFootstep).setHardness(1F).setUnlocalizedName("redSkull").func_111022_d("redstonekit:redskull");
		redstoneFenceIdle = new Block/* RedstoneFence */(redstoneFenceIdleID, Material.wood).setCreativeTab(redTab).setStepSound(Block.soundWoodFootstep).setHardness(0.5F).setUnlocalizedName("redFenceIdle").func_111022_d("redstonekit:redfence");
		redstoneFenceActive = new Block/* RedstoneFence */(redstoneFenceActiveID, Material.wood).setCreativeTab(redTab).setStepSound(Block.soundWoodFootstep).setHardness(0.5F).setLightValue(0.3F).setUnlocalizedName("redFenceActive").func_111022_d("redstonekit:redfence");
		redstoneMicrowaveIdle = new Block/* RedstoneMicrowave */(redstoneMicrowaveIdleID, Material.rock).setCreativeTab(redTab).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("redMicrowaveIdle").func_111022_d("redstonekit:redmicrowave");
		redstoneMicrowaveActive = new Block/* RedstoneMicrowave */(redstoneMicrowaveActiveID, Material.rock).setCreativeTab(redTab).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setLightValue(0.3F).setUnlocalizedName("redMicrowaveActive").func_111022_d("redstonekit:redmicrowave");
		redstonePoweredBlockIdle = new Block/* RedstonePoweredBlock */(redstonePoweredBlockIdleID, Material.rock).setCreativeTab(redTab).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("redPowerBlockIdle").func_111022_d("redstonekit:redpowerblock");
		redstonePoweredBlockActive = new Block/* RedstonePoweredBlock */(redstonePoweredBlockActiveID, Material.rock).setCreativeTab(redTab).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setLightValue(0.7167F).setUnlocalizedName("redPowerBlockActive").func_111022_d("redstonekit:redpowerblock");
		redstoneDistributor = new Block/* RedstoneDistributor */(redstoneDistributorID, Material.rock).setCreativeTab(redTab).setHardness(3.5F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("redDistributor").func_111022_d("redstonekit:reddistributor");

		redstoneIngot = new Item(redstoneIngotID).setCreativeTab(redTab).setUnlocalizedName("redIngot").func_111206_d("redstonekit:redingot");
		redstoneRafinedIngot = new Item(redstoneRafinedIngotID).setCreativeTab(redTab).setUnlocalizedName("redstoneRafinedIngot").func_111206_d("redstonekit:redrafinedingot");
		redstoneBullet = new Item(redstoneBulletID).setCreativeTab(redTab).setUnlocalizedName("redBullet").func_111206_d("redstonekit:redbullet");
		redstoneGun = new ItemRedstoneGun(redstoneGunID).setCreativeTab(redTab).setMaxStackSize(1).setMaxDamage(128).setUnlocalizedName("redGun").func_111206_d("redstonekit:redgun");
		redstoneGrenade = new Item/* RedstoneGrenade */(redstoneGrenadeID).setCreativeTab(redTab).setMaxStackSize(16).setUnlocalizedName("redGrenade").func_111206_d("redstonekit:redgrenade");
		redstoneStick = new Item(redstoneStickID).setCreativeTab(redTab).setUnlocalizedName("redStick").func_111206_d("redstonekit:redstick");
		redstoneHelmet = new Item(redstoneHelmetID).setCreativeTab(redTab).setUnlocalizedName("redHelmet").func_111206_d("redstonekit:redhelmet");
		redstoneChestplate = new Item(redstoneChestplateID).setCreativeTab(redTab).setUnlocalizedName("redChestplate").func_111206_d("redstonekit:redchestplate");
		redstoneLeggings = new Item(redstoneLeggingsID).setCreativeTab(redTab).setUnlocalizedName("redLeggings").func_111206_d("redstonekit:redleggings");
		redstoneBoots = new Item(redstoneBootsID).setCreativeTab(redTab).setUnlocalizedName("redBoots").func_111206_d("redstonekit:redboots");
		redPick = new Item(redPickID).setCreativeTab(redTab).setUnlocalizedName("redPick").func_111206_d("redstonekit:redpick");
		redAxe = new Item(redAxeID).setCreativeTab(redTab).setUnlocalizedName("redAxe").func_111206_d("redstonekit:redaxe");
		redShovel = new Item(redShovelID).setCreativeTab(redTab).setUnlocalizedName("redShovel").func_111206_d("redstonekit:redshovel");
		redHoe = new Item(redHoeID).setCreativeTab(redTab).setUnlocalizedName("redHoe").func_111206_d("redstonekit:redhoe");
		redSword = new Item(redHoeID).setCreativeTab(redTab).setUnlocalizedName("redSword").func_111206_d("redstonekit:redsword");
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		GameRegistry.registerPlayerTracker(new RedstoneKitPlayerTracker());
	}

	@EventHandler
	public void postLoad(FMLPostInitializationEvent event)
	{
		proxy.addNonMobEntity(EntityRedstoneBull.class, "RedstoneBullet", 500, this, 40, 1, true);
	}

	public static boolean anotherModLoadedDetectionByID(String modID)
	{
		return Loader.isModLoaded(modID);
	}

}
