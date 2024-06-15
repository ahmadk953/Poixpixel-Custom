package com.poixpixelcustom.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ButterflyTask implements Runnable {

    private static final ButterflyTask instance = new ButterflyTask();

    private final Set<UUID> viewingWings = new HashSet<>();

    private ButterflyTask() {
    }

    /**
     * Executes the task that generates butterfly wing effects for online players.
     *
     */
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers())
            if (hasPlayer(player.getUniqueId()))
                generateButterflyWingEffect(player);
    }

    /**
     * Generates butterfly wing effects around the specified player location by spawning particles in a circular pattern.
     *
     * @param  player  the player for whom the butterfly wing effects are generated
     */
    private void generateButterflyWingEffect(Player player) {
        Location location = player.getLocation();

        location.add(location.getDirection().normalize().multiply(-0.3)); // move behind the player
        location.add(0, 0.85, 0); // push down to chest
        location.setPitch(0F); // stop vertical rotation, only make particles rotate to sides, not up and down

        double wingSize = 0.35;
        double circlesAmount = 4;

        for (double degree = 0; degree < 360; degree += 2 /* particle density */) {
            double radians = Math.toRadians(degree);

            double circle = wingSize * Math.pow(Math.E, Math.cos(radians));
            double radius = circle - Math.cos(circlesAmount * radians);

            double x = Math.sin(radians) * radius;
            double z = Math.cos(radians) * radius;

            Vector particleLocation = new Vector(x, 0, z);

            rotateAroundAxisX(particleLocation, -90);
            rotateAroundAxisY(particleLocation, location.getYaw());

            DustOptions dust = new DustOptions(Color.fromRGB(212, 146, 53), 0.6F);
            player.getWorld().spawnParticle(Particle.DUST, location.clone().add(particleLocation), 0, dust);
        }
    }

    /**
     * Rotates the given vector around the X-axis by the specified angle.
     *
     * @param  vector  the vector to rotate
     * @param  angle   the angle of rotation in degrees
     */
    private void rotateAroundAxisX(Vector vector, double angle) {
        angle = Math.toRadians(angle);

        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double y = vector.getY() * cos - vector.getZ() * sin;
        double z = vector.getY() * sin + vector.getZ() * cos;

        vector.setY(y).setZ(z);
    }

    /**
     * Rotates the given vector around the Y-axis by the specified angle.
     *
     * @param  vector  the vector to rotate
     * @param  angle   the angle of rotation in degrees
     */
    private void rotateAroundAxisY(Vector vector, double angle) {
        angle = -angle;
        angle = Math.toRadians(angle);

        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = vector.getX() * cos + vector.getZ() * sin;
        double z = vector.getX() * -sin + vector.getZ() * cos;

        vector.setX(x).setZ(z);
    }

    /**
     * Adds a player with the specified UUID to the list of players viewing wings.
     *
     * @param  uuid  the UUID of the player to add
     */
    public void addPlayer(UUID uuid) {
        viewingWings.add(uuid);
    }

    /**
     * Removes a player with the specified UUID from the list of players viewing wings.
     *
     * @param  uuid  the UUID of the player to remove
     */
    public void removePlayer(UUID uuid) {
        viewingWings.remove(uuid);
    }

    /**
     * Checks if the given UUID is present in the set of UUIDs of players viewing wings.
     *
     * @param        uuid  the UUID of the player to check
     * @return       true if the UUID is present in the set, false otherwise
     */
    public boolean hasPlayer(UUID uuid) {
        return viewingWings.contains(uuid);
    }

    /**
     * Returns the singleton instance of the ButterflyTask class.
     *
     * @return  the singleton instance of the ButterflyTask class
     */
    public static ButterflyTask getInstance() {
        return instance;
    }
}