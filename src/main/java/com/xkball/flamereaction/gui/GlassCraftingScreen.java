package com.xkball.flamereaction.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.network.NetworkHandler;
import com.xkball.flamereaction.network.message.GlassCraftingMessage;
import com.xkball.flamereaction.util.translateutil.TranslateUtil;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class GlassCraftingScreen extends Screen {
    
    private ImageCheckBox[] imageCheckBoxes;
    private final int[] intList;
    
    public static final TranslatableComponent NAME = TranslateUtil.create("glass_crafting_screen","玻璃处理界面","glass crafting screen");
    public GlassCraftingScreen(IntList intList) {
        super(NAME);
        this.intList = intList.toIntArray();
    }
    
    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float p_96565_) {
        this.renderBackground(poseStack);
        
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0,new ResourceLocation(FlameReaction.MOD_ID, "textures/gui/empty.png"));
            int textureWidth = 256;
            int textureHeight = 256;
            blit(poseStack, (this.width/2) -88, (this.height/2) -83, 0, 0, 176, 166, textureWidth, textureHeight);
            drawString(poseStack, this.font, "玻璃合成", this.width / 2 - 10, this.height/2-80, new Color(0,0,0).getRGB());
            
            for(ImageCheckBox imageCheckBox: imageCheckBoxes) {
                imageCheckBox.render(poseStack, mouseX, mouseY, p_96565_);
            }
        super.render(poseStack, mouseX, mouseY, p_96565_);
    }
    
    @Override
    protected void init() {
        imageCheckBoxes = new ImageCheckBox[25];
        var startX = this.width/2-50;
        var startY = this.height/2-50;
        int i=0;
        for(int k = 0;k<5;k++){
            for(int j = 0;j<5;j++){
                imageCheckBoxes[i] =
                        new ImageCheckBox(startX+j*20,startY+k*20,
                                20,20,new TranslatableComponent("flamereaction.null"),
                                true,new ResourceLocation(FlameReaction.MOD_ID,"textures/gui/white_glass_checkbox.png"),false,
                                new ResourceLocation(FlameReaction.MOD_ID,"textures/gui/light_gray_glass_checkbox.png"),0,0,1,20,20);
                if(intList[i] == 1 && !imageCheckBoxes[i].selected()) imageCheckBoxes[i].onPress();
                this.addRenderableWidget(imageCheckBoxes[i]);
                i++;
            }
            
        }
        super.init();
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
    
    
    //选中为1，没选中是0
    //默认选中
    @Override
    public void onClose() {
        if(this.minecraft != null && minecraft.level != null){
            var i = new int [25];
            for(int k = 0;k<i.length;k++){
                i[k] = imageCheckBoxes[k].selected()?1:0;
            }
            NetworkHandler.CHANNEL.sendToServer(new GlassCraftingMessage(i));
        }
        super.onClose();
    }
    
}
