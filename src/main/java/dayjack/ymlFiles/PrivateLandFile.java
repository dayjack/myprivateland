package dayjack.ymlFiles;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;


public class PrivateLandFile {
    static public FileConfiguration config;
    final static public File file = new File("plugins/PrivateLandFile/privatelandData.yml");
    public PrivateLandFile() {
        reloadConfig();
    }
    static public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
        try {
            if (!file.exists()) {
                config.save(file);
            }
            config.load(file);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }
}