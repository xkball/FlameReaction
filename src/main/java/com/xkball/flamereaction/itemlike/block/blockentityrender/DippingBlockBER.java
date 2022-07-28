package com.xkball.flamereaction.itemlike.block.blockentityrender;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.eventhandler.register.FluidRegister;
import com.xkball.flamereaction.itemlike.block.blockentity.DippingBlockEntity;
import com.xkball.flamereaction.render.FluidRenderFromMantle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import org.jetbrains.annotations.NotNull;

public class DippingBlockBER implements BlockEntityRenderer<DippingBlockEntity> {
    public DippingBlockBER(BlockEntityRendererProvider.Context context) {
    }
    
    @Override
    public void render(@NotNull DippingBlockEntity entity, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource,int combinedLightIn, int combinedOverlayIn) {
        var buffer = multiBufferSource.getBuffer(FluidRenderFromMantle.RenderTypesFromMantle.FLUID);
        var texture = FluidRenderFromMantle.getBlockSprite(FluidRegister.STILL_TEXTURE);
        var fluid = entity.getFluid();
        var fluidAttributes = fluid.getFluid().getAttributes();
        var color = fluidAttributes.getColor();
        var from = Vector3f.ZERO.copy();
        var item = entity.getItem();
        var to = new Vector3f(1.0F,fluid.getAmount()/3000F,1.0F);
        FluidRenderFromMantle.renderFluid(poseStack,buffer,texture, Direction.UP,from,to,color,1);
    
        poseStack.pushPose();
        poseStack.translate(0.5,0.374,0.5);
        
        if(!item.is(FlameReaction.PLIERS) || !(item.getItem() instanceof BlockItem)){
            poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        }
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.FIXED,combinedLightIn,combinedOverlayIn,poseStack,multiBufferSource,1);
        poseStack.popPose();
    }
}
