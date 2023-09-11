package net.povstalec.sgjourney.common.block_entities.stargate;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.povstalec.sgjourney.common.init.BlockEntityInit;
import net.povstalec.sgjourney.common.init.SoundInit;
import net.povstalec.sgjourney.common.stargate.Stargate;

public class TollanStargateEntity extends AbstractStargateEntity
{
	public int currentSymbol = 0;
	public int[] addressBuffer = new int[0];

	public TollanStargateEntity(BlockPos pos, BlockState state)
	{
		super(BlockEntityInit.TOLLAN_STARGATE.get(), pos, state, Stargate.Gen.GEN_2, 2);
	}
	
	@Override
    public void onLoad()
	{
        if(level.isClientSide())
        	return;
        setPointOfOrigin(this.getLevel());
        setSymbols(this.getLevel());
        
        super.onLoad();
    }
	
	@Override
    public void load(CompoundTag nbt)
	{
        super.load(nbt);
        
        addressBuffer = nbt.getIntArray("AddressBuffer");
        currentSymbol = nbt.getInt("CurrentSymbol");
    }
	
	@Override
	protected void saveAdditional(@NotNull CompoundTag nbt)
	{
		super.saveAdditional(nbt);
		
		nbt.putIntArray("AddressBuffer", addressBuffer);
		nbt.putInt("CurrentSymbol", currentSymbol);
	}
	
	public SoundEvent chevronEngageSound()
	{
		return SoundInit.TOLLAN_CHEVRON_ENGAGE.get();
	}
	
	public SoundEvent wormholeOpenSound()
	{
		return SoundInit.MILKY_WAY_WORMHOLE_OPEN.get();
	}
	
	public SoundEvent failSound()
	{
		return SoundInit.TOLLAN_DIAL_FAIL.get();
	}
	
//	@Override
//	public Stargate.Feedback engageSymbol(int symbol)
//	{
//		System.out.println("engageSymbol: " + symbol);
//		if(isConnected() && symbol == 0)
//			return disconnectStargate(Stargate.Feedback.CONNECTION_ENDED_BY_DISCONNECT);
//
//		if(Addressing.addressContainsSymbol(addressBuffer, symbol))
//			return Stargate.Feedback.SYMBOL_ENCODED;
//
//		addressBuffer = ArrayHelper.growIntArray(addressBuffer, symbol);
//		System.out.println("addressBuffer length: " + addressBuffer.length);
//		return Stargate.Feedback.SYMBOL_ENCODED;
//	}

	public static void tick(Level level, BlockPos pos, BlockState state, TollanStargateEntity stargate)
	{
		if(level.isClientSide())
			return;

		AbstractStargateEntity.tick(level, pos, state, (AbstractStargateEntity) stargate);
	}

	@Override
	public Stargate.Feedback resetStargate(Stargate.Feedback feedback)
	{
		currentSymbol = 0;
		addressBuffer = new int[0];
		return super.resetStargate(feedback);
	}

	@Override
	public void playRotationSound()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopRotationSound()
	{
		// TODO Auto-generated method stub
		
	}
}
