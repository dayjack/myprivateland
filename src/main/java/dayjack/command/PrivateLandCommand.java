package dayjack.command;




import dayjack.util.Location.ReloadLandInfo;
import dayjack.ymlFiles.PrivateLandFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import java.io.IOException;

import static org.bukkit.Bukkit.getServer;

/*
/privateland set [x] [y] [z] [x] [y] [z] [landname]
/privateland remove [landname]
/privateland give [username] [landname]
/privateland del [username] [landname]
* */

public class PrivateLandCommand implements CommandExecutor {
    ConsoleCommandSender console = getServer().getConsoleSender();
    PrivateLandFile privateLandFile = new PrivateLandFile();
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.isOp()) {
            commandSender.sendMessage(ChatColor.RED + "관리자 전용 명령어입니다!");
            return false;
        }

        switch (strings[0]) {
            case "set"->{
                try {
                    setCommand(strings[1], strings[2], strings[3], strings[4], strings[5], strings[6], strings[7]);
                    privateLandFile.config.save(privateLandFile.file);
                    ReloadLandInfo reloadLandInfo = new ReloadLandInfo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            case "remove"->{
                try {
                    removeCommand(strings[1]);
                    ReloadLandInfo reloadLandInfo = new ReloadLandInfo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            case "give" ->{
                try {
                    giveCommand(strings[1], strings[2]);
                    ReloadLandInfo reloadLandInfo = new ReloadLandInfo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            case "del" -> {
                try {
                    delCommand(strings[1], strings[2]);
                    ReloadLandInfo reloadLandInfo = new ReloadLandInfo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            default -> commandSender.sendMessage(ChatColor.RED + "잘못된 커맨드 입력입니다.");
        }
        return true;
    }

    void setCommand(String x1,String y1,String z1, String x2,String y2,String z2, String landname) throws IOException {
        int temp_x1 = Integer.parseInt(x1);
        int temp_y1 = Integer.parseInt(y1);
        int temp_z1 = Integer.parseInt(z1);
        int temp_x2 = Integer.parseInt(x2);
        int temp_y2 = Integer.parseInt(y2);
        int temp_z2 = Integer.parseInt(z2);
        int x_low = temp_x1 < temp_x2 ? temp_x1 : temp_x2;
        int x_max = temp_x1 > temp_x2 ? temp_x1 : temp_x2 ;
        int y_low = temp_y1 < temp_y2 ? temp_y1 : temp_y2 ;
        int y_max = temp_y1 > temp_y2 ? temp_y1 : temp_y2 ;
        int z_low = temp_z1 < temp_z2 ? temp_z1 : temp_z2 ;
        int z_max = temp_z1 > temp_z2 ? temp_z1 : temp_z2 ;

        Location location_low = new Location(Bukkit.getWorld("world"), x_low, y_low, z_low);
        Location location_max = new Location(Bukkit.getWorld("world"), x_max, y_max, z_max);

        privateLandFile.config.set("privateland." + landname + ".loc.location_low", location_low);
        privateLandFile.config.set("privateland." + landname + ".loc.location_max", location_max);
        privateLandFile.config.set("privateland."+landname+".loc.owner","");


        privateLandFile.config.save(privateLandFile.file);
        ReloadLandInfo.reload();

        console.sendMessage(ChatColor.GREEN+landname+" 좌표 설정\n"+"x_low: "+x_low+" y_low: "+y_low+" z1: "+z_low+
                "\nx_max: "+x_max+" y_max: "+y_max+" z_max: "+z_max);
    }

    void removeCommand(String landname) throws IOException {
        privateLandFile.config.set("privateland."+landname,null);
        privateLandFile.config.save(privateLandFile.file);
        ReloadLandInfo.reload();
        console.sendMessage(ChatColor.GREEN+landname+" 삭제 완료");
    }

    void giveCommand(String username, String landname) throws IOException {
        privateLandFile.config.set("privateland."+landname+".owner",username);
        privateLandFile.config.save(privateLandFile.file);
        ReloadLandInfo.reload();
        console.sendMessage(ChatColor.GREEN+landname+" 땅 "+username+"에게 지급");
    }

    void delCommand(String username, String landname) throws IOException {
        privateLandFile.config.set("privateland."+landname+".owner",null);
        privateLandFile.config.save(privateLandFile.file);
        ReloadLandInfo.reload();
        console.sendMessage(ChatColor.GREEN+landname+" 땅 권한 "+username+"에게서 삭제");
    }


}