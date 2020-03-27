package net.hyper_pigeon.curses.mixin;


import net.hyper_pigeon.curses.CursesMod;
import net.minecraft.advancement.criterion.Criterions;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Shadow
    public native void postMine(World world, BlockState state, BlockPos pos, PlayerEntity miner);

    @Shadow
    public native boolean damage(int amount, Random random, ServerPlayerEntity player);

    @Shadow
    public native <T extends LivingEntity> void damage(int amount, T entity, Consumer<T> breakCallback);

    @Shadow
    public native Item getItem();

    @Shadow
    public native int getDamage();

    @Shadow
    public native boolean isDamageable();

    @Inject(at = @At("HEAD"), method = "postMine")
    public void postMine_FragilityCurseCheck(World world, BlockState state, BlockPos pos, PlayerEntity miner, CallbackInfo info) {
        Item item = this.getItem();

        if(EnchantmentHelper.getLevel(CursesMod.FRAGILITY,(ItemStack) (Object) this) >= 1) {

            double random_chance = (Math.random()*100) + 1;

            if(random_chance <= 2.5) {
                this.damage(9001, miner, (e) -> {
                    e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
                });
            }

        }

    }


    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void fragility_damage(int amount, Random random, ServerPlayerEntity player,
                                 CallbackInfoReturnable<Boolean> callback) {
        if (!this.isDamageable()) {
            callback.setReturnValue(false);
        }
        else {
            if (amount > 0) {
                int i = EnchantmentHelper.getLevel(CursesMod.FRAGILITY, (ItemStack)(Object)this);
                if (i >= 1) {
                    double break_number = Math.random()*100+1;
                    if (break_number <= 2.5) {
                        if (player != null ) {
                            Criterions.ITEM_DURABILITY_CHANGED.trigger(player, (ItemStack)(Object)this, this.getDamage()*2500);
                            callback.setReturnValue(true);
                        }
                    }
                }

            }
            else {
                callback.setReturnValue(false);
            }

        }



    }


    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void instability_activation(int amount, Random random, ServerPlayerEntity player,
                                 CallbackInfoReturnable<Boolean> callback) {
        if (!this.isDamageable()) {
            callback.setReturnValue(false);
        }
        else {
            if (amount > 0) {
                int i = EnchantmentHelper.getLevel(CursesMod.INSTABILITY, (ItemStack)(Object)this);
                if(player.isOnFire()) {
                    if (i >= 1) {
                        if (player != null) {
                            Criterions.ITEM_DURABILITY_CHANGED.trigger(player, (ItemStack) (Object) this, this.getDamage() * 2500);
                            player.getEntityWorld().createExplosion(EntityType.TNT.create(player.getEntityWorld()), DamageSource.MAGIC, player.getX(), player.getY(), player.getZ(), 5, false, Explosion.DestructionType.DESTROY);
                            callback.setReturnValue(true);
                        }
                    }
                }
            }
            else {
                callback.setReturnValue(false);
            }

        }

    }
}
