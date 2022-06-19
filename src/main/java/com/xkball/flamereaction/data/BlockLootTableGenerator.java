package com.xkball.flamereaction.data;

import com.mojang.datafixers.util.Pair;
import com.xkball.flamereaction.util.BlockList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;


/**
 * 所有方块都掉落自己
 */
public class BlockLootTableGenerator extends LootTableProvider {
    public BlockLootTableGenerator(DataGenerator p_124437_) {
        super(p_124437_);
    }
    
    
    //复制自正山小种
    @Nonnull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        // 此处指明 Data Generator 需生成的战利品表类别，此处仅需生成方块的战利品表
        return List.of(Pair.of(CustomBlockLoot::new, LootContextParamSets.BLOCK));
    }
    
    //复制自正山小种
    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationContext context) {
        // 模组自定义的战利品表 DataProvider 必须覆盖此方法，以绕过对原版战利品表的检查
        map.forEach((key, value) -> LootTables.validate(context, key, value));
    }
    
    //部分复制自正山小种
    public static class CustomBlockLoot extends BlockLoot {
        @Override
        protected void addTables() {
            for (Block block : BlockList.block_instance.values()) {
                 this.dropSelf(block);
            }
        }
    
        @Nonnull
        @Override
        protected Iterable<Block> getKnownBlocks() {
            // 模组自定义的方块战利品表必须覆盖此方法，以绕过对原版方块战利品表的检查（此处返回该模组的所有方块）
            return BlockList.block_instance.values();
        }
    }
}
    

