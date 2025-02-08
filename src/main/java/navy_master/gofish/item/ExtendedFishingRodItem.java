package navy_master.gofish.item;

import navy_master.gofish.api.*;
import navy_master.gofish.registry.GoFishEnchantments;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.ChatFormatting;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ExtendedFishingRodItem extends FishingRodItem {

    private final SoundInstance retrieve;
    private final SoundInstance cast;
    private final int baseLure;
    private final int baseLOTS;
    private final int baseExperience;
    private final boolean autosmelt;
    private final boolean lavaProof;
    private final boolean nightLuck;
    private final ChatFormatting formatting;
    private final int lines;

    public ExtendedFishingRodItem(Properties properties, SoundInstance retrieve, SoundInstance cast, int baseLure, int baseLOTS, int baseExperience, boolean autosmelt, boolean lavaProof, boolean nightLuck, ChatFormatting formatting, int tooltipLines) {
        super(properties);
        this.retrieve = retrieve;
        this.cast = cast;
        this.baseLure = baseLure;
        this.baseLOTS = baseLOTS;
        this.baseExperience = baseExperience;
        this.autosmelt = autosmelt;
        this.lavaProof = lavaProof;
        this.nightLuck = nightLuck;
        this.formatting = formatting;
        this.lines = tooltipLines;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        RandomSource random = world.getRandom();

        // 处理收回钓线逻辑
        if (player.fishing != null) {
            if (!world.isClientSide) {
                int damage = player.fishing.retrieve(stack);
                stack.hurtAndBreak(damage, player, p -> p.broadcastBreakEvent(hand));
            }

            world.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    retrieve.getSound(),
                    SoundSource.NEUTRAL,
                    retrieve.getVolume(random),
                    retrieve.getPitch(random)
            );
        }
        // 处理抛出钓线逻辑
        else {
            world.playSound(
                    null,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    cast.getSound(),
                    SoundSource.NEUTRAL,
                    cast.getVolume(random),
                    cast.getPitch(random)
            );

            if (!world.isClientSide) {
                // 初始化加成变量
                boolean smeltBuff = false;
                int bonusLure = 0;
                int bonusLuck = 0;
                int bonusExperience = 0;

                // 夜间幸运加成
                if (nightLuck && world.isNight()) {
                    bonusLuck++;
                }

                // 遍历物品栏寻找加成物品
                List<FishingBonus> foundBonuses = new ArrayList<>();
                for (ItemStack inventoryStack : player.getInventory().items) {
                    Item item = inventoryStack.getItem();
                    if (item instanceof FishingBonus bonus && !foundBonuses.contains(bonus)) {
                        if (bonus.shouldApply(world, player)) {
                            foundBonuses.add(bonus);
                            smeltBuff |= bonus.providesAutosmelt();
                            bonusLure += bonus.getLure();
                            bonusLuck += bonus.getLuckOfTheSea();
                            bonusExperience += bonus.getBaseExperience();
                        }
                    }
                }

                // 计算自动熔炼状态
                boolean hasDeepfry = stack.getEnchantmentLevel(GoFishEnchantments.DEEPFRY.get()) > 0;
                boolean rodAutosmelt = stack.getItem() instanceof ExtendedFishingRodItem rod && rod.autosmelts();
                boolean finalSmelt = hasDeepfry || rodAutosmelt || smeltBuff;

                // 计算最终属性
                int finalLure = Math.min(
                        EnchantmentHelper.getFishingSpeedBonus(stack) + baseLure + bonusLure,
                        5
                );
                int finalLuck = EnchantmentHelper.getFishingLuckBonus(stack) + baseLOTS + bonusLuck;

                // 创建并配置钓鱼钩实体
                FishingHook hook = new FishingHook(player, world, finalLuck, finalLure);
                if (hook instanceof FireproofEntity fireproof) fireproof.gf_setFireproof(lavaProof);
                if (hook instanceof SmeltingBobber smelting) smelting.gf_setSmelts(finalSmelt);
                if (hook instanceof ExperienceBobber exp) exp.gf_setBaseExperience(baseExperience + bonusExperience);

                world.addFreshEntity(hook);
            }

            // 更新使用统计
            player.awardStat(Stats.ITEM_USED.get(this));
        }

        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }


    @Override
    public Component getName(ItemStack stack) {
        Component name = super.getName(stack);
        if (name instanceof MutableComponent mutable) {
            return mutable.withStyle(formatting);
        }
        return name;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);

        if (lines > 0) {
            for (int i = 1; i <= lines; i++) {
                tooltip.add(Component.translatable(String.format("%s.tooltip_%d", getDescriptionId(), i)).withStyle(ChatFormatting.GRAY));
            }
        }
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    public boolean autosmelts() {
        return autosmelt;
    }

    @Override
    public int getEnchantmentValue () {
        return 1;
    }

    public boolean canFishInLava() {
        return lavaProof;
    }

    public static class Builder {
        private Item.Properties properties = new Item.Properties().durability(100);
        private SoundInstance retrieve = new SoundInstance(SoundEvents.FISHING_BOBBER_RETRIEVE, 1.0F, SoundInstance.DEFAULT_PITCH);
        private SoundInstance cast = new SoundInstance(SoundEvents.FISHING_BOBBER_THROW, 0.5F, SoundInstance.DEFAULT_PITCH);
        private int baseLure = 0;
        private int baseLOTS = 0;
        private int experience = 1;
        private boolean autosmelt = false;
        private boolean lavaProof = false;
        private boolean nightLuck = false;
        private ChatFormatting formatting = ChatFormatting.WHITE;
        private int tooltipLines = 0;

        public Builder durability(int durability) {
            this.properties.durability(durability);
            return this;
        }
        public Builder() {

        }

        public Builder withSettings(Item.Properties settings) {
            this.properties = settings;
            return this;
        }

        public Builder color(ChatFormatting formatting) {
            this.formatting = formatting;
            return this;
        }

        public Builder withRetrieveSound(SoundInstance sound) {
            this.retrieve = sound;
            return this;
        }

        public Builder withCastSound(SoundInstance sound) {
            this.cast = sound;
            return this;
        }

        public Builder withBaseLure(int lure) {
            this.baseLure = lure;
            return this;
        }

        public Builder withBaseLOTS(int lots) {
            this.baseLOTS = lots;
            return this;
        }

        public Builder baseExperienceGain(int experience) {
            this.experience = experience;
            return this;
        }

        public Builder autosmelt() {
            this.autosmelt = true;
            return this;
        }

        public Builder lavaProof(boolean lavaProof) {
            this.lavaProof = lavaProof;
            return this;
        }

        public Builder nightLuck() {
            this.nightLuck = true;
            return this;
        }

        public Builder tooltipLines(int tooltipLines) {
            this.tooltipLines = tooltipLines;
            return this;
        }

        public ExtendedFishingRodItem build() {
            return new ExtendedFishingRodItem(properties, retrieve, cast, baseLure, baseLOTS, experience, autosmelt, lavaProof, nightLuck, formatting, tooltipLines);
        }
    }
}
