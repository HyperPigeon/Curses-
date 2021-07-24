package net.hyper_pigeon.curses;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.explosion.Explosion;

public class InstabilityCurseEnchantment extends Enchantment {

    protected InstabilityCurseEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, EnchantmentTarget.BREAKABLE, slotTypes);
    }


    public boolean isTreasure() {
        return true;
    }

    public boolean isCursed() {
        return true;
    }


    public float explosion_power (PlayerEntity user) {
        PlayerInventory inventory = user.getInventory();
        float amount = 2;
        for(int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            float k = EnchantmentHelper.getLevel(CursesMod.INSTABILITY, stack);
            if(k > 0) {
                amount += 2;
                stack.damage(1000, user, (e) -> {
                    e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                });
            }

        }
        return amount;
    }


    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (target.isOnFire() || target instanceof BlazeEntity) {
            user.getMainHandStack().damage(1000, user, (e) -> {
                user.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
            });

            float e_power = 2.5F;
            if(user instanceof PlayerEntity) {
                e_power = explosion_power((PlayerEntity) user);
            }
            user.getEntityWorld().createExplosion(EntityType.TNT.create(user.getEntityWorld()),user.getX(), user.getY(), user.getZ(), e_power, Explosion.DestructionType.DESTROY);

        }
        super.onTargetDamaged(user, target, level);
    }




}
