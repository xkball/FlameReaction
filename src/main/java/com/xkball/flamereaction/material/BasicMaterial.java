package com.xkball.flamereaction.material;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemgroup.Groups;
import com.xkball.flamereaction.util.PeriodicTableOfElements;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BasicMaterial {
    public static List<IMaterial> materials = new ArrayList<>();
    public static void loadList(){
        materials.clear();
        materials.add(PeriodicTableOfElements.Rainbow);
        //是硼酸产生的
        materials.add(PeriodicTableOfElements.B);
        materials.add(PeriodicTableOfElements.Li);
        materials.add(PeriodicTableOfElements.Na);
        materials.add(PeriodicTableOfElements.Ca);
        materials.add(PeriodicTableOfElements.Ba);
        materials.add(PeriodicTableOfElements.Cs);
        materials.add(PeriodicTableOfElements.Cu);
        materials.add(PeriodicTableOfElements.Fe);
        materials.add(PeriodicTableOfElements.In);
        materials.add(PeriodicTableOfElements.K);
        materials.add(PeriodicTableOfElements.Mn);
        materials.add(PeriodicTableOfElements.Mo);
        materials.add(PeriodicTableOfElements.Pd);
        materials.add(PeriodicTableOfElements.Rb);
        materials.add(PeriodicTableOfElements.Sb);
        materials.add(PeriodicTableOfElements.Sr);
        materials.add(PeriodicTableOfElements.Tl);
        materials.add(PeriodicTableOfElements.Zn);
    }
    
    @SubscribeEvent
    public static void onRegisterItem(RegistryEvent.Register<Item> event) {
        if(materials.size() == 0){
            loadList();
        }
        for(IMaterial material : materials){
            event.getRegistry().register(new MaterialIngot(material));
            event.getRegistry().register(new MaterialPlate(material));
        }
        
    }
    
    static class MaterialIngot extends Item{
        public MaterialIngot(IMaterial material) {
            super(new Properties()
                    .fireResistant()
                    .setNoRepair()
                    .tab(Groups.MATERIAL_GROUP));
            this.setRegistryName(FlameReaction.MOD_ID,material.getName()+"_ingot");
        }
    }
    
    static class MaterialPlate extends Item{
        public MaterialPlate(IMaterial material){
            super((new Properties()
                    .fireResistant()
                    .setNoRepair()
                    .tab(Groups.MATERIAL_GROUP)));
            this.setRegistryName(FlameReaction.MOD_ID,material.getName()+"_plate");
        }
    }
}
