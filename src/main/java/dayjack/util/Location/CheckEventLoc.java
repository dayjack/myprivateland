package dayjack.util.Location;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class CheckEventLoc {
    Location eventLoc;
    String playerName;
    public CheckEventLoc(Event event) {

        if (event instanceof PlayerInteractEntityEvent) {
            PlayerInteractEntityEvent playerInteractEntityEvent = (PlayerInteractEntityEvent) event;
            playerName = playerInteractEntityEvent.getPlayer().getName();
            playerName = ChatColor.stripColor(playerName);
            this.eventLoc = GetLocation.getLocationByEvent(playerInteractEntityEvent);
        }
        else if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) event;
            playerName = entityDamageByEntityEvent.getDamager().getName();
            playerName = ChatColor.stripColor(playerName);
            this.eventLoc = GetLocation.getLocationByEvent(entityDamageByEntityEvent);
        }
        else if (event instanceof HangingBreakByEntityEvent) {
            HangingBreakByEntityEvent hangingBreakByEntityEvent = (HangingBreakByEntityEvent) event;
            playerName = hangingBreakByEntityEvent.getRemover().getName();
            playerName = ChatColor.stripColor(playerName);
            this.eventLoc = GetLocation.getLocationByEvent(hangingBreakByEntityEvent);
        }
        else if (event instanceof BlockPlaceEvent) {
            BlockPlaceEvent blockPlaceEvent = (BlockPlaceEvent) event;
            playerName = blockPlaceEvent.getPlayer().getName();
            playerName = ChatColor.stripColor(playerName);
            this.eventLoc = GetLocation.getLocationByEvent(blockPlaceEvent);
        }
        else if (event instanceof BlockBreakEvent) {
            BlockBreakEvent blockBreakEvent = (BlockBreakEvent) event;
            playerName = blockBreakEvent.getPlayer().getDisplayName();
            playerName = ChatColor.stripColor(playerName);
            this.eventLoc = GetLocation.getLocationByEvent(blockBreakEvent);
        } else if (event instanceof PlayerBucketEmptyEvent) {
            PlayerBucketEmptyEvent playerBucketEmptyEvent = (PlayerBucketEmptyEvent) event;
            playerName = playerBucketEmptyEvent.getPlayer().getName();
            playerName = ChatColor.stripColor(playerName);
            this.eventLoc = GetLocation.getLocationByEvent(playerBucketEmptyEvent);
        } else if (event instanceof HangingPlaceEvent) {
            HangingPlaceEvent hangingPlaceEvent = (HangingPlaceEvent) event;
            playerName = hangingPlaceEvent.getPlayer().getName();
            playerName = ChatColor.stripColor(playerName);
            this.eventLoc = GetLocation.getLocationByEvent(hangingPlaceEvent);
        }
    }
}
