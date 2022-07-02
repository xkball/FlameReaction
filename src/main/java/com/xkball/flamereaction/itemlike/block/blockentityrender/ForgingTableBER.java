package com.xkball.flamereaction.itemlike.block.blockentityrender;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.xkball.flamereaction.itemlike.block.blockentity.ForgingTableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ForgingTableBER implements BlockEntityRenderer<ForgingTableBlockEntity> {
    
    
    
    public ForgingTableBER(BlockEntityRendererProvider.Context context) {
    }
    
    @Override
    public void render(@NotNull ForgingTableBlockEntity blockEntity, float p_112308_, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i1, int i2) {
        var item = blockEntity.getItem(0);
        
        if(!item.isEmpty()){
            poseStack.pushPose();
            poseStack.translate(0.5,0.374,0.5);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(blockEntity.getDegree()));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.FIXED,i1,i2,poseStack,multiBufferSource,1);
            poseStack.popPose();
        }
    }
}
