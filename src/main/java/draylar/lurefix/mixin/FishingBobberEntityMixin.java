package draylar.lurefix.mixin;

import draylar.lurefix.logic.FishingBobberLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin extends Entity {

    @Shadow
    private int waitCountdown;

    @Shadow
    @Final
    private int lureLevel;

    private FishingBobberEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "tickFishingLogic",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;nextInt(Ljava/util/Random;II)I", ordinal = 2),
            cancellable = true
    )
    private void setupCustomTime(BlockPos pos, CallbackInfo ci) {
        // Set the initial wait time based on current lure level.
        // All lure levels have the potential for no wait time (0 ticks).
        // Vanilla has each lure level as 600 - level, so lure 2 is a max of 400 ticks (20 seconds).
        // We use an exponential function to support all lure levels, while retaining the initial 3 lure level accuracy.
        this.waitCountdown = MathHelper.nextInt(this.random, 100, 600);
        this.waitCountdown -= Math.min(waitCountdown, FishingBobberLogic.computeTimeReductionFor(lureLevel));

        // Handle no-countdown case that would normally re-call this method
        if(waitCountdown == 0) {
            waitCountdown = 1;
        }

        // Cancel method, hope nobody injects at the end :)
        ci.cancel();
    }
}
