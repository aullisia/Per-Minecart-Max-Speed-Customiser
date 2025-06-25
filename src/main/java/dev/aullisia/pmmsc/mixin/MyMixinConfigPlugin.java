package dev.aullisia.pmmsc.mixin;

import me.fallenbreath.conditionalmixin.api.mixin.RestrictiveMixinConfigPlugin;

import java.util.List;
import java.util.Set;

public class MyMixinConfigPlugin extends RestrictiveMixinConfigPlugin{
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        boolean result = super.shouldApplyMixin(targetClassName, mixinClassName);
        System.out.println("Mixin " + mixinClassName + " applied? " + result);
        return result;
    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public void acceptTargets(Set<String> set, Set<String> set1) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }
}
