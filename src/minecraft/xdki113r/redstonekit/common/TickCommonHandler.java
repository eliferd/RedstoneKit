package xdki113r.redstonekit.common;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class TickCommonHandler implements ITickHandler
{
	public void playerTick(EntityPlayer player)
	{
		if (player.getCurrentItemOrArmor(4) != null && player.getCurrentItemOrArmor(3) != null
				&& player.getCurrentItemOrArmor(2) != null && player.getCurrentItemOrArmor(1) != null)
		{
			ItemStack helmet = player.getCurrentItemOrArmor(4);
			ItemStack chest = player.getCurrentItemOrArmor(3);
			ItemStack legs = player.getCurrentItemOrArmor(2);
			ItemStack boots = player.getCurrentItemOrArmor(1);

			if(helmet.getItem() == RedstoneKit.redstoneHelmet && chest.getItem() == RedstoneKit.redstoneChestplate && legs.getItem() == RedstoneKit.redstoneLeggings && boots.getItem() == RedstoneKit.redstoneBoots)
			{
				/** helmet **/
				player.fallDistance = -25F; // (ou 0F, du moment qu'il est pas au dessus de 0F c'est bon)
				RedstoneKit.redstoneHelmetEquipped = true;
				
				/** chestplate **/
				RedstoneKit.redstoneChestplateEquipped = true;
				player.addPotionEffect((new PotionEffect(Potion.digSpeed.getId(), 200, 0)));
				
				/** leggings **/
				RedstoneKit.redstoneLeggingsEquipped = true;
				//player.capabilities.setPlayerWalkSpeed(0.5F); /** sois ça **/
				player.addPotionEffect((new PotionEffect(Potion.moveSpeed.getId(), 200, 2))); /** ou ça **/
				
				/** boots **/
				RedstoneKit.redstoneBootsEquipped = true;
				player.addPotionEffect((new PotionEffect(Potion.jump.getId(), 200, 2))); // 200 = tick de durée et 2 = si je mets 0 ça sera Jump I etc..
				
			}else
			{
				RedstoneKit.redstoneHelmetEquipped = false;
				RedstoneKit.redstoneChestplateEquipped = false;
				RedstoneKit.redstoneLeggingsEquipped = false;
				RedstoneKit.redstoneBootsEquipped = false;

				//player.capabilities.setPlayerWalkSpeed(0.1F);
				player.clearActivePotions();
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
