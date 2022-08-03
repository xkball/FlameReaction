package com.xkball.flamereaction.itemlike.block.blockentityrender;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.block.blockentity.SolarReflectorBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;


public class SolarReflectorBER implements BlockEntityRenderer<SolarReflectorBlockEntity> {
    
    public static final ItemStack item = new ItemStack(FlameReaction.REFLECTOR);
    
    public SolarReflectorBER(BlockEntityRendererProvider.Context context) {
    }
    
    @Override
    public void render(@NotNull SolarReflectorBlockEntity pBlockEntity, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        var x1 = pBlockEntity.getPX();
        var y1 = pBlockEntity.getPY();
        var z1 = pBlockEntity.getPZ();
        
        var pos = pBlockEntity.getBlockPos();
        var x2 = pos.getX();
        var y2 = pos.getY();
        var z2 = pos.getZ();
        
        float dx = x1-x2;
        float dy = y1-y2;
        float dz = z1-z2;
        
        double fy = dx/dz;
        double dis = Math.sqrt(dx*dx+dz*dz);
        double fx = dy/dis;
        
        pPoseStack.pushPose();
        pPoseStack.translate(0.5,1.4,0.5);
        pPoseStack.scale(2.2F,2.2F,2.2F);
        if(x1 != 0 && y1 !=0 && z1 != 0){
            if(dz<0){
                pPoseStack.mulPose(Vector3f.YP.rotationDegrees((float) Math.toDegrees(Math.atan(fy))));
            }
            else {
                pPoseStack.mulPose(Vector3f.YP.rotationDegrees((float) Math.toDegrees(Math.atan(fy))+180));
            }
                pPoseStack.mulPose(Vector3f.XP.rotationDegrees((float) Math.toDegrees(Math.atan(fx))));
        }
        
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.FIXED,pPackedLight,pPackedOverlay,pPoseStack,pBufferSource,1);
        pPoseStack.popPose();
    }
}
