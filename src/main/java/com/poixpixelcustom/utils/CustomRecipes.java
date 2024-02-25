package com.poixpixelcustom.utils;

import com.poixpixelcustom.PoixpixelCustom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class CustomRecipes {

    public static void register() {
        ItemStack superPaper = new ItemStack(org.bukkit.Material.PAPER);
        ItemMeta superPaperMeta = superPaper.getItemMeta();
        superPaperMeta.setDisplayName(ChatColor.GOLD + "Super Paper");
        superPaperMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        superPaperMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        superPaper.setItemMeta(superPaperMeta);

        ShapelessRecipe superPaperRecipe = new ShapelessRecipe(new NamespacedKey(PoixpixelCustom.getInstance(), "SuperPaperRecpie"), superPaper);
        superPaperRecipe.addIngredient(3, Material.BOOK);
        Bukkit.addRecipe(superPaperRecipe);

        ItemStack superSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta superSwordMeta = superSword.getItemMeta();
        superSwordMeta.setDisplayName(ChatColor.WHITE + "Super Sword");
        superSwordMeta.setLore(Arrays.asList("", ChatColor.GOLD + "Its a cheap super sword"));
        superSword.setItemMeta(superSwordMeta);

        FurnaceRecipe superSwordRecipe = new FurnaceRecipe(
                new NamespacedKey(PoixpixelCustom.getInstance(), "SuperSwordRecipe"),
                superSword,
                new RecipeChoice.ExactChoice(superPaper),
                10,
                2400
        );
        Bukkit.addRecipe(superSwordRecipe);

        ItemStack laserPointer = new ItemStack(Material.NETHER_STAR);
        ItemMeta laserPointerMeta = laserPointer.getItemMeta();
        laserPointerMeta.setDisplayName(ChatColor.WHITE + "Laser Pointer");
        laserPointer.setItemMeta(laserPointerMeta);

        ShapedRecipe laserPointerRecipe = new ShapedRecipe(new NamespacedKey(PoixpixelCustom.getInstance(), "LaserPointerRecipe"), laserPointer);
        laserPointerRecipe.shape(" X ", "XBX", " X ");
        laserPointerRecipe.setIngredient('X', superSword);
        laserPointerRecipe.setIngredient('B', new ItemStack(Material.BOOK));
        Bukkit.addRecipe(laserPointerRecipe);
    }
}
