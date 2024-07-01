package net.keinepixel;

import org.bukkit.plugin.java.JavaPlugin;

public final class LobbyPlugin extends JavaPlugin {

    PixelAPI pixelAPI;

    @Override
    public void onEnable() {

        pixelAPI = (PixelAPI) getServer().getPluginManager().getPlugin("PixelAPI");
        if (pixelAPI == null) {
            getLogger().severe("PixelAPI not found! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }


    }

    @Override
    public void onDisable() {

    }
}
