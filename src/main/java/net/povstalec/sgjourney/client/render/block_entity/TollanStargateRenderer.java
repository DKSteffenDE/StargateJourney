package net.povstalec.sgjourney.client.render.block_entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.povstalec.sgjourney.client.Layers;
import net.povstalec.sgjourney.client.models.TollanStargateModel;
import net.povstalec.sgjourney.client.models.WormholeModel;
import net.povstalec.sgjourney.common.block_entities.stargate.AbstractStargateEntity;
import net.povstalec.sgjourney.common.block_entities.stargate.TollanStargateEntity;
import net.povstalec.sgjourney.common.blocks.stargate.AbstractStargateBaseBlock;
import net.povstalec.sgjourney.common.blocks.stargate.TollanStargateBlock;
import net.povstalec.sgjourney.common.config.ClientStargateConfig;
import net.povstalec.sgjourney.common.misc.Orientation;

@OnlyIn(Dist.CLIENT)
public class TollanStargateRenderer extends AbstractStargateRenderer implements BlockEntityRenderer<TollanStargateEntity>
{
	protected final WormholeModel wormholeModel;
	protected final TollanStargateModel stargateModel;

	public TollanStargateRenderer(BlockEntityRendererProvider.Context context)
	{
		super(context);
		this.wormholeModel = new WormholeModel(ClientStargateConfig.tollan_rgba, 0.125F);
		this.stargateModel = new TollanStargateModel(
				context.bakeLayer(Layers.TOLLAN_RING_LAYER),
				context.bakeLayer(Layers.TOLLAN_SYMBOL_RING_LAYER),
				context.bakeLayer(Layers.TOLLAN_CHEVRON_LAYER));
	}
	
	@Override
	public void render(TollanStargateEntity stargate, float partialTick, PoseStack stack,
			MultiBufferSource source, int combinedLight, int combinedOverlay)
	{
		BlockState blockstate = stargate.getBlockState();
		Direction facing = blockstate.getValue(TollanStargateBlock.FACING);
		Vec3 center = stargate.getRelativeCenter();
		Orientation orientation = blockstate.getValue(AbstractStargateBaseBlock.ORIENTATION);
		
        stack.pushPose();
        
        double shiftBase = orientation.getIndex() *  0.5;
        double shiftY = center.y();
		double shiftX = center.x();
		double shiftZ = center.z();
        
		if(orientation != Orientation.REGULAR)
		{
			if(facing.getAxis() == Direction.Axis.X)
				shiftX += facing.getAxisDirection().getStep() * shiftBase;
			else
				shiftZ += facing.getAxisDirection().getStep() * shiftBase;
			
		}
        stack.translate(shiftX, shiftY, shiftZ);
        
        stack.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));
        
        if(orientation == Orientation.UPWARD)
            stack.mulPose(Axis.XP.rotationDegrees(-90));
        else if(orientation == Orientation.DOWNWARD)
            stack.mulPose(Axis.XP.rotationDegrees(90));
        
        this.stargateModel.renderStargate(stargate, partialTick, stack, source, combinedLight, combinedOverlay);
		
        if(stargate.isConnected())
	    	this.wormholeModel.renderEventHorizon((AbstractStargateEntity) stargate, stack, source, combinedLight, combinedOverlay);
		
	    stack.popPose();
	}
	
	@Override
	public int getViewDistance()
	{
		return 128;
	}
	
}
