package dayjack.util.Location;


import dayjack.dao.LandInfo;
import dayjack.ymlFiles.PrivateLandFile;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class ReloadLandInfo {
    static List<LandInfo> landInfoList = new ArrayList<LandInfo>();
    static public void reload() {
        ConfigurationSection section = PrivateLandFile.config.getConfigurationSection("privateland");
        if (section == null)
            return;
        Object[] key = section.getKeys(false).toArray();
        landInfoList = new ArrayList<>();
        for(int i=0;i<key.length;i++) {
            Location location_low = (Location) PrivateLandFile.config.get("privateland." + key[i] + ".loc.location_low");
            Location location_max = (Location) PrivateLandFile.config.get("privateland." + key[i] + ".loc.location_max");
            String owner = (String) PrivateLandFile.config.get("privateland."+key[i]+".owner");
            LandInfo landInfo = new LandInfo(owner, location_low, location_max);
            landInfoList.add(landInfo);
            ConsoleCommandSender console = getServer().getConsoleSender();
            console.sendMessage(ChatColor.GREEN + " " + landInfo.getLocation_low().getX(),
                    " " + landInfo.getLocation_low().getY(), " " + landInfo.getLocation_low().getZ(),
                    " " + landInfo.getOwner());

        }

    }
}
