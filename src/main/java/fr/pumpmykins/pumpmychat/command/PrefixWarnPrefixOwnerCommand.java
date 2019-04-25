package fr.pumpmykins.pumpmychat.command;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.pumpmykins.pumpmychat.Main;
import fr.pumpmykins.pumpmychat.MySql;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixWarnPrefixOwnerCommand extends QSubCommand {

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return "rank.staff.moderateur";
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
		if(sender instanceof ProxiedPlayer) {
			
			if(sender.hasPermission("rank.staff.moderateur")) {
			
				ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[1]);
				
				if(p != null) {
					
					MySql mySQL = Main.getMySQL();
					mySQL.openConnection();
					if(mySQL.isConnected()) {
					
						ResultSet rs = Main.getMySQL().getResult(Main.REQUEST_GET_USER_PREFIX +"WHERE `uuid` = '"+p.getUniqueId()+"'");
						try {
							if(!rs.next()) {
								
								sender.sendMessage(Main.getERROR_NO_PREFIX());
								
							} else {
								
								rs.first();
								int warn = rs.getInt("warn");
								warn++;
								
								TextComponent desactive = new TextComponent("Préfix warn !");
								desactive.setColor(ChatColor.RED);
								sender.sendMessage(desactive);
								
								Main.getMySQL().update("UPDATE `PrefixPlayer` SET `warn`="+warn+" WHERE `uuid`= '"+p.getUniqueId()+"'");
								
								ProxyServer.getInstance().getPluginManager().dispatchCommand(sender, "prefix reload");
								
							}
							
							mySQL.closeConnection();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

}