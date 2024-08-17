package de.jan.skyblock.builder;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.jan.skyblock.SkyBlock;
import de.jan.skyblock.component.ComponentSerializer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionType;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(@NotNull Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(@NotNull ItemStack stack) {
        this.itemStack = stack;
        this.itemMeta = stack.getItemMeta();
    }

    public ItemBuilder setDisplayName(@NotNull String string) {
        itemMeta.displayName(ComponentSerializer.deserialize(string));
        return this;
    }

    public ItemBuilder setDisplayName(@NotNull Component component) {
        itemMeta.displayName(component);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setUnbreakable() {
        itemMeta.setUnbreakable(true);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder setLore(@NotNull List<String> loreList) {
        itemMeta.setLore(loreList);
        return this;
    }

    public ItemBuilder setLore(@NotNull Component... lore) {
        List<Component> loreList = new ArrayList<>();
        Collections.addAll(loreList, lore);
        itemMeta.lore(loreList);
        return this;
    }

    public ItemBuilder setLore(@NotNull String... lore) {
        List<Component> loreList = new ArrayList<>();
        Arrays.stream(lore).forEach(string -> loreList.add(ComponentSerializer.deserialize(string)));
        itemMeta.lore(loreList);
        return this;
    }

    public ItemBuilder setPlayerHeadTexture(@NotNull URL skinTextureURL) {
        if(Material.PLAYER_HEAD.equals(this.itemStack.getType())) {
            PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID());
            PlayerTextures playerTextures = playerProfile.getTextures();
            playerTextures.setSkin(skinTextureURL);
            playerProfile.setTextures(playerTextures);

            SkullMeta skullMeta = (SkullMeta) itemMeta;
            skullMeta.setPlayerProfile(playerProfile);
            itemStack.setItemMeta(itemMeta);
            return this;
        }
        return this;
    }

    public ItemBuilder setSkull(@NotNull Player player) {
        if(!itemStack.getType().equals(Material.PLAYER_HEAD)) throw new IllegalArgumentException("Material " + itemStack.getType() + " must be an " + Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) itemMeta;
        skullMeta.setPlayerProfile(player.getPlayerProfile());
        return this;
    }

    public ItemBuilder setSkull(@NotNull String skullOwner) {
        if(!itemStack.getType().equals(Material.PLAYER_HEAD)) throw new IllegalArgumentException("Material " + itemStack.getType() + " must be an " + Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) itemMeta;
        GameProfile profile = new GameProfile(UUID.randomUUID(), skullOwner);
        profile.getProperties().put("textures", new Property("textures", skullOwner));
        Field profileField;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (Exception exception) {
            SkyBlock.Logger.info("error", exception);
        }
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    public ItemBuilder setFirework(int power, @NotNull Color color, @NotNull FireworkEffect.Type type) {
        if(!itemStack.getType().equals(Material.FIREWORK_ROCKET)) throw new IllegalArgumentException("Material " + itemStack.getType() + " must be an " + Material.FIREWORK_ROCKET);
        FireworkMeta fireworkMeta = (FireworkMeta) itemStack.getItemMeta();
        FireworkEffect.Builder builder = FireworkEffect.builder();
        builder.withColor(color);
        builder.with(type);
        fireworkMeta.addEffect(builder.build());
        fireworkMeta.setPower(power);
        itemStack.setItemMeta(fireworkMeta);
        return this;
    }

    public ItemBuilder setColoredLeatherArmor(@NotNull Color color) {
        Material type = itemStack.getType();
        if(type.equals(Material.LEATHER_BOOTS) || type.equals(Material.LEATHER_LEGGINGS) || type.equals(Material.LEATHER_CHESTPLATE) || type.equals(Material.LEATHER_HELMET)) {
            LeatherArmorMeta leatherMeta = (LeatherArmorMeta) itemMeta;
            leatherMeta.setColor(color);
            itemStack.setItemMeta(itemMeta);
        }
        return this;
    }

    public ItemBuilder addFlags(@NotNull ItemFlag... itemFlags) {
        itemMeta.addItemFlags(itemFlags);
        return this;
    }

    public ItemBuilder hideAllFlags() {
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_UNBREAKABLE);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean value) {
        itemMeta.setUnbreakable(value);
        return this;
    }

    public ItemBuilder addEnchantment(@NotNull Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, false);
        return this;
    }

    public ItemBuilder setCustomModelData(int customModelData) {
        itemMeta.setCustomModelData(customModelData);
        return this;
    }

    /*
    public ItemBuilder setTippedArrow(PotionType potionType) {
        if(!itemStack.getType().equals(Material.TIPPED_ARROW)) return this;
        PotionMeta potionMeta = (PotionMeta) itemMeta;
        potionMeta.setBasePotionType(potionType);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

     */

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return this.itemStack;
    }
}