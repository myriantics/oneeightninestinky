package net.myriantics.oneeightninestinky.mixin.legacy_hitbox;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.myriantics.oneeightninestinky.OneEightNineStinky;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AnvilBlock.class)
public abstract class AnvilBlockMixin extends FallingBlock {

    @Shadow
    @Final
    public static EnumProperty<Direction> FACING;

    @Unique
    private static final VoxelShape LEGACY_X_HITBOX = box(0, 0, 2, 16, 16, 14);
    @Unique
    private static final VoxelShape LEGACY_Z_HITBOX = box(2, 0, 0, 14, 16, 16);

    public AnvilBlockMixin(Properties properties) {
        super(properties);
    }

    @WrapMethod(
            method = "getShape"
    )
    private VoxelShape oneEightNineStinky$useLegacyAnvilShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, Operation<VoxelShape> original) {
        return state.getValue(FACING).getAxis() == Direction.Axis.X ? LEGACY_X_HITBOX : LEGACY_Z_HITBOX;
    }
}
