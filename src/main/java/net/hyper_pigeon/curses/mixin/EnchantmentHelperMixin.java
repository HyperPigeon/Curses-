package net.hyper_pigeon.curses.mixin;

import net.hyper_pigeon.curses.CursesMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Shadow
    public static native int getEquipmentLevel(Enchantment ench, LivingEntity entity);


    @Inject(at = @At("HEAD"), method = "getLooting", cancellable = true)
    private static void returnPovertyLevelOrLootingLevel(LivingEntity entity, CallbackInfoReturnable<Integer> callback) {

        if(getEquipmentLevel(CursesMod.POVERTY, entity) != 0){
            callback.setReturnValue(-50);
        }
        else {
            callback.setReturnValue(getEquipmentLevel(Enchantments.LOOTING, entity));
        }

    }


}
