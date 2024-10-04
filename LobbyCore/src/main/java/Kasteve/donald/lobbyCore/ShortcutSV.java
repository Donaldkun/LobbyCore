package Kasteve.donald.lobbyCore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ShortcutSV implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("このコマンドはプレイヤーのみが実行できます。");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("sv")) {
            Location l2 = player.getLocation();
            player.sendMessage(ChatColor.AQUA + "サバイバルモードのサーバーに移動します！");
            Location location = new Location(player.getWorld(), 17.5, -2, 0.5); // 座標 (0, 0, 0) の位置を作成
            player.teleport(location);
         //   waiter(20);
          //  player.teleport(l2);
           // player.sendMessage(ChatColor.AQUA +"テレポートしました！"+ChatColor.WHITE + "ロビーです。");
            return true;
        }

        return false;
    }
    public void waiter(int tick){
        new BukkitRunnable() {

            private int ticksPassed = 0;

            @Override
            public void run() {
                ticksPassed++;
                if (ticksPassed > tick) { // 100 ticks = 5秒（1 tick毎にタスクを実行しているので）
                    this.cancel();
                }

            }
        }.runTaskTimer(JavaPlugin.getPlugin(LobbyCore.class), 0L, 1L);
    }
}
