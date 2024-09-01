package de.jan.skyblock.builder;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
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
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    private final List<String> loreList;

    public ItemBuilder(@NotNull Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
        this.loreList = new ArrayList<>();
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

    public ItemBuilder setLore(@NotNull String... lore) {
        if(lore.length == 0) return this;
        loreList.addAll(Arrays.asList(lore));
        return this;
    }

    public ItemBuilder setLoreString(@NotNull List<String> lore) {
        if(lore.isEmpty()) return this;
        loreList.addAll(lore);
        return this;
    }

    public ItemBuilder setLore(@NotNull Component... lore) {
        Arrays.stream(lore).forEach(component -> loreList.add(ComponentSerializer.serialize(component)));
        return this;
    }

    private ItemBuilder setLore(@NotNull List<Component> lore) {
        lore.forEach(component -> loreList.add(ComponentSerializer.serialize(component)));
        return this;
    }

    public ItemBuilder setSkull(@NotNull Player player) {
        if(!itemStack.getType().equals(Material.PLAYER_HEAD)) throw new IllegalArgumentException("Material " + itemStack.getType() + " must be an " + Material.PLAYER_HEAD);
        String texture = getTexture(player);
        if(texture == null) return this;
        PlayerProfile profile = Bukkit.getServer().createProfile(UUID.nameUUIDFromBytes(texture.getBytes()), player.getName());
        profile.setProperty(new ProfileProperty("textures", texture));
        profile.complete(true, true);
        SkullMeta skullMeta = (SkullMeta) itemMeta;
        skullMeta.setPlayerProfile(profile);
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    private String getTexture(Player player) {
        PlayerProfile profile = player.getPlayerProfile();
        for(ProfileProperty property : profile.getProperties()) {
            if(property.getName().equals("textures")) return property.getValue();
        }
        return null;
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
        if(loreList != null) {
            List<Component> componentList = new ArrayList<>();
            loreList.forEach(string -> componentList.add(ComponentSerializer.deserialize(string)));
            itemMeta.lore(componentList);
        }
        itemStack.setItemMeta(itemMeta);
        return this.itemStack;
    }
}
