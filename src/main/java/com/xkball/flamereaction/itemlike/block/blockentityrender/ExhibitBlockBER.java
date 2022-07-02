package com.xkball.flamereaction.itemlike.block.blockentityrender;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.xkball.flamereaction.itemlike.block.blockentity.ExhibitBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nonnull;
import java.util.Random;

public class ExhibitBlockBER implements BlockEntityRenderer<ExhibitBlockEntity> {
    
    private final Random random = new Random();
    private final ItemRenderer itemRenderer;
    private final float bobOffs;
    
    public ExhibitBlockBER(BlockEntityRendererProvider.Context context) {
        this.itemRenderer =  Minecraft.getInstance().getItemRenderer();
        this.bobOffs = this.random.nextFloat() * (float)Math.PI * 2.0F;
    }
    
    @Override
    public void render(@Nonnull ExhibitBlockEntity entity, float float1, @Nonnull PoseStack poseStack,
                       @Nonnull MultiBufferSource multiBufferSource, int i1, int i2) {
        var item = entity.getItem();
            if(!item.isEmpty() && item.getItem() != Items.AIR){
                if(entity.isLikeItemEntity()){
                    itemsRender(entity,float1,poseStack,multiBufferSource,i1);
                }
                else {
                    poseStack.pushPose();
                    //移动
                    poseStack.translate(0.5D,0.5D,0.5D);
                    //缩放
                    poseStack.scale(0.7F, 0.7F,0.7F);
                    //选择
                    if(entity.getAxis()== Direction.Axis.X){
                        poseStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
                    }
                    if(entity.getAxis()== Direction.Axis.Y){
                        poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
                    }
                    itemRenderer.renderStatic(item, ItemTransforms.TransformType.FIXED,i1,i2,poseStack,multiBufferSource,1);
                    poseStack.popPose();
                }
                
            }
            
    }
    
    public void itemsRender(ExhibitBlockEntity exhibitBlockEntity, float p_115038_, PoseStack poseStack, MultiBufferSource p_115040_, int p_115041_) {
        poseStack.pushPose();
        ItemStack itemstack = exhibitBlockEntity.getItem();
        int i = itemstack.isEmpty() ? 187 : Item.getId(itemstack.getItem()) + itemstack.getDamageValue();
        this.random.setSeed(i);
        BakedModel bakedmodel = this.itemRenderer.getModel(itemstack, exhibitBlockEntity.getLevel(), null, 1);
        boolean flag = bakedmodel.isGui3d();
        int j = this.getRenderAmount(itemstack);
        float f1 = Mth.sin(((float)exhibitBlockEntity.getAge() + p_115038_) / 10.0F + this.bobOffs) * 0.1F + 0.1F;
        float f2 = shouldBob() ? ItemTransforms.NO_TRANSFORMS.getTransform(ItemTransforms.TransformType.GROUND).scale.y() : 0;
        poseStack.translate(0.5D, f1 + 0.55F * f2, 0.5D);
        float f3 = this.getSpin(p_115038_,exhibitBlockEntity);
        poseStack.scale(1.3F, 1.3F,1.3F);
        poseStack.mulPose(Vector3f.YP.rotation(f3));
        if (!flag) {
            float f7 = -0.0F * (float)(j - 1) * 0.5F;
            float f8 = -0.0F * (float)(j - 1) * 0.5F;
            float f9 = -0.09375F * (float)(j - 1) * 0.5F;
            poseStack.translate(f7, f8, f9);
        }
        
        for(int k = 0; k < j; ++k) {
            poseStack.pushPose();
            if (k > 0) {
                if (flag) {
                    float f11 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f13 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f10 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    poseStack.translate(shouldSpreadItems() ? f11 : 0, shouldSpreadItems() ? f13 : 0, shouldSpreadItems() ? f10 : 0);
                } else {
                    float f12 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                    float f14 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                    poseStack.translate(shouldSpreadItems() ? f12 : 0, shouldSpreadItems() ? f14 : 0, 0.0D);
                }
            }
            
            this.itemRenderer.render(itemstack, ItemTransforms.TransformType.GROUND, false, poseStack, p_115040_, p_115041_, OverlayTexture.NO_OVERLAY, bakedmodel);
            poseStack.popPose();
            if (!flag) {
                poseStack.translate(0.0, 0.0, 0.09375F);
            }
        }
        
        poseStack.popPose();
    }
    
    //复制自原版
    protected int getRenderAmount(ItemStack p_115043_) {
        int i = 1;
        if (p_115043_.getCount() > 48) {
            i = 5;
        } else if (p_115043_.getCount() > 32) {
            i = 4;
        } else if (p_115043_.getCount() > 16) {
            i = 3;
        } else if (p_115043_.getCount() > 1) {
            i = 2;
        }
        
        return i;
    }
    public boolean shouldBob() {
        return true;
    }
    public boolean shouldSpreadItems() {
        return true;
    }
    public float getSpin(float p_32009_,ExhibitBlockEntity entity) {
        return ((float)entity.getAge() + p_32009_) / 20.0F + this.bobOffs;
    }
    
    
    
}
