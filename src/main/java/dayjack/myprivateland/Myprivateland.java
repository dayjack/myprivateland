package dayjack.myprivateland;


import dayjack.command.PrivateLandCommand;
import dayjack.util.Location.CheckLocationOwner;
import dayjack.util.Location.ReloadLandInfo;
import dayjack.ymlFiles.PrivateLandFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Myprivateland extends JavaPlugin implements Listener {
    ConsoleCommandSender console = getServer().getConsoleSender();
    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(this, this);
        getCommand("privateland").setExecutor(new PrivateLandCommand());
        PrivateLandFile.reloadConfig();
        ReloadLandInfo reloadLandInfo = new ReloadLandInfo();
        reloadLandInfo.reload();
        console.sendMessage(ChatColor.GREEN+"myprivateland plugin 실행!");
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    // TODO : func evnenthandler

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        if (event.getPlayer().isOp()) {
            return;
        }
        Material bucket = event.getBucket();
        if (bucket.toString().contains("LAVA") || bucket.toString().contains("WATER")) {
            CheckLocationOwner checkLocationOwner = new CheckLocationOwner(event);
            if (!checkLocationOwner.canUseLocation) {
                event.getPlayer().sendMessage(ChatColor.RED+"사유지에서는 양동이를 설치할 수 없습니다.");
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void paintingRotate (PlayerInteractEntityEvent event) {
        if (event.getPlayer().isOp()) {
            return;
        }
        Entity clicked = event.getRightClicked();
        if (clicked != null && clicked instanceof ItemFrame) {
            CheckLocationOwner checkLocationOwner = new CheckLocationOwner(event);
            if (!checkLocationOwner.canUseLocation) {
                event.getPlayer().sendMessage(ChatColor.RED+"땅 소유자만 파괴할 수 있습니다.");
                event.setCancelled(true);
                return;
            }
        }
    }
    @EventHandler
    public void paintingRotate1 (EntityDamageByEntityEvent event) {
        if (event.getDamager().isOp()) {
            return;
        }
        CheckLocationOwner checkLocationOwner = new CheckLocationOwner(event);
        if (event.getEntity() instanceof ItemFrame) {
            if (!checkLocationOwner.canUseLocation) {
                event.getDamager().sendMessage(ChatColor.RED+"땅 소유자만 파괴할 수 있습니다.");
                event.setCancelled(true);
                return;
            }
        }
    }
    @EventHandler
    public void album(HangingBreakByEntityEvent event) {
        if (event.getRemover().isOp()) {
            return;
        }
        CheckLocationOwner checkLocationOwner = new CheckLocationOwner(event);
        if (!checkLocationOwner.canUseLocation) {
            if (event.getEntity() instanceof ItemFrame || event.getEntity() instanceof Painting) {
                event.getRemover().sendMessage(ChatColor.RED+"땅 소유자만 파괴할 수 있습니다.");
                event.setCancelled(true);
                return;
            }
        }
        event.setCancelled(false);
    }

    @EventHandler
    public void hangingPrivate(HangingPlaceEvent event) {
        if (event.getEntity().isOp()) {
            return;
        }
        CheckLocationOwner checkLocationOwner = new CheckLocationOwner(event);
        if (!checkLocationOwner.canUseLocation) {
            if (event.getEntity() instanceof ItemFrame || event.getEntity() instanceof Painting) {
                event.getPlayer().sendMessage(ChatColor.RED+"땅 소유자만 설치할 수 있습니다.");
                event.setCancelled(true);
                event.getPlayer().updateInventory();
                return;
            }
        }
    }

    @EventHandler
    public void dontPlace(BlockPlaceEvent event) {
        if (event.getPlayer().isOp()) {
            return;
        }
        CheckLocationOwner checkLocationOwner = new CheckLocationOwner(event);
        if (!checkLocationOwner.canUseLocation) {
            event.getPlayer().sendMessage(ChatColor.RED+"사유지 입니다.\n땅 소유자만 설치 할 수 있습니다.");
            event.setCancelled(true);
            return;
        }
    }
    @EventHandler
    public void confirmMyLand(BlockBreakEvent event) {
        if (event.getPlayer().isOp()) {
            return;
        }
        CheckLocationOwner checkLocationOwner = new CheckLocationOwner(event);
        if (!checkLocationOwner.canUseLocation) {
            event.getPlayer().sendMessage(ChatColor.RED+"사유지 입니다.\n땅 소유자만 파괴 할 수 있습니다.");
            event.setCancelled(true);
            return;
        }
        event.setCancelled(false);
    }

}
