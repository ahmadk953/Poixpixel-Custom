package com.poixpixelcustom.tasks;

import com.poixpixelcustom.PoixpixelCustom;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Board implements Runnable {

    private final static Board instance = new Board();

    private Board() {
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getScoreboard() != null && player.getScoreboard().getObjective(PoixpixelCustom.getInstance().getName()) != null)
                updateScoreboard(player);
            else
                createScoreboard(player);
        }
    }

    private void createScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(PoixpixelCustom.getInstance().getName(), "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.displayName(Component.text(PoixpixelCustom.getInstance().getName()).color(NamedTextColor.DARK_PURPLE).decorate(TextDecoration.BOLD));

        objective.getScore(ChatColor.LIGHT_PURPLE + " ").setScore(5);
        objective.getScore(ChatColor.WHITE + "This is a test scoreboard").setScore(4);
        objective.getScore(ChatColor.DARK_RED + " ").setScore(3);
        objective.getScore(ChatColor.AQUA + " ").setScore(1);
        objective.getScore(ChatColor.GREEN + "Plugin by ahmadk953").setScore(0);

        Team team = scoreboard.registerNewTeam("walk_distance");
        String teamKey = ChatColor.GOLD.toString();
        team.addEntry(teamKey);
        team.prefix(Component.text("Walked: "));
        team.suffix(Component.text("0cm"));

        objective.getScore(teamKey).setScore(2);
        player.setScoreboard(scoreboard);
    }

    private void updateScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Team team = scoreboard.getTeam("walk_distance");

        assert team != null;
        team.suffix(Component.text((player.getStatistic(Statistic.WALK_ONE_CM) + player.getStatistic(Statistic.SPRINT_ONE_CM)) + "cm").color(NamedTextColor.YELLOW));
    }

    public static Board getInstance() {
        return instance;
    }
}
