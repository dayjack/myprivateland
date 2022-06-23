package dayjack.dao;

import lombok.Data;
import org.bukkit.Location;

@Data
public class LandInfo {
    String owner;
    Location location_low;
    Location location_max;
    public LandInfo(String ownerNickname, Location location_low, Location location_max) {
        this.owner = ownerNickname;
        this.location_low = location_low;
        this.location_max = location_max;
    }
}
