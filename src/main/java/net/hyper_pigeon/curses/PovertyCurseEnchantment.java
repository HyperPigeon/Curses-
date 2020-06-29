package net.hyper_pigeon.curses;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;

public class PovertyCurseEnchantment extends Enchantment {

    public  PovertyCurseEnchantment(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slots)
    {
        super(weight, target, slots);
    }


    public int getMinimumPower(int level) {
        return 20;
    }

    public int getMaximumPower(int level) {
        return 50;
    }


    public int getMaximumLevel() {
        return 1;
    }

    public boolean isTreasure() {
        return true;
    }

    public boolean isCursed() {
        return true;
    }




}
