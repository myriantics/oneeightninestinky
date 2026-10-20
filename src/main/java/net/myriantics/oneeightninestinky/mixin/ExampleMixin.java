package net.myriantics.oneeightninestinky.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.myriantics.oneeightninestinky.OneEightNineStinky;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class ExampleMixin {
	@Shadow
	private Level level;

	@Shadow
	public abstract AABB getBoundingBox();

	@WrapMethod(
			method = "isColliding"
	)
	private boolean oneEightNineStinky$CheaterMcCheatyCheatCheat(BlockPos pos, BlockState state, Operation<Boolean> original) {
		OneEightNineStinky.LOGGER.info("SHIT FUCK SHIT");
		if ((Object) this instanceof LocalPlayer) {
			HitResult result = Minecraft.getInstance().hitResult;
			if (result.getType() == HitResult.Type.BLOCK) {
				BlockPos coolPos = ((BlockHitResult) result).getBlockPos();
				BlockState skibState = this.level.getBlockState(coolPos);
				if (state.is(skibState.getBlock())) {
					VoxelShape shape = skibState.getCollisionShape(this.level, coolPos).move(pos);
					return Shapes.joinIsNotEmpty(shape, Shapes.create(this.getBoundingBox()), BooleanOp.AND);
				}
			}
		}
		return original.call(pos, state);
	}
}