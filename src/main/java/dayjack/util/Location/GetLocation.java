package dayjack.util.Location;


import org.bukkit.Location;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class GetLocation {
    static public Location getLocationByEvent(PlayerInteractEntityEvent event) {
        return event.getRightClicked().getLocation();
    }
    static public Location getLocationByEvent(EntityDamageByEntityEvent event) {
        return event.getEntity().getLocation();
    }
    static public Location getLocationByEvent(HangingBreakByEntityEvent event) {
        return event.getEntity().getLocation();
    }
    static public Location getLocationByEvent(BlockPlaceEvent event) {
        return event.getBlock().getLocation();
    }
    static public Location getLocationByEvent(BlockBreakEvent event) {
        return event.getBlock().getLocation();
    }
    static public Location getLocationByEvent(PlayerBucketEmptyEvent event) {
        return event.getPlayer().getLocation();
    }
    static Location getLocationByEvent(HangingPlaceEvent event) {
        return event.getPlayer().getLocation();
    }
}
