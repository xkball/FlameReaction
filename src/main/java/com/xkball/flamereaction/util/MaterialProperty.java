package com.xkball.flamereaction.util;

import com.xkball.flamereaction.item.materialitem.MaterialItem;
import com.xkball.flamereaction.part.material.IMaterial;
import net.minecraft.world.item.Item;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MaterialProperty {
    //记录所有的该类实例用于查找
    private static final Map<IMaterial,MaterialProperty> materialMaterialPropertyMap = new HashMap<>();
    //记录属性：名称，颜色，tooltip
    private final String name;
    private final Color color;
    private final String[] tooltip;
    
    MaterialProperty(IMaterial material,Color color,String[] tooltip){
        this.color = color;
        this.tooltip = tooltip;
        this.name = material.getName();
        materialMaterialPropertyMap.put(material,this);
    }
    
    public static IMaterial create(IMaterial material){
        return create(material,null,null);
    }
    
    public static IMaterial create(IMaterial material,Color color){
        return create(material,color,null);
    }
    
    public static IMaterial create(IMaterial material,String[] tooltip){
        return create(material,null,tooltip);
    }
    
    
    public static IMaterial create(IMaterial material,Color color,String[] tooltip){
        if( getInstanceFromIMaterial(material)==null){
            new MaterialProperty(material,color,tooltip);
        }
        return material;
    }
    
    public String[] getTooltip() {
        return tooltip;
    }
    
    public Color getColor() {
        return color;
    }
    
    public String getName() {
        return name;
    }
    
    public static MaterialProperty getInstanceFromIMaterial(IMaterial material){
        return materialMaterialPropertyMap.get(material);
    }
    
    
}
