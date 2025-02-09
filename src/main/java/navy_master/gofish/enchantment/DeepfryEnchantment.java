package navy_master.gofish.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DeepfryEnchantment extends Enchantment {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "your_mod_id");

    public static final RegistryObject<Enchantment> DEEPFRY =
            ENCHANTMENTS.register("deepfry", DeepfryEnchantment::new);

    public DeepfryEnchantment() {
        super(
                Rarity.RARE,
                EnchantmentCategory.FISHING_ROD,
                new EquipmentSlot[] {
                        EquipmentSlot.MAINHAND,
                        EquipmentSlot.OFFHAND
                }
        );
    }

    @Override
    public int getMinCost(int level) {
        return 15;
    }

    @Override
    public int getMaxCost(int level) {
        return super.getMinCost(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
