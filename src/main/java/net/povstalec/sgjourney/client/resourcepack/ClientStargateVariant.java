package net.povstalec.sgjourney.client.resourcepack;

import java.util.HashMap;
import java.util.Optional;

import javax.annotation.Nullable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.resources.ResourceLocation;
import net.povstalec.stellarview.StellarView;

public class ClientStargateVariant
{
	private static final HashMap<ResourceLocation, ClientStargateVariant> STARGATE_VARIANTS = new HashMap<>();
	
	public static void clear()
	{
		STARGATE_VARIANTS.clear();
	}
	
	public static Optional<ClientStargateVariant> getStargateVariant(ResourceLocation location)
	{
		if(STARGATE_VARIANTS.containsKey(location))
			return Optional.of(STARGATE_VARIANTS.get(location));
		
		return Optional.empty();
	}
	
	public static void addStargateVariant(ResourceLocation location, ClientStargateVariant stargateVariant)
	{
		if(!STARGATE_VARIANTS.containsKey(location))
			STARGATE_VARIANTS.put(location, stargateVariant);
		else
			StellarView.LOGGER.error("Client Stargate Variant " + location.toString() + " already exists");
	}
	
	
	
	public static final String TEXTURE = "texture";
	public static final String ENCODED_TEXTURE = "encoded_texture";
	public static final String ENGAGED_TEXTURE = "engaged_texture";
	
	public static final String WORMHOLE = "wormhole";
	public static final String SHINY_WORMHOLE = "shiny_wormhole";

	public static final String SYMBOLS = "symbols";
	
	public static final String CHEVRON_ENGAGED_SOUNDS = "chevron_engaged_sounds";
	public static final String CHEVRON_INCOMING_SOUNDS = "chevron_incoming_sounds";
	
	public static final String ROTATION_SOUNDS = "rotation_sounds";
	public static final String WORMHOLE_SOUNDS = "wormhole_sounds";
	// TODO Fail sound
	
	// TODO Add a way to decide if there is a vortex
	// TODO Add a way to specify how much distortion there will be on each event horizon
	// TODO Add a way to specify the model that will be used
	
	// Textures
	private final ResourceLocation texture;
	private final ResourceLocation encodedTexture;
	private final ResourceLocation engagedTexture;
	
	// Wormhole stuff
	private ResourcepackModel.Wormhole wormhole;
	@Nullable
	private ResourcepackModel.Wormhole shinyWormhole;
	
	// Symbol stuff
	@Nullable
	private ResourcepackModel.SymbolsModel symbols;
	
	// Sound stuff
	@Nullable
	private ResourcepackSounds.Chevron chevronEngagedSounds;
	@Nullable
	private ResourcepackSounds.Chevron chevronIncomingSounds;
	
	@Nullable
	private ResourcepackSounds.Rotation rotationSounds;
	@Nullable
	private ResourcepackSounds.Wormhole wormholeSounds;
	
	public static final Codec<ClientStargateVariant> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			// Gate and chevron textures
			ResourceLocation.CODEC.fieldOf(TEXTURE).forGetter(ClientStargateVariant::texture),
			ResourceLocation.CODEC.optionalFieldOf(ENCODED_TEXTURE).forGetter(variant -> Optional.of(variant.encodedTexture)),
			ResourceLocation.CODEC.fieldOf(ENGAGED_TEXTURE).forGetter(ClientStargateVariant::engagedTexture),
			// Wormholes
			ResourcepackModel.Wormhole.CODEC.fieldOf(WORMHOLE).forGetter(ClientStargateVariant::wormhole),
			ResourcepackModel.Wormhole.CODEC.optionalFieldOf(SHINY_WORMHOLE).forGetter(ClientStargateVariant::shinyWormhole),
			// Symbols
			ResourcepackModel.SymbolsModel.CODEC.optionalFieldOf(SYMBOLS).forGetter(ClientStargateVariant::symbols),
			// Sounds
			ResourcepackSounds.Chevron.CODEC.optionalFieldOf(CHEVRON_ENGAGED_SOUNDS).forGetter(ClientStargateVariant::chevronEngagedSounds),
			ResourcepackSounds.Chevron.CODEC.optionalFieldOf(CHEVRON_INCOMING_SOUNDS).forGetter(ClientStargateVariant::chevronIncomingSounds),
			
			ResourcepackSounds.Rotation.CODEC.optionalFieldOf(ROTATION_SOUNDS).forGetter(ClientStargateVariant::rotationSounds),
			ResourcepackSounds.Wormhole.CODEC.optionalFieldOf(WORMHOLE_SOUNDS).forGetter(ClientStargateVariant::wormholeSounds)
			).apply(instance, ClientStargateVariant::new));
	
	public ClientStargateVariant(ResourceLocation texture, Optional<ResourceLocation> encodedTexture, ResourceLocation engagedTexture,
			ResourcepackModel.Wormhole wormhole, Optional<ResourcepackModel.Wormhole> shinyWormhole,
			Optional<ResourcepackModel.SymbolsModel> symbols,
			Optional<ResourcepackSounds.Chevron> chevronEngagedSounds, Optional<ResourcepackSounds.Chevron> chevronIncomingSounds,
			Optional<ResourcepackSounds.Rotation> rotationSounds, Optional<ResourcepackSounds.Wormhole> wormholeSounds)
	{
		this.texture = texture;
		
		if(encodedTexture.isPresent())
			this.encodedTexture = encodedTexture.get();
		else
			this.encodedTexture = engagedTexture;

		this.engagedTexture = engagedTexture;
		
		this.wormhole = wormhole;
		
		if(shinyWormhole.isPresent())
			this.shinyWormhole = shinyWormhole.get();
		
		if(symbols.isPresent())
			this.symbols = symbols.get();
		
		if(chevronEngagedSounds.isPresent())
			this.chevronEngagedSounds = chevronEngagedSounds.get();
		
		if(rotationSounds.isPresent())
			this.rotationSounds = rotationSounds.get();
		
		if(wormholeSounds.isPresent())
			this.wormholeSounds = wormholeSounds.get();
	}
	
	public ResourceLocation texture()
	{
		return texture;
	}
	
	public ResourceLocation encodedTexture()
	{
		return encodedTexture;
	}
	
	public ResourceLocation engagedTexture()
	{
		return engagedTexture;
	}
	
	public ResourcepackModel.Wormhole wormhole()
	{
		return wormhole;
	}
	
	public Optional<ResourcepackModel.Wormhole> shinyWormhole()
	{
		return Optional.ofNullable(shinyWormhole);
	}
	
	public Optional<ResourcepackModel.SymbolsModel> symbols()
	{
		return Optional.ofNullable(symbols);
	}
	
	public Optional<ResourcepackSounds.Chevron> chevronEngagedSounds()
	{
		return Optional.ofNullable(chevronEngagedSounds);
	}
	
	public Optional<ResourcepackSounds.Chevron> chevronIncomingSounds()
	{
		return Optional.ofNullable(chevronIncomingSounds);
	}
	
	public Optional<ResourcepackSounds.Rotation> rotationSounds()
	{
		return Optional.ofNullable(rotationSounds);
	}
	
	public Optional<ResourcepackSounds.Wormhole> wormholeSounds()
	{
		return Optional.ofNullable(wormholeSounds);
	}
}
