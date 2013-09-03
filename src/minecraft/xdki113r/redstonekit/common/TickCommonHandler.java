package xdki113r.redstonekit.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class TickCommonHandler implements ITickHandler
{
	private Map<String, Boolean> isFalling = new HashMap<String, Boolean>();
	
	public void playerTick(EntityPlayer player)
	{
		if(player.getCurrentItemOrArmor(1) != null)
		{
			if(player.getCurrentItemOrArmor(1).getItem() == RedstoneKit.redstoneBoots)
			{
				RedstoneKit.redstoneBootsEquipped = true;
		        player.addPotionEffect(new PotionEffect(Potion.jump.id, 1, 2, true));
			}
		}
		if(player.getCurrentItemOrArmor(2) != null)
		{
			if(player.getCurrentItemOrArmor(2).getItem() == RedstoneKit.redstoneLeggings && !RedstoneKit.redstoneLeggingsEquipped)
			{
				RedstoneKit.redstoneLeggingsEquipped = true;
				player.capabilities.setPlayerWalkSpeed(0.2F);
			}
			else if(player.getCurrentItemOrArmor(2).getItem() != RedstoneKit.redstoneLeggings && RedstoneKit.redstoneLeggingsEquipped)
			{
				RedstoneKit.redstoneLeggingsEquipped = false;
				player.capabilities.setPlayerWalkSpeed(player.capabilities.getWalkSpeed()-0.2F);
			}
		}
		else if(RedstoneKit.redstoneLeggingsEquipped)
		{
			RedstoneKit.redstoneLeggingsEquipped = false;
			player.capabilities.setPlayerWalkSpeed(0.1F);
		}
		if(player.getCurrentItemOrArmor(3) != null)
		{
			if(player.getCurrentItemOrArmor(3).getItem() == RedstoneKit.redstoneChestplate)
			{
				RedstoneKit.redstoneChestplateEquipped = true;
				player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 1, 2, true));
			}
		}
		if(player.getCurrentItemOrArmor(4) != null)
		{
			if(player.getCurrentItemOrArmor(4).getItem() == RedstoneKit.redstoneHelmet)
			{
				isFalling.put(player.username, player.fallDistance > 0F);
				
				if(player.onGround && isFalling.get(player.username).booleanValue())
				{
					player.fallDistance /= 4.9812909121F;
					player.getCurrentItemOrArmor(4).damageItem((player.fallDistance/5 < 1F ? 0 : (int) player.fallDistance/5), player);
				}
				
				RedstoneKit.redstoneHelmetEquipped = true;
			}
		}
		
		if(player.func_110143_aJ() <= 0.0F) // pour corriger le bug ou quand il a plus de vie il vire les effets
		{
			RedstoneKit.redstoneHelmetEquipped = false;
			RedstoneKit.redstoneChestplateEquipped = false;
			RedstoneKit.redstoneLeggingsEquipped = false;
			RedstoneKit.redstoneBootsEquipped = false;
		}
	}
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		if(type.equals(EnumSet.of(TickType.PLAYER)))
		{
			playerTick((EntityPlayer)tickData[0]);
		}
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.PLAYER, TickType.SERVER);
	}

	@Override
	public String getLabel()
	{
		return "TickCommonHandler";
	}
}
