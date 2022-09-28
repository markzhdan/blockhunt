package me.cuft.blockhunt.Commands;

import me.cuft.blockhunt.BlockHunt;
import me.cuft.blockhunt.StartHunt;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Commands implements CommandExecutor
{
    public String cmd1 = "starthunt";

    private final BlockHunt main;

    public Commands(BlockHunt main)
    {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            if (command.getName().equalsIgnoreCase(cmd1))
            {
                StartHunt start = new StartHunt(main);

            }
        }
        else
        {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }
        return false;
    }
}
