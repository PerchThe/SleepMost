package me.qintinator.sleepmost.repositories;

import me.qintinator.sleepmost.Main;
import me.qintinator.sleepmost.interfaces.IConfigRepository;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class ConfigRepository implements IConfigRepository {
    private final Main main;

    public ConfigRepository(Main main) {
        this.main = main;
    }


    @Override
    public double getPercentageRequired(World world) {
        return main.getConfig().getDouble(String.format("sleep.%s.percentage-required", world.getName()));
    }

    @Override
    public boolean containsWorld(World world) {
        return main.getConfig().contains(String.format("sleep.%s", world.getName()));
    }

    @Override
    public String getString(String string) {
        return main.getConfig().getString(string);
    }

    @Override
    public int getCooldown() {
        return main.getConfig().getInt("messages.cooldown");
    }

    @Override
    public boolean getMobNoTarget(World world) {
        return main.getConfig().getBoolean(String.format("sleep.%s.mob-no-target", world.getName()));
    }

    @Override
    public String getPrefix() {
        return main.getConfig().getString("messages.prefix");
    }
}
