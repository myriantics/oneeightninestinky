package net.myriantics.oneeightninestinky;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OneEightNineStinky implements ModInitializer {
	public static final String MOD_ID = "oneeightninestinky";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("1.8.9 is certifiably STINKY. OneEightNineStinky has initialized!");
	}

	public static boolean allowHitboxManipulation(BlockState collidedState, BlockState targetedState) {
		// Chests had no metadata, but the bug worked for both chests and trapped chests - although not between them.
		// This allows for the same functionality.
		if (collidedState.getBlock() instanceof ChestBlock && collidedState.is(targetedState.getBlock())) {
			return true;
		}

		// Since anvils use block metadata on 1.8, you could hitbox manipulate across anvil damage stages.
		// We have special handling to preserve this.
		if (isAny(collidedState, Blocks.ANVIL, Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL) && isAny(collidedState, Blocks.ANVIL, Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL)) {
			return true;
		}

		return false;
	}

	public static boolean isAny(BlockState state, Block... blocks) {
		for (Block block : blocks) {
			if (state.is(block)) {
				return true;
			}
		}
		return false;
	}

	public static Identifier locate(String name) {
		return Identifier.fromNamespaceAndPath(MOD_ID, name);
	}
}