package net.keinepixel;

import eu.koboo.atcommand.CommandEnvironment;
import eu.koboo.atcommand.CommandEnvironmentSpigot;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.keinepixel.commands.binder.ErrorBinder;
import net.keinepixel.commands.binder.NoPermissionBinder;
import net.keinepixel.commands.binder.OnlyConsoleBinder;
import net.keinepixel.commands.binder.OnlyPlayerBinder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.logging.Level;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class LobbyPlugin extends JavaPlugin {

    PixelAPI pixelAPI;

    final Component prefix = MiniMessage.miniMessage().deserialize("<gradient:#ecff00:#ecff00><bold>KeinePixel</bold></gradient><dark_gray> • </dark_gray><gray>");

    CommandEnvironment<JavaPlugin> commandEnvironment;

    //CloudAPI.getInstance().getCloudServiceGroupManager().getLobbyGroups()

    @Override
    public void onEnable() {
        pixelAPI = (PixelAPI) getServer().getPluginManager().getPlugin("PixelAPI");
        if (pixelAPI == null) {
            getLogger().severe("PixelAPI not found! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        commandEnvironment = new CommandEnvironmentSpigot(this);

        commandEnvironment.binder(new ErrorBinder(this));
        commandEnvironment.binder(new NoPermissionBinder(this));
        commandEnvironment.binder(new OnlyConsoleBinder(this));
        commandEnvironment.binder(new OnlyPlayerBinder(this));

        this.registerListeners();
        this.registerCommands();

        this.getLogger().log(Level.INFO, "Plugin enabled!");

    }

    @Override
    public void onDisable() {
        this.getLogger().log(Level.INFO, "Plugin disabled!");
    }

    private void registerListeners() {
        Reflections reflections = new Reflections("net.keinepixel.listener");
        reflections.getSubTypesOf(org.bukkit.event.Listener.class).forEach(listener -> {
            try {
                getServer().getPluginManager().registerEvents(listener.getDeclaredConstructor(LobbyPlugin.class).newInstance(this), this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void registerCommands() {
        Reflections reflections = new Reflections("net.keinepixel.commands");
        reflections.getSubTypesOf(eu.koboo.atcommand.api.Command.class).forEach(command -> {
            try {
                commandEnvironment.command(this, command.getDeclaredConstructor(LobbyPlugin.class).newInstance(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
