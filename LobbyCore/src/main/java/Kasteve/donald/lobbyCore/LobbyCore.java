package Kasteve.donald.lobbyCore;


import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class LobbyCore extends JavaPlugin {


    @Override
    public void onEnable() {
        getLogger().info("______________");
        getLogger().info("LobbyCore 有効化");
        getLogger().info("______________");


        getCommand("sv").setExecutor(new ShortcutSV());

        //events
        getServer().getPluginManager().registerEvents(new Eventone(), this);

    }

    @Override
    public void onDisable() {
        getLogger().info("______________");
        getLogger().info("LobbyCore 無効化");
        getLogger().info("______________");
    }
}
