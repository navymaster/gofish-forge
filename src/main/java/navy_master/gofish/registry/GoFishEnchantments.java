package navy_master.gofish.registry;

import navy_master.gofish.GoFish;
import navy_master.gofish.enchantment.DeepfryEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class GoFishEnchantments {

    // 创建延迟注册器（需在主类初始化）
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, GoFish.MOD_ID);

    // 使用 RegistryObject 持有附魔实例
    public static final RegistryObject<Enchantment> DEEPFRY =
            register("deepfry", DeepfryEnchantment::new);

    // Forge 风格的注册方法
    private static RegistryObject<Enchantment> register(String name, Supplier<? extends Enchantment> supplier) {
        return ENCHANTMENTS.register(name, supplier);
    }
}

