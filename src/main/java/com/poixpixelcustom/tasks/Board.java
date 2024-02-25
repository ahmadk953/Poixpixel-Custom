package com.poixpixelcustom.tasks;

import com.poixpixelcustom.PoixpixelCustom;

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
        objective.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + PoixpixelCustom.getInstance().getName());

        objective.getScore(ChatColor.LIGHT_PURPLE + " ").setScore(6);
        objective.getScore(ChatColor.WHITE + "This is a test scoreboard").setScore(5);
        objective.getScore(ChatColor.DARK_RED + " ").setScore(4);
        objective.getScore(ChatColor.AQUA + " ").setScore(2);
        objective.getScore(ChatColor.GREEN + "Plugin by ahmadk953 for Poixpixel").setScore(1);
        objective.getScore(ChatColor.RED + "Plugin Version: " + PoixpixelCustom.getInstance().getDescription().getVersion()).setScore(0);

        Team team = scoreboard.registerNewTeam("walk_distance");
        String teamKey = ChatColor.GOLD.toString();
        team.addEntry(teamKey);
        team.setPrefix("Walked: ");
        team.setSuffix("0cm");

        objective.getScore(teamKey).setScore(3);
        player.setScoreboard(scoreboard);
    }

    private void updateScoreboard(Player player) {
        Scoreboard scoreboard = player.getScoreboard();
        Team team = scoreboard.getTeam("walk_distance");

        team.setSuffix(ChatColor.YELLOW + "" + (player.getStatistic(Statistic.WALK_ONE_CM) + player.getStatistic(Statistic.SPRINT_ONE_CM)) + "cm");
    }

    public static Board getInstance() {
        return instance;
    }
}
