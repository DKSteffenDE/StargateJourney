package net.povstalec.sgjourney.blocks.symbols;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.povstalec.sgjourney.block_entities.symbols.CartoucheEntity;
import net.povstalec.sgjourney.block_entities.symbols.SandstoneCartoucheEntity;
import net.povstalec.sgjourney.init.BlockInit;

public class SandstoneCartoucheBlock extends CartoucheBlock
{

	public SandstoneCartoucheBlock(Properties properties)
	{
		super(properties);
	}
	
	@Nullable
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) 
	{
		SandstoneCartoucheEntity cartouche = new SandstoneCartoucheEntity(pos, state);
		
		 return cartouche;
	}
    
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack)
    {
    	BlockPos blockpos = pos.above();
    	level.setBlock(blockpos, BlockInit.SANDSTONE_CARTOUCHE.get().defaultBlockState().setValue(FACING, state.getValue(FACING)).setValue(HALF, DoubleBlockHalf.UPPER), 3);
	}
    
    @Override
	public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
	{
    	if(level.getBlockState(pos).getValue(HALF) == DoubleBlockHalf.UPPER)
    		pos = pos.below();
		BlockEntity blockentity = level.getBlockEntity(pos);
		if (blockentity instanceof CartoucheEntity)
		{
			if (!level.isClientSide)
			{
				ItemStack itemstack = new ItemStack(BlockInit.SANDSTONE_CARTOUCHE.get());
				
				blockentity.saveToItem(itemstack);

				ItemEntity itementity = new ItemEntity(level, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, itemstack);
				itementity.setDefaultPickUpDelay();
				level.addFreshEntity(itementity);
			}
		}

		super.playerWillDestroy(level, pos, state, player);
	}

}