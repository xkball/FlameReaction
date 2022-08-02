package com.xkball.flamereaction.itemlike.block.blockentityrender;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity.AlcoholLampBlockEntity;
import com.xkball.flamereaction.itemlike.block.commonblocks.burningblock.AlcoholLamp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraftforge.client.model.data.EmptyModelData;
import org.jetbrains.annotations.NotNull;

public class AlcoholLampBER implements BlockEntityRenderer<AlcoholLampBlockEntity> {
    
    public AlcoholLampBER(BlockEntityRendererProvider.Context context) {
    }
    
    @Override
    public void render(@NotNull AlcoholLampBlockEntity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        var blockRenderer = Minecraft.getInstance().getBlockRenderer();
        var pState = pBlockEntity.getBlockState();
        var model_o = blockRenderer.getBlockModelShaper().getModelManager().getModel(new ResourceLocation(FlameReaction.MOD_ID,"alcohol_lamp_fire_gray"));
        var model = blockRenderer.getBlockModelShaper().getModelManager().getModel(new ResourceLocation(FlameReaction.MOD_ID,"alcohol_lamp_fire"));
        
        if(pState.getValue(AlcoholLamp.FIRED)){
            var i = Math.abs(pBlockEntity.getTimeLast());
            var i2 = i%5%4;
            pPoseStack.pushPose();
            pPoseStack.mulPose(Vector3f.YP.rotationDegrees(90F*i2));
            if(pBlockEntity.getBlockState().getValue(AlcoholLamp.O_COLORED)){
                blockRenderer.getModelRenderer()
                        .renderModel(pPoseStack.last(), pBufferSource.getBuffer(ItemBlockRenderTypes.getRenderType(pState, false)), pState, model_o, 1f,1f,1f, pPackedLight, pPackedOverlay, EmptyModelData.INSTANCE);
            }
            else {
                blockRenderer.getModelRenderer()
                        .renderModel(pPoseStack.last(), pBufferSource.getBuffer(ItemBlockRenderTypes.getRenderType(pState, false)), pState, model, 1f,1f,1f, pPackedLight, pPackedOverlay, EmptyModelData.INSTANCE);
            }
            pPoseStack.popPose();
        }
        
    }
}
