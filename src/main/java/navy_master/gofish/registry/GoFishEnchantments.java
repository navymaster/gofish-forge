package navy_master.gofish.registry;

import navy_master.gofish.GoFish;
import navy_master.gofish.enchantment.DeepfryEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

//深度烧烤附魔
public class GoFishEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, GoFish.MOD_ID);

    public static final RegistryObject<Enchantment> DEEPFRY =
            register("deepfry", DeepfryEnchantment::new);

    private static RegistryObject<Enchantment> register(String name, Supplier<? extends Enchantment> supplier) {
        return ENCHANTMENTS.register(name, supplier);
    }
}

