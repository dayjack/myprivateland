package dayjack.util.Location;


import dayjack.dao.LandInfo;
import dayjack.ymlFiles.PrivateLandFile;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.Event;

import static org.bukkit.Bukkit.getServer;

public class CheckLocationOwner {
    ConsoleCommandSender console = getServer().getConsoleSender();
    public boolean canUseLocation;
    boolean ymlfileNull;
    boolean eventLocNotPrivate;
    boolean eventLocIsYours;
    boolean isNormalWorld;
    int index_list = 0;

    public CheckLocationOwner(Event event) {
        PrivateLandFile.reloadConfig();
        CheckEventLoc checkEventLoc = new CheckEventLoc(event);
        if (!checkEventLoc.eventLoc.getWorld().toString().equals("CraftWorld{name=world}")) {
            console.sendMessage(checkEventLoc.eventLoc.getWorld().toString());
            this.isNormalWorld = false;
            this.canUseLocation = true;
            return;
        }
        if (PrivateLandFile.config.getConfigurationSection("privateland") == null) {
            console.sendMessage("null");
            this.ymlfileNull = true;
            this.canUseLocation = true;
            return;
        }
        if (!isEventLocPrivate(event)) {
            console.sendMessage("isEventlocPrivate");
            this.eventLocNotPrivate = true;
            this.canUseLocation = true;
        }
        else {
            LandInfo landInfo = ReloadLandInfo.landInfoList.get(index_list);
            String eventLocOwner = landInfo.getOwner();
            String playerName = checkEventLoc.playerName;
            console.sendMessage("eventLocOwner: "+eventLocOwner);
            console.sendMessage("playerName: "+playerName);
            if (eventLocOwner.equals(playerName)) {
                this.eventLocIsYours = true;
                this.canUseLocation = true;
                return;
            } else {
                this.eventLocIsYours = false;
                this.canUseLocation = false;
                return;
            }
        }
    }
    public boolean isEventLocPrivate(Event event) {
        PrivateLandFile.reloadConfig();
        CheckEventLoc checkEventLoc = new CheckEventLoc(event);
        int x = (int) checkEventLoc.eventLoc.getX();
        int y = (int) checkEventLoc.eventLoc.getY();
        int z = (int) checkEventLoc.eventLoc.getZ();
        Location location_low;
        Location location_max;
        index_list = 0;
        //ReloadLandInfo.reload();
        for (LandInfo landInfo : ReloadLandInfo.landInfoList) {
            location_low = landInfo.getLocation_low();
            location_max = landInfo.getLocation_max();

            int x_low = (int) location_low.getX();
            int y_low = (int) location_low.getY();
            int z_low = (int) location_low.getZ();
            int x_max = (int) location_max.getX();
            int y_max = (int) location_max.getY();
            int z_max = (int) location_max.getZ();

            console.sendMessage("x y z: "+x+" "+y+" "+z);
            console.sendMessage("x_low y_low z_low: "+x_low+" "+y_low+" "+z_low);
            console.sendMessage("x_max y_max z_amx: "+x_max+" "+y_max+" "+z_max);

            if (x_low < x && x < x_max && y_low < y && y < y_max && z_low < z && z < z_max) {
                console.sendMessage("check");
                return true;
            }
            index_list++;
        }
        return false;
    }
}
