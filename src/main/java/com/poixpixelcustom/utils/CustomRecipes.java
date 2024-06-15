package com.poixpixelcustom.utils;

import com.poixpixelcustom.PoixpixelCustom;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public final class CustomRecipes {

    private CustomRecipes() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Registers custom recipes for the game.
     *
     * This function creates and adds custom recipes to the game's recipe manager.
     * It creates a recipe for "Super Paper" using the ShapelessRecipe class,
     * and a recipe for "Super Sword" using the FurnaceRecipe class.
     * It also creates a recipe for "Laser Pointer" using the ShapedRecipe class.
     * The recipes are added to the Bukkit recipe manager using the addRecipe method.
     *
     */
    public static void register() {
        ItemStack superPaper = new ItemStack(org.bukkit.Material.PAPER);
        ItemMeta superPaperMeta = superPaper.getItemMeta();
        superPaperMeta.displayName(Component.text("Super Paper").color(NamedTextColor.GOLD));
        superPaperMeta.addEnchant(Enchantment.FIRE_PROTECTION, 1, true);
        superPaperMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        superPaper.setItemMeta(superPaperMeta);

        ShapelessRecipe superPaperRecipe = new ShapelessRecipe(new NamespacedKey(PoixpixelCustom.getInstance(), "SuperPaperRecipe"), superPaper);
        superPaperRecipe.addIngredient(3, Material.BOOK);
        Bukkit.addRecipe(superPaperRecipe);

        ItemStack superSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta superSwordMeta = superSword.getItemMeta();
        superSwordMeta.displayName(Component.text("Super Sword").color(NamedTextColor.WHITE));
        superSwordMeta.lore(Arrays.asList(
                Component.text(""),
                Component.text("Its a cheap super sword").color(NamedTextColor.GOLD)
        ));
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
        laserPointerMeta.displayName(Component.text("Laser Pointer").color(NamedTextColor.WHITE));
        laserPointer.setItemMeta(laserPointerMeta);

        ShapedRecipe laserPointerRecipe = new ShapedRecipe(new NamespacedKey(PoixpixelCustom.getInstance(), "LaserPointerRecipe"), laserPointer);
        laserPointerRecipe.shape(" X ", "XBX", " X ");
        laserPointerRecipe.setIngredient('X', superSword);
        laserPointerRecipe.setIngredient('B', new ItemStack(Material.BOOK));
        Bukkit.addRecipe(laserPointerRecipe);
    }
}
