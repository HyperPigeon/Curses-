package net.hyper_pigeon.curses;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

import java.util.Iterator;

public class FragilityCurseEnchantment extends Enchantment {

    public FragilityCurseEnchantment(Weight weight, EnchantmentTarget target, EquipmentSlot[] slots)
    {
        super(weight, EnchantmentTarget.BREAKABLE, slots);
    }


    public int getMinimumPower(int level) {
        return 30;
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

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level)
    {
        if(target instanceof LivingEntity)
        {
            double break_item =  (Math.random()*100) + 1;

            if (break_item <= 2.5) {
                user.getMainHandStack().damage(9001, user, (e) -> {
                    e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                });
            }

        }
        super.onTargetDamaged(user, target, level);
    }


}
