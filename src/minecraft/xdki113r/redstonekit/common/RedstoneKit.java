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
import cpw.mods.fml.common.registry.LanguageRegistry;

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

	public Configuration config;

	public static boolean modLoaded;

	public static CreativeTabs redTab = new RedstoneKitCreativeTabs("redKitTab");

	@EventHandler
	public void preLoad(FMLPreInitializationEvent event)
	{
		modLoaded = false;
		config = new RedstoneKitConfiguration(event);
		try
		{
			config.load();

			redstoneGunID = config.getItem("Redstone Shotgun Item ID", 12500).getInt();
			redstoneIngotID = config.getItem("Redstone Ingot Item ID", 12501).getInt();
			redstoneRafinedIngotID = config.getItem("Redstone Rafined Ingot Item ID", 12502).getInt();
			redstoneBulletID = config.getItem("Redstone Bullet Item ID", 12503).getInt();
			redstoneGrenadeID = config.getItem("Redstone Grenade Item ID", 12504).getInt();
			redstoneHelmetID = config.getItem("Redstone Helmet Item ID", 12505).getInt();
			redstoneChestplateID = config.getItem("Redstone Chestplate Item ID", 12506).getInt();
			redstoneLeggingsID = config.getItem("Redstone Leggings Item ID", 12507).getInt();
			redstoneBootsID = config.getItem("Redstone Boots Item ID", 12508).getInt();
			redPickID = config.getItem("Redstone Pickaxe Item ID", 12509).getInt();
			redAxeID = config.getItem("Redstone Axe Item ID", 12510).getInt();
			redShovelID = config.getItem("Redstone Shovel Item ID", 12511).getInt();
			redHoeID = config.getItem("Redstone Hoe Item ID", 12512).getInt();
			redSwordID = config.getItem("Redstone Sword Item ID", 12513).getInt();
			redstoneStickID = config.getItem("Redstone Stick Item ID", 12514).getInt();

			redstoneGlassID = config.getBlock("Redstone Glass Block ID", 12000).getInt();
			redstoneProtectionID = config.getBlock("Redstone Protection Block ID", 12001).getInt();
			redstoneMobHeadID = config.getBlock("Redstone MobHead Block ID", 12002).getInt();
			redstoneFenceIdleID = config.getBlock("Redstone FenceIdle Block ID", 12003).getInt();
			redstoneFenceActiveID = config.getBlock("Redstone FenceActive Block ID", 12004).getInt();
			redstoneMicrowaveIdleID = config.getBlock("Redstone MicrowaveIdle Block ID", 12005).getInt();
			redstoneMicrowaveActiveID = config.getBlock("Redstone MicrowaveActive Block ID", 12006).getInt();
			redstonePoweredBlockIdleID = config.getBlock("Redstone PoweredBlockIdle Block ID", 12007).getInt();
			redstonePoweredBlockActiveID = config.getBlock("Redstone PoweredBlockActive Block ID", 12008).getInt();
			redstoneDistributorID = config.getBlock("Redstone Distributor Block ID", 12009).getInt();
		} finally
		{
			if(config.hasChanged())
			{
				config.save();
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
