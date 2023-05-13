package net.povstalec.sgjourney.common.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.povstalec.sgjourney.common.init.SoundInit;

public class PegasusStargateRingSound extends AbstractTickableSoundInstance
{
	private static final float VOLUME_MAX = 0.1F;
	public PegasusStargateRingSound()
	{
		super(SoundInit.PEGASUS_RING_SPIN.get(), SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
		this.volume = VOLUME_MAX;
	}
	
	@Override
	public boolean isLooping()
	{
		return false;
	}

	@Override
	public void tick()
	{
		this.volume = VOLUME_MAX;
	}
	
	public void stopSound()
	{
		this.stop();
	}
}
