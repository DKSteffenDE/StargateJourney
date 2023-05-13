package net.povstalec.sgjourney.common.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.povstalec.sgjourney.common.block_entities.stargate.MilkyWayStargateEntity;
import net.povstalec.sgjourney.common.init.SoundInit;

public class MilkyWayStargateRingSound extends AbstractTickableSoundInstance
{
	private static final float VOLUME_MIN = 0.0F;
	private static final float VOLUME_MAX = 0.5F;
	
	MilkyWayStargateEntity stargate;
	
	public MilkyWayStargateRingSound(MilkyWayStargateEntity stargate)
	{
		super(SoundInit.MILKY_WAY_RING_SPIN.get(), SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
		this.stargate = stargate;
        this.looping = true;
        this.volume = VOLUME_MIN;
	}

	@Override
	public void tick()
	{
		if(stargate.isRotating())
			fadeIn();
		else
			fadeOut();
	}
	
	@Override
	public boolean canStartSilent()
	{
		return true;
	}
	
	private void fadeIn()
	{
		if(this.volume < VOLUME_MAX)
			this.volume += 0.05F;
	}
	
	private void fadeOut()
	{
		if(this.volume > VOLUME_MIN)
			this.volume -= 0.05F;
	}
	
}
