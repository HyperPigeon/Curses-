package net.hyper_pigeon.curses.mixin;

import net.hyper_pigeon.curses.CursesMod;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    @Final
    private DefaultedList<ItemStack> equippedArmor;

    @Shadow public native void kill();

    @Shadow public native ItemStack getMainHandStack();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }


    @Inject(at = @At("HEAD"), method = "tick")
    public void instaKill(CallbackInfo info) {
        ItemStack helmetStack = equippedArmor.get(0);
        ItemStack chestStack = equippedArmor.get(1);
        ItemStack leggingsStack = equippedArmor.get(2);
        ItemStack bootsStack = equippedArmor.get(3);
        ItemStack handStack = getMainHandStack();

        if(EnchantmentHelper.getLevel(CursesMod.DEATH, helmetStack) >= 1 || EnchantmentHelper.getLevel(CursesMod.DEATH, chestStack) >= 1
        || EnchantmentHelper.getLevel(CursesMod.DEATH, leggingsStack) >= 1 || EnchantmentHelper.getLevel(CursesMod.DEATH, bootsStack) >= 1 || EnchantmentHelper.getLevel(CursesMod.DEATH, handStack) >= 1) {
            kill();
        }

    }


    @Inject(at = @At("HEAD"), method = "damage")
    public void blowUpArmor(DamageSource source, float amount, CallbackInfoReturnable<Boolean> callback) {
        ItemStack helmetStack = equippedArmor.get(0);
        ItemStack chestStack = equippedArmor.get(1);
        ItemStack leggingsStack = equippedArmor.get(2);
        ItemStack bootsStack = equippedArmor.get(3);
        float explosive_power = 5;

        if(source.isFire()) {
            if (EnchantmentHelper.getLevel(CursesMod.INSTABILITY, helmetStack) >= 1 || EnchantmentHelper.getLevel(CursesMod.INSTABILITY, chestStack) >= 1
                    || EnchantmentHelper.getLevel(CursesMod.INSTABILITY, leggingsStack) >= 1 || EnchantmentHelper.getLevel(CursesMod.INSTABILITY, bootsStack) >= 1) {
                if (EnchantmentHelper.getLevel(CursesMod.INSTABILITY, helmetStack) >= 1) {
                    helmetStack.damage(9999, (LivingEntity) (Object) this, (e) -> {
                        e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                    });
                    explosive_power += 5;
                }
                if (EnchantmentHelper.getLevel(CursesMod.INSTABILITY, chestStack) >= 1) {
                    chestStack.damage(9999, (LivingEntity) (Object) this, (e) -> {
                        e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                    });
                    explosive_power += 5;
                }
                if (EnchantmentHelper.getLevel(CursesMod.INSTABILITY, leggingsStack) >= 1) {
                    leggingsStack.damage(9999, (LivingEntity) (Object) this, (e) -> {
                        e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                    });
                    explosive_power += 5;
                }
                if (EnchantmentHelper.getLevel(CursesMod.INSTABILITY, bootsStack) >= 1) {
                    bootsStack.damage(9999, (LivingEntity) (Object) this, (e) -> {
                        e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                    });
                    explosive_power += 5;
                }

                getEntityWorld().createExplosion(EntityType.TNT.create(getEntityWorld()), getX(), getY(), getZ(), explosive_power, Explosion.DestructionType.DESTROY);

            }
        }
    }
}
