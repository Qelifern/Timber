package timber.event;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LogBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import timber.config.Config;

import java.util.ArrayList;

public class Timber {


    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        World world = event.getPlayer().getEntityWorld();
        if (!world.isRemote) {
            if (!Config.CLIENT.activateTimberMod.get()) {
                return;
            }
            if (Config.CLIENT.reverseControl.get()) {
                if (!event.getPlayer().isCrouching()) {
                    return;
                }
            } else if (event.getPlayer().isCrouching()) {
                return;
            }
            if (event.getPlayer().getHeldItem(event.getPlayer().getActiveHand()).getItem() instanceof AxeItem) {
                if (world.getBlockState(event.getPos()).getBlock() instanceof LogBlock) {
                    if (!event.getPlayer().isCreative()) {
                        event.getPlayer().addExhaustion(0.025F);
                        chopLogs(world, event.getPos(), world.getBlockState(event.getPos()).getBlock(), new ArrayList<BlockPos>(), true, event.getPlayer());
                    } else {
                        chopLogs(world, event.getPos(), world.getBlockState(event.getPos()).getBlock(), new ArrayList<BlockPos>(), Config.SERVER.dropsInCreativeMode.get(), event.getPlayer());
                    }
                }
            }
        }
    }

    private static void chopLogs(World world, BlockPos pos, Block block, ArrayList<BlockPos> list, boolean drop, PlayerEntity player) {
        if (world.getBlockState(pos.north()).getBlock() == block) {
            list.add(pos.north());
        }
        if (world.getBlockState(pos.north().east()).getBlock() == block) {
            list.add(pos.north().east());
        }
        if (world.getBlockState(pos.north().west()).getBlock() == block) {
            list.add(pos.north().west());
        }
        if (world.getBlockState(pos.north().east().down()).getBlock() == block) {
            list.add(pos.north().east().down());
        }
        if (world.getBlockState(pos.north().west().down()).getBlock() == block) {
            list.add(pos.north().west().down());
        }
        if (world.getBlockState(pos.south()).getBlock() == block) {
            list.add(pos.south());
        }
        if (world.getBlockState(pos.south().east()).getBlock() == block) {
            list.add(pos.south().east());
        }
        if (world.getBlockState(pos.south().west()).getBlock() == block) {
            list.add(pos.south().west());
        }
        if (world.getBlockState(pos.south().east().down()).getBlock() == block) {
            list.add(pos.south().east().down());
        }
        if (world.getBlockState(pos.south().west().down()).getBlock() == block) {
            list.add(pos.south().west().down());
        }
        if (world.getBlockState(pos.east()).getBlock() == block) {
            list.add(pos.east());
        }
        if (world.getBlockState(pos.west()).getBlock() == block) {
            list.add(pos.west());
        }
        if (world.getBlockState(pos.down()).getBlock() == block) {
            list.add(pos.down());
        }
        if (world.getBlockState(pos.down().north()).getBlock() == block) {
            list.add(pos.down().north());
        }
        if (world.getBlockState(pos.down().south()).getBlock() == block) {
            list.add(pos.down().south());
        }
        if (world.getBlockState(pos.down().east()).getBlock() == block) {
            list.add(pos.down().east());
        }
        if (world.getBlockState(pos.down().west()).getBlock() == block) {
            list.add(pos.down().west());
        }
        if (world.getBlockState(pos.north().east().up()).getBlock() == block) {
            list.add(pos.north().east().up());
        }
        if (world.getBlockState(pos.north().west().up()).getBlock() == block) {
            list.add(pos.north().west().up());
        }
        if (world.getBlockState(pos.south().east().up()).getBlock() == block) {
            list.add(pos.south().east().up());
        }
        if (world.getBlockState(pos.south().west().up()).getBlock() == block) {
            list.add(pos.south().west().up());
        }
        if (world.getBlockState(pos.up()).getBlock() == block) {
            list.add(pos.up());
        }
        if (world.getBlockState(pos.up().north()).getBlock() == block) {
            list.add(pos.up().north());
        }
        if (world.getBlockState(pos.up().south()).getBlock() == block) {
            list.add(pos.up().south());
        }
        if (world.getBlockState(pos.up().east()).getBlock() == block) {
            list.add(pos.up().east());
        }
        if (world.getBlockState(pos.up().west()).getBlock() == block) {
            list.add(pos.up().west());
        }
        if (list.size() <= 0 || list == null) {
            list = null;
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            BlockPos pos1 = list.get(i);
            destroyBlock(world, pos1, pos, drop);
            if (Config.SERVER.damageAxe.get() && !player.isCreative()) {
                player.getHeldItemMainhand().attemptDamageItem(1, player.getRNG(), null);
            }
            chopLogs(world, list.get(i), block, new ArrayList<BlockPos>(), drop, player);
        }
    }

    public static boolean destroyBlock(World world, BlockPos pos, BlockPos posToDropItems, boolean drop) {
        BlockState blockstate = world.getBlockState(pos);
        if (blockstate.isAir(world, pos)) {
            return false;
        } else {
            TileEntity tileentity = blockstate.hasTileEntity() ? world.getTileEntity(pos) : null;
            if (drop) {
                Block.spawnDrops(blockstate, world, posToDropItems, tileentity);
            }

            return world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

}
