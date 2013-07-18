package xdki113r.redstonekit.common;


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
	
	public static Item redstoneGun, redstoneIngot, redstoneBullet;
	
	public int redstoneGunID, redstoneIngotID, redstoneBulletID;
	
	public Configuration config;
		
	public static boolean modLoaded;
	
	public static CreativeTabs redTab = new RedstoneKitCreativeTabs("redKitTab");
	
	@EventHandler
	public void preLoad(FMLPreInitializationEvent event)
	{
		modLoaded = false;
		config = new RedstoneKitConfiguration(event);
		try {
			config.load();
			redstoneGunID = config.getItem("Redstone Shotgun Item ID", 12500).getInt();
			redstoneIngotID = config.getItem("Redstone Ingot Item ID", 12501).getInt();
			redstoneBulletID = config.getItem("Redstone Bullet Item ID", 12502).getInt();
		} finally {
			if(config.hasChanged())
			{
				config.save();
			}
		}
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		redstoneIngot = new Item(redstoneIngotID).setCreativeTab(redTab).setUnlocalizedName("ModId:texture").func_111206_d("ModId:texture");
		redstoneBullet = new Item(redstoneBulletID).setCreativeTab(redTab).setUnlocalizedName("").func_111206_d("");
		redstoneGun = new ItemRedstoneGun(redstoneGunID).setCreativeTab(redTab).setUnlocalizedName("gtz").func_111206_d("gtz");
		GameRegistry.registerPlayerTracker(new RedstoneKitPlayerTracker());
	}
	
	@EventHandler
	public void postLoad(FMLPostInitializationEvent event)
	{
		LanguageRegistry.addName(redstoneIngot, "Redstone Ingot");
		LanguageRegistry.instance().addStringLocalization("itemGroup.redKitTab", "Undefined");
	}
	
	public static boolean anotherModLoadedDetectionByID(String modID)
	{
		return Loader.isModLoaded(modID);
	}
	
}
