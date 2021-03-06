package net.hyper_pigeon.curses;

import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CursesMod implements ModInitializer {

	public static Enchantment POVERTY;

	public static Enchantment FRAGILITY;

	public static Enchantment INSTABILITY;

	public static Enchantment DEATH;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.


		POVERTY = Registry.register(
				Registry.ENCHANTMENT,
				new Identifier("curses", "poverty"),
				new PovertyCurseEnchantment(
						Enchantment.Rarity.VERY_RARE,
						EnchantmentTarget.WEAPON,
						new EquipmentSlot[] {
								EquipmentSlot.MAINHAND
						}
				)
		);


		FRAGILITY = Registry.register(
				Registry.ENCHANTMENT,
				new Identifier("curses", "fragility"),
				new FragilityCurseEnchantment(
						Enchantment.Rarity.VERY_RARE,
						EnchantmentTarget.BREAKABLE,
						new EquipmentSlot[] {
								EquipmentSlot.MAINHAND
						}
				)
		);

		INSTABILITY = Registry.register(
				Registry.ENCHANTMENT,
				new Identifier("curses", "instability"),
				new InstabilityCurseEnchantment(
						Enchantment.Rarity.RARE,
						EnchantmentTarget.BREAKABLE,
						new EquipmentSlot[] {
								EquipmentSlot.MAINHAND
						}
				)
		);


		DEATH = Registry.register(
				Registry.ENCHANTMENT,
				new Identifier("curses", "death"),
				new DeathCurseEnchantment(
						Enchantment.Rarity.RARE,
						EnchantmentTarget.BREAKABLE,
						new EquipmentSlot[] {
								EquipmentSlot.MAINHAND
						}
				)
		);


	}
}
