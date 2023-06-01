package net.povstalec.sgjourney.client;

import net.minecraft.client.Minecraft;
import net.povstalec.sgjourney.StargateJourney;
import net.povstalec.sgjourney.client.sounds.MilkyWayStargateRingBuildupSound;
import net.povstalec.sgjourney.client.sounds.MilkyWayStargateRingSound;
import net.povstalec.sgjourney.client.sounds.PegasusStargateRingSound;
import net.povstalec.sgjourney.client.sounds.StargateSound;
import net.povstalec.sgjourney.client.sounds.UniverseStargateRingSound;
import net.povstalec.sgjourney.client.sounds.WormholeIdleSound;
import net.povstalec.sgjourney.common.block_entities.stargate.AbstractStargateEntity;
import net.povstalec.sgjourney.common.block_entities.stargate.MilkyWayStargateEntity;
import net.povstalec.sgjourney.common.block_entities.stargate.PegasusStargateEntity;
import net.povstalec.sgjourney.common.block_entities.stargate.UniverseStargateEntity;

public abstract class StargateSoundWrapper
{
	protected static Minecraft minecraft = Minecraft.getInstance();

	protected AbstractStargateEntity stargate;
	protected StargateSound sound;
	protected boolean playingSound = false;

	protected StargateSoundWrapper(AbstractStargateEntity stargate, StargateSound sound)
	{
		this.stargate = stargate;
		this.sound = sound;
	}
	
	public boolean isPlaying()
	{
		return this.playingSound;
	}
	
	public void playSound()
	{
		if(!this.playingSound)
		{
			minecraft.getSoundManager().play(sound);
			this.playingSound = true;
		}
	}
	
	public void stopSound()
	{
		if(this.playingSound)
		{
			this.sound.stopSound();
			this.playingSound = false;
		}
	}
	

	public static class WormholeIdle extends StargateSoundWrapper
	{
		public WormholeIdle(AbstractStargateEntity stargate)
		{
			super(stargate, new WormholeIdleSound(stargate));
		}
	}
	
	public static class UniverseRingRotation extends StargateSoundWrapper
	{
		public UniverseRingRotation(UniverseStargateEntity stargate)
		{
			super(stargate, new UniverseStargateRingSound(stargate));
		}
	}
	
	public static class MilkyWayRingBuildup extends StargateSoundWrapper
	{
		public MilkyWayRingBuildup(MilkyWayStargateEntity stargate)
		{
			super(stargate, new MilkyWayStargateRingBuildupSound(stargate));
		}
		
		@Override
		public void playSound()
		{
			if(!this.playingSound)
			{
				this.sound = new MilkyWayStargateRingBuildupSound((MilkyWayStargateEntity) stargate);
				minecraft.getSoundManager().play(sound);
				this.playingSound = true;
			}
		}
	}
	
	public static class MilkyWayRingRotation extends StargateSoundWrapper
	{
		public MilkyWayRingRotation(MilkyWayStargateEntity stargate)
		{
			super(stargate, new MilkyWayStargateRingSound(stargate));
		}
	}
	
	public static class PegasusRingRotation extends StargateSoundWrapper
	{
		public PegasusRingRotation(PegasusStargateEntity stargate)
		{
			super(stargate, new PegasusStargateRingSound(stargate));
		}
		
		@Override
		public void playSound()
		{
			this.sound = new PegasusStargateRingSound((PegasusStargateEntity) stargate);
			minecraft.getSoundManager().play(sound);
			this.playingSound = true;
		}

		@Override
		public void stopSound()
		{
			this.sound.stopSound();
			this.playingSound = false;
		}
	}
}
