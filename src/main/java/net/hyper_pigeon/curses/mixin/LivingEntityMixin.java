package net.hyper_pigeon.curses.mixin;

import net.hyper_pigeon.curses.CursesMod;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Shadow
    @Final
    private DefaultedList<ItemStack> equippedArmor;

    @Shadow public native void kill();

    @Shadow public native ItemStack getMainHandStack();

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
}
