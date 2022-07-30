package com.xkball.flamereaction.data;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.eventhandler.register.FluidRegister;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class FluidTagGenerator extends FluidTagsProvider {
    public FluidTagGenerator(DataGenerator p_126523_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126523_, FlameReaction.MOD_ID, existingFileHelper);
    }
    
    @Override
    protected void addTags() {
        this.tag(FluidTags.WATER).add(FluidRegister.IMPURE_ALCOHOL_FLUID.get()).add(FluidRegister.IMPURE_ALCOHOL_FLUID_FLOWING.get());
    }
}
