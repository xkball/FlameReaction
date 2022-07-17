package com.xkball.flamereaction.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.xkball.flamereaction.FlameReaction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;


//几乎完全复制自Mantle模组
//但是它是MIT许可证，所以应该没毛病
public class FluidRenderFromMantle {
    
    public static class RenderTypesFromMantle extends RenderType{
    
        public RenderTypesFromMantle(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
            super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
        }
        
        public static final RenderType FLUID = create(
                FlameReaction.MOD_ID + ":block_render_type",
                DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true,
                RenderType.CompositeState.builder()
                        .setLightmapState(LIGHTMAP)
                        .setShaderState(POSITION_COLOR_TEX_LIGHTMAP_SHADER)
                        .setTextureState(BLOCK_SHEET_MIPPED)
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .createCompositeState(false));
        
    }

    /**
     * Gets a block sprite from the given location
     * @param sprite  Sprite name
     * @return  Sprite location
     */
    public static TextureAtlasSprite getBlockSprite(ResourceLocation sprite) {
        return Minecraft.getInstance().getModelManager().getAtlas(InventoryMenu.BLOCK_ATLAS).getSprite(sprite);
    }
    
    /**
     * Takes the larger light value between combinedLight and the passed block light
     * @param combinedLight  Sky light/block light lightmap value
     * @param blockLight     New 0-15 block light value
     * @return  Updated packed light including the new light value
     */
    public static int withBlockLight(int combinedLight, int blockLight) {
        // skylight from the combined plus larger block light between combined and parameter
        // not using methods from LightTexture to reduce number of operations
        return (combinedLight & 0xFFFF0000) | Math.max(blockLight << 4, combinedLight & 0xFFFF);
    }
    
    /* Fluid cuboids */
    
    /**
     * Forces the UV to be between 0 and 1
     * @param value  Original value
     * @param upper  If true, this is the larger UV. Needed to enforce integer values end up at 1
     * @return  UV mapped between 0 and 1
     */
    private static float boundUV(float value, boolean upper) {
        value = value % 1;
        if (value == 0) {
            // if it lands exactly on the 0 bound, map that to 1 instead for the larger UV
            return upper ? 1 : 0;
        }
        // modulo returns a negative result if the input is negative, so add 1 to account for that
        return value < 0 ? (value + 1) : value;
    }
    
    /**
     * Adds a quad to the renderer
     * @param renderer    Renderer instnace
     * @param matrix      Render matrix
     * @param sprite      Sprite to render
     * @param from        Quad start
     * @param to          Quad end
     * @param face        Face to render
     * @param color       Color to use in rendering
     * @param brightness  Face brightness
     * @param flowing     If true, half texture coordinates
     */
    public static void putTexturedQuad(VertexConsumer renderer, Matrix4f matrix, TextureAtlasSprite sprite, Vector3f from, Vector3f to, Direction face, int color, int brightness, int rotation, boolean flowing) {
        // start with texture coordinates
        float x1 = from.x(), y1 = from.y(), z1 = from.z();
        float x2 = to.x(), y2 = to.y(), z2 = to.z();
        // choose UV based on the directions, some need to negate UV due to the direction
        // note that we use -UV instead of 1-UV as its slightly simpler and the later logic deals with negatives
        float u1, u2, v1, v2;
        switch (face) {
            default -> { // DOWN
                u1 = x1; u2 = x2;
                v1 = z2; v2 = z1;
            }
            case UP -> {
                u1 = x1; u2 = x2;
                v1 = -z1; v2 = -z2;
            }
            case NORTH -> {
                u1 = -x1; u2 = -x2;
                v1 = y1; v2 = y2;
            }
            case SOUTH -> {
                u1 = x2; u2 = x1;
                v1 = y1; v2 = y2;
            }
            case WEST -> {
                u1 = z2; u2 = z1;
                v1 = y1; v2 = y2;
            }
            case EAST -> {
                u1 = -z1; u2 = -z2;
                v1 = y1; v2 = y2;
            }
        }
        
        // flip V when relevant
        if (rotation == 0 || rotation == 270) {
            float temp = v1;
            v1 = -v2;
            v2 = -temp;
        }
        // flip U when relevant
        if (rotation >= 180) {
            float temp = u1;
            u1 = -u2;
            u2 = -temp;
        }
        
        // bound UV to be between 0 and 1
        boolean reverse = u1 > u2;
        u1 = boundUV(u1, reverse);
        u2 = boundUV(u2, !reverse);
        reverse = v1 > v2;
        v1 = boundUV(v1, reverse);
        v2 = boundUV(v2, !reverse);
        
        // if rotating by 90 or 270, swap U and V
        float minU, maxU, minV, maxV;
        double size = flowing ? 8 : 16;
        if ((rotation % 180) == 90) {
            minU = sprite.getU(v1 * size);
            maxU = sprite.getU(v2 * size);
            minV = sprite.getV(u1 * size);
            maxV = sprite.getV(u2 * size);
        } else {
            minU = sprite.getU(u1 * size);
            maxU = sprite.getU(u2 * size);
            minV = sprite.getV(v1 * size);
            maxV = sprite.getV(v2 * size);
        }
        // based on rotation, put coords into place
        float u3, u4, v3, v4;
        switch(rotation) {
            default -> { // 0
                u1 = minU; v1 = maxV;
                u2 = minU; v2 = minV;
                u3 = maxU; v3 = minV;
                u4 = maxU; v4 = maxV;
            }
            case 90 -> {
                u1 = minU; v1 = minV;
                u2 = maxU; v2 = minV;
                u3 = maxU; v3 = maxV;
                u4 = minU; v4 = maxV;
            }
            case 180 -> {
                u1 = maxU; v1 = minV;
                u2 = maxU; v2 = maxV;
                u3 = minU; v3 = maxV;
                u4 = minU; v4 = minV;
            }
            case 270 -> {
                u1 = maxU; v1 = maxV;
                u2 = minU; v2 = maxV;
                u3 = minU; v3 = minV;
                u4 = maxU; v4 = minV;
            }
        }
        // add quads
        int light1 = brightness & 0xFFFF;
        int light2 = brightness >> 0x10 & 0xFFFF;
        int a = color >> 24 & 0xFF;
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        switch (face) {
            case DOWN -> {
                renderer.vertex(matrix, x1, y1, z2).color(r, g, b, a).uv(u1, v1).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x1, y1, z1).color(r, g, b, a).uv(u2, v2).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x2, y1, z1).color(r, g, b, a).uv(u3, v3).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x2, y1, z2).color(r, g, b, a).uv(u4, v4).uv2(light1, light2).endVertex();
            }
            case UP -> {
                renderer.vertex(matrix, x1, y2, z1).color(r, g, b, a).uv(u1, v1).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x1, y2, z2).color(r, g, b, a).uv(u2, v2).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x2, y2, z2).color(r, g, b, a).uv(u3, v3).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x2, y2, z1).color(r, g, b, a).uv(u4, v4).uv2(light1, light2).endVertex();
            }
            case NORTH -> {
                renderer.vertex(matrix, x1, y1, z1).color(r, g, b, a).uv(u1, v1).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x1, y2, z1).color(r, g, b, a).uv(u2, v2).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x2, y2, z1).color(r, g, b, a).uv(u3, v3).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x2, y1, z1).color(r, g, b, a).uv(u4, v4).uv2(light1, light2).endVertex();
            }
            case SOUTH -> {
                renderer.vertex(matrix, x2, y1, z2).color(r, g, b, a).uv(u1, v1).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x2, y2, z2).color(r, g, b, a).uv(u2, v2).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x1, y2, z2).color(r, g, b, a).uv(u3, v3).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x1, y1, z2).color(r, g, b, a).uv(u4, v4).uv2(light1, light2).endVertex();
            }
            case WEST -> {
                renderer.vertex(matrix, x1, y1, z2).color(r, g, b, a).uv(u1, v1).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x1, y2, z2).color(r, g, b, a).uv(u2, v2).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x1, y2, z1).color(r, g, b, a).uv(u3, v3).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x1, y1, z1).color(r, g, b, a).uv(u4, v4).uv2(light1, light2).endVertex();
            }
            case EAST -> {
                renderer.vertex(matrix, x2, y1, z1).color(r, g, b, a).uv(u1, v1).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x2, y2, z1).color(r, g, b, a).uv(u2, v2).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x2, y2, z2).color(r, g, b, a).uv(u3, v3).uv2(light1, light2).endVertex();
                renderer.vertex(matrix, x2, y1, z2).color(r, g, b, a).uv(u4, v4).uv2(light1, light2).endVertex();
            }
        }
    }
    
    public static void renderFluid(PoseStack poseStack,VertexConsumer buffer,TextureAtlasSprite texture,Direction direction,Vector3f from, Vector3f to,int color,int light){
        Matrix4f matrix = poseStack.last().pose();
        putTexturedQuad(buffer,matrix,texture,from,to,direction,color,light,0,true);
    }
    
    
    
}
