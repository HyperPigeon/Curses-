package net.hyper_pigeon.curses;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class DeathCurseEnchantment extends Enchantment {

    protected DeathCurseEnchantment(Weight weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }

    public int getMinimumPower(int level) {
        return 30;
    }

    public int getMaximumPower(int level) {
        return 100;
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
