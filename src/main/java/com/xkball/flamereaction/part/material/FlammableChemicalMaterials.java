package com.xkball.flamereaction.part.material;

import com.xkball.flamereaction.util.PeriodicTableOfElements;
import net.minecraft.util.StringRepresentable;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Locale;

public enum FlammableChemicalMaterials implements IMaterial, StringRepresentable {
    //硼酸
    BORIC_ACID("boric_acid","H3BO3",new Color(197,253,106),PeriodicTableOfElements.B, true),
    //硫酸铜
    COPPER_SULFATE("copper_sulfate","CuSO4",new Color(104,179,111),PeriodicTableOfElements.Cu, true),
    //氯化钠
    SODIUM_CHLORIDE("sodium_chloride","NaCl",new Color(254,255,36),PeriodicTableOfElements.Na, true),
    //二氧化硒
    SELENIUM_DIOXIDE("selenium_dioxide","SeO2",new Color(181,244,253),PeriodicTableOfElements.Se, true),
    //钡盐
    BARIUM_SALT("barium_salt","Ba2+??",new Color(170,189,88),PeriodicTableOfElements.Ba, true),
    //钙盐
    CALCIUM_SALT("calcium_salt","Ca2+??",new Color(216,94,10),PeriodicTableOfElements.Ca, true),
    //铯盐
    CESIUM_SALT("cesium_salt","Cs+??",new Color(215,75,18), PeriodicTableOfElements.Cs, true),
    //锰盐
    MANGANESE_SALT("manganese_salt","Mn2+??",new Color(182,215,139),PeriodicTableOfElements.Mn, true),
    //钼盐
    MOLYBDENUM_SALT("molybdenum_salt","Mo+5???",new Color(124,146,94),PeriodicTableOfElements.Mo, true),
    //铅盐
    LEAD_SALT("lead_salt","Pd2+??",new Color(118,206,114),PeriodicTableOfElements.Pd, true),
    //铷盐
    RUBIDIUM_SALT("rubidium_salt","Rb+??",new Color(199,137,201),PeriodicTableOfElements.Rb, true),
    //锑盐
    ANTIMONY_SALT("antimony_salt","Sb3+??",new Color(133,189,136),PeriodicTableOfElements.Sb, true),
    //锌盐
    ZINC_SALT("zinc_salt","Zn2+??",new Color(100,197,196),PeriodicTableOfElements.Zn, true),
    //锂盐
    LITHIUM_SALT("lithium_salt","Li+??",new Color(239,104,170),PeriodicTableOfElements.Li, true),
    //锶盐
    STRONTIUM_SALT("strontium_salt","Sr2+??",new Color(237,36,105), PeriodicTableOfElements.Sr, true),
    //铟盐
    INDIUM_SALT("indium_salt","In2+/In3+??",new Color(246,216,245), PeriodicTableOfElements.In, true),
    
    //不明显区
    //铁盐
    IRON_SALT("iron_salt","Fe2+??",new Color(136,146,156),PeriodicTableOfElements.Fe, false),
    //钾盐
    POTASSIUM_SALT("potassium_salt","K+??",new Color(197,201,230), PeriodicTableOfElements.K, false),
    ;
    
    private final boolean isObvious;
    private final String name;
    private final Color color;
    private final String symbol;
    private final IMaterial material;
    private final PhaseState phaseState;
    
    FlammableChemicalMaterials(String name, String symbol, Color color, IMaterial material, boolean is_obvious) {
        this(is_obvious, name, symbol, color, material, PhaseState.SOLID);
    }
    
    FlammableChemicalMaterials(boolean is_obvious, String name, String symbol, Color color, IMaterial material, PhaseState phaseState) {
        this.isObvious = is_obvious;
        this.name = name;
        this.symbol = symbol;
        this.color = color;
        this.material = material;
        this.phaseState = phaseState;
    }
    
    public Color getColor() {
        return color;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getSymbol() {
        return symbol;
    }
    
    public IMaterial getMaterial() {
        return material;
    }
    
    public PhaseState getPhaseState() {
        return phaseState;
    }
    
    public boolean isObvious() {
        return isObvious;
    }
    
    @Override
    public @Nonnull String getSerializedName() {
        return this.toString().toLowerCase(Locale.ROOT);
    }
}
