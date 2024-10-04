package Kasteve.donald.lobbyCore;

import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Allay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.velocitypowered.api.proxy.ServerConnection;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class Eventone implements Listener {

    private final HashMap<UUID, Long> playerJoinTime = new HashMap<>();

    @EventHandler
    public void playerlogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage("ようこそ！");
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();

        playerJoinTime.put(player.getUniqueId(), System.currentTimeMillis());


        ItemStack navigationItem = new ItemStack(Material.COMPASS);
        ItemMeta meta = navigationItem.getItemMeta();
            meta.setDisplayName(ChatColor.LIGHT_PURPLE + ("サーバー選択"));
            List<String> lore = new ArrayList<>();

            lore.add(ChatColor.YELLOW + "アビリティ：" + ChatColor.ITALIC + ChatColor.LIGHT_PURPLE + "サーバー移動" + ChatColor.YELLOW + " 右クリック!");
            meta.setLore(lore);
            navigationItem.setItemMeta(meta);



        player.getInventory().setItem(0, navigationItem);
        Location location = new Location(player.getWorld(), 21, 11, 7);
        location.setYaw(180f);// 座標 (0, 0, 0) の位置を作成
        player.teleport(location);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.COMPASS) {
            UUID playerId = player.getUniqueId();

            long joinTime = playerJoinTime.getOrDefault(playerId, 0L);
            long timeSinceJoin = System.currentTimeMillis() - joinTime;
            int time = (int) ((6000 - timeSinceJoin) / 1000);

            if (timeSinceJoin < 5000) {
                player.sendMessage(ChatColor.AQUA + "サーバー移動クールダウン:" + ChatColor.RED + "あと" + time + "秒");
                player.sendMessage(ChatColor.AQUA + "/server <サーバー名>でクールダウンなしで移動できます。");
                event.setCancelled(true);
                return;
            }

            if (item != null && item.getType() == Material.COMPASS) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    Inventory customInventory = Bukkit.createInventory(null, 9, ChatColor.GREEN + "サーバー選択");

                    ItemStack grassBlock = new ItemStack(Material.GRASS_BLOCK);
                    ItemMeta grassMeta = grassBlock.getItemMeta();
                    grassMeta.setDisplayName(ChatColor.GREEN + "クリエイティブ");
                    grassBlock.setItemMeta(grassMeta);

                    // ダイヤモンドのつるはしを作成して設定
                    ItemStack diamondPickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
                    ItemMeta pickaxeMeta = diamondPickaxe.getItemMeta();
                    pickaxeMeta.setDisplayName(ChatColor.AQUA + "サバイバル");
                    diamondPickaxe.setItemMeta(pickaxeMeta);

                    // 鉄の剣を作成して設定
                    ItemStack ironSword = new ItemStack(Material.IRON_SWORD);
                    ItemMeta swordMeta = ironSword.getItemMeta();
                    swordMeta.setDisplayName(ChatColor.RED + "PVP");
                    ironSword.setItemMeta(swordMeta);

                    // インベントリにアイテムを配置
                    customInventory.setItem(0, grassBlock);
                    customInventory.setItem(4, diamondPickaxe);
                    customInventory.setItem(8, ironSword);

                    // プレイヤーにインベントリを開かせる
                    player.openInventory(customInventory);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // プレイヤーがクリックしたインベントリがカスタムインベントリか確認
        if (event.getView().getTitle().equals(ChatColor.GREEN + "サーバー選択")) {
            event.setCancelled(true); // アイテムを移動させないようにする

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null || !clickedItem.hasItemMeta()) return;

            String itemName = clickedItem.getItemMeta().getDisplayName();



            if (itemName.equals(ChatColor.GREEN + "クリエイティブ")) {

              //  player.sendMessage(ChatColor.GREEN + "クリエイティブモードのサーバーに移動します！");
                player.sendMessage(ChatColor.RED+ "このサーバーは準備中です。");


            } else if (itemName.equals(ChatColor.AQUA + "サバイバル")) {

                Location l2 = player.getLocation();
                player.sendMessage(ChatColor.AQUA + "サバイバルモードのサーバーに移動します！");
                Location location = new Location(player.getWorld(), 17.5, -2, 0.5);
                player.teleport(location);
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                player.teleport(l2);
//                player.sendMessage(ChatColor.RED + "移動に失敗しました。");

            } else if (itemName.equals(ChatColor.RED + "PVP")) {
                // PVP処理
             //   player.sendMessage(ChatColor.RED + "PVPサーバーに移動します！");
                player.sendMessage(ChatColor.RED+ "このサーバーは準備中です。");
            }
        }
    }

    public void ene(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }
}
