package net.hyper_pigeon.curses;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.world.explosion.Explosion;

public class InstabilityCurseEnchantment extends Enchantment {

    protected InstabilityCurseEnchantment(Weight weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, EnchantmentTarget.BREAKABLE, slotTypes);
    }


    public int getMinimumPower(int level) {
        return 25;
    }

    public int getMaximumPower(int level) {
        return 75;
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


    public float explosion_power (PlayerEntity user) {
        PlayerInventory inventory = user.inventory;
        float amount = 2;
        for(int i = 0; i < inventory.getInvSize(); i++) {
            ItemStack stack = inventory.getInvStack(i);
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
                e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
            });
            float e_power = 2;
            if(user instanceof PlayerEntity) {
                e_power = explosion_power((PlayerEntity) user);
            }
            user.getEntityWorld().createExplosion(EntityType.TNT.create(user.getEntityWorld()), DamageSource.MAGIC, user.getX(), user.getY(), user.getZ(), e_power, false, Explosion.DestructionType.DESTROY);
        }
        super.onTargetDamaged(user, target, level);
    }




}
