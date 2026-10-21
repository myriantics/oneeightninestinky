package net.myriantics.oneeightninestinky.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.*;
import net.myriantics.oneeightninestinky.OneEightNineStinky;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityCollisionContext.class)
public abstract class EntityCollisionContextMixin implements CollisionContext {

    @Shadow
    @Final
    private @Nullable Entity entity;

    @WrapMethod(
            method = "getCollisionShape"
    )
    private VoxelShape oneEightNineStinky$overrideCollisionShapeWithSelectedIfValid(BlockState state, CollisionGetter collisionGetter, BlockPos pos, Operation<VoxelShape> original) {
        if (this.entity instanceof LocalPlayer) {
            HitResult result = Minecraft.getInstance().hitResult;
            if (result.getType() == HitResult.Type.BLOCK && result instanceof BlockHitResult blockHitResult) {
                BlockPos crosshairTargetedPos = blockHitResult.getBlockPos();
                if (!crosshairTargetedPos.equals(pos)) {

                    BlockState crosshairTargetedState = this.entity.level().getBlockState(crosshairTargetedPos);
                    if (OneEightNineStinky.allowHitboxManipulation(state, crosshairTargetedState)) {
                        return crosshairTargetedState.getCollisionShape(this.entity.level(), crosshairTargetedPos, this);
                    }
                }
            }
        }
        return original.call(state, collisionGetter, pos);
    }
}
