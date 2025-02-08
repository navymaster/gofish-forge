
//你是一个资深的我的世界mod开发者，深谙forge和fabric提供的API之间的差异和使用，用户正在尝试将一个我很喜欢的我的世界的mod从fabric移植到forge，请你帮助他进行代码的移植，移植前后的mc版本都是1.20.2

//请帮忙将这个关于附魔的声明代码移植到forge：
package navy_master.gofish.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DeepfryEnchantment extends Enchantment {

    // 创建 DeferredRegister（需在主 mod 类初始化）
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, "your_mod_id");

    // 注册附魔（需在主 mod 类构造器注册）
    public static final RegistryObject<Enchantment> DEEPFRY =
            ENCHANTMENTS.register("deepfry", DeepfryEnchantment::new);

    public DeepfryEnchantment() {
        super(
                Rarity.RARE,
                EnchantmentCategory.FISHING_ROD, // Forge 使用 EnchantmentCategory
                new EquipmentSlot[] {
                        EquipmentSlot.MAINHAND,
                        EquipmentSlot.OFFHAND
                }
        );
    }

    @Override
    public int getMinCost(int level) {  // Forge 1.20.2 重命名为 getMinCost
        return 15;
    }

    @Override
    public int getMaxCost(int level) {  // Forge 1.20.2 重命名为 getMaxCost
        return super.getMinCost(level) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
