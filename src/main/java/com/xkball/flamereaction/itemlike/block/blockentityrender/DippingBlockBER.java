package com.xkball.flamereaction.itemlike.block.blockentityrender;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.xkball.flamereaction.eventhandler.register.FluidRegister;
import com.xkball.flamereaction.itemlike.block.blockentity.DippingBlockEntity;
import com.xkball.flamereaction.render.FluidRenderFromMantle;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;

public class DippingBlockBER implements BlockEntityRenderer<DippingBlockEntity> {
    public DippingBlockBER(BlockEntityRendererProvider.Context context) {
    }
    
    @Override
    public void render(@NotNull DippingBlockEntity entity, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource,int combinedLightIn, int combinedOverlayIn) {
        var buffer = multiBufferSource.getBuffer(FluidRenderFromMantle.RenderTypesFromMantle.FLUID);
        var texture = FluidRenderFromMantle.getBlockSprite(FluidRegister.FLOWING_TEXTURE);
        var color = FluidRegister.IMPURE_ALCOHOL_COLOR.getRGB();
        var from = Vector3f.ZERO.copy();
        var to = new Vector3f(1.0F,1.0F,3/16F);
       // FluidRenderFromMantle.renderFluid(poseStack,buffer,texture, Direction.UP,from,to,color,1);
    }
}
