package com.xkball.flamereaction.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class ImageCheckBox extends Checkbox {
    
    private final ResourceLocation imageLocation;
    private final ResourceLocation selectedLocation;
    private final int xTexStart;
    private final int yTexStart;
    private final int yDiffTex;
    private final int textureWidth;
    private final int textureHeight;
    private final boolean showLabel;
    
    public static final Logger LOGGER = LogManager.getLogger();
    
    public ImageCheckBox(int positionX, int positionY, int sizeX, int sizeY, Component name, boolean selected,
                         ResourceLocation selectedLocation, boolean showLabel,
                         ResourceLocation resourceLocation,
                         int xTexStart, int yTexStart, int yDiffTex, int textureWidth, int textureHeight) {
        super(positionX, positionY, sizeX, sizeY, name, selected,showLabel);
        this.selectedLocation = selectedLocation;
        this.imageLocation = resourceLocation;
        this.showLabel = showLabel;
        this.xTexStart = xTexStart;
        this.yTexStart = yTexStart;
        this.yDiffTex = yDiffTex;
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }
    
    @Override
    public void renderButton(@NotNull PoseStack p_93843_, int p_93844_, int p_93845_, float p_93846_) {
        Minecraft minecraft = Minecraft.getInstance();
        RenderSystem.setShaderTexture(0, selected()?selectedLocation:imageLocation);
        RenderSystem.enableDepthTest();
        int i = this.yTexStart;
        if (this.isHoveredOrFocused()) {
            i += this.yDiffTex;
        }
    
        Font font = minecraft.font;
        RenderSystem.setShaderColor(isFocused()?0.9F:1.0F, isFocused()?0.9F:1.0F, isFocused()?0.9F:1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        blit(p_93843_, this.x, this.y, xTexStart,i, this.width, this.height, textureWidth,textureHeight);
        this.renderBg(p_93843_, minecraft, p_93844_, p_93845_);
        if (showLabel) {
            drawString(p_93843_, font, this.getMessage(), this.x + 24, this.y + (this.height - 8) / 2, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
        }
    }
    
    @Override
    protected void onDrag(double v, double v1, double v2, double v3) {
        //if (selected()) onPress();
        super.onDrag(v, v1, v2, v3);
        
    }
    
    
}
