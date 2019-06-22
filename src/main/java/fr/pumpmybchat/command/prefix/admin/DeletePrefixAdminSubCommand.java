package fr.pumpmybchat.command.prefix.admin;

import java.util.ArrayList;
import java.util.List;

import fr.pumpmybchat.Main;
import fr.pumpmybchat.command.utils.ISubCommand;
import fr.pumpmybchat.command.utils.ISubTabCompleter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class DeletePrefixAdminSubCommand implements ISubCommand,ISubTabCompleter {

	private Main main;

	public DeletePrefixAdminSubCommand(Main main) {
		this.main = main;
	}

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		if(sender instanceof ProxiedPlayer) {

			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("Console command only !");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			sender.sendMessage(txt);

			return;

		}else {

			if(args.size() == 1 && this.onTabComplete(exec, sender, args).contains(args.get(0).trim())) {

				ProxiedPlayer player = this.main.getProxy().getPlayer(args.get(0));

				try {
					this.main.getChatManager().deletePlayerPrefix(player);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}else {

				TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
				TextComponent txt1 = new TextComponent("Console command synthax error !");
				txt1.setColor(ChatColor.RED);
				txt.addExtra(txt1);			
				sender.sendMessage(txt);				
				return;

			}

		}

	}

	@Override
	public List<String> onTabComplete(Command command, CommandSender sender, List<String> args) {

		List<String> l = new ArrayList<>();

		for (ProxiedPlayer player : this.main.getProxy().getPlayers()) {
			l.add(player.getName());
		}

		return l;

	}

}
