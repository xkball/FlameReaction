package com.xkball.flamereaction.itemlike.block.blockentityrender;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.xkball.flamereaction.eventhandler.register.FluidRegister;
import com.xkball.flamereaction.itemlike.block.blockentity.DippingBlockEntity;
import com.xkball.flamereaction.itemlike.item.commonitem.tool.Pliers;
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
    public DippingBlockBER(
            @SuppressWarnings("unused")
            BlockEntityRendererProvider.Context context) {
    }
    
    @Override
    public void render(@NotNull DippingBlockEntity entity, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource,int combinedLightIn, int combinedOverlayIn) {
        var fluid = entity.getFluid();
        var fluidAttributes = fluid.getFluid().getAttributes();
        var buffer = multiBufferSource.getBuffer(FluidRenderFromMantle.RenderTypesFromMantle.FLUID);
        var t = fluidAttributes.getStillTexture();
        var texture = FluidRenderFromMantle.getBlockSprite(t);
        var color = fluidAttributes.getColor();
        var from = Vector3f.ZERO.copy();
        var item = entity.getItem();
        var to = new Vector3f(1.0F,(fluid.getAmount()/1000F)*(2/16F)-0.01F,1.0F);
        FluidRenderFromMantle.renderFluid(poseStack,buffer,texture, Direction.UP,from,to,color,1);
    
        poseStack.pushPose();
        poseStack.translate(0.5,0.374,0.5);
        poseStack.scale(0.9f,0.9f,0.9f);
        
        if(!(item.getItem() instanceof Pliers )|| !(item.getItem() instanceof BlockItem)){
            poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        }
        if(item.getItem() instanceof Pliers){
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(180F));
        }
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.FIXED,combinedLightIn,combinedOverlayIn,poseStack,multiBufferSource,1);
        poseStack.popPose();
    }
}
