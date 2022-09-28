package me.cuft.blockhunt;

import me.cuft.blockhunt.Commands.Commands;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public final class BlockHunt extends JavaPlugin implements Listener {

    private boolean stop = false;

    private boolean stop2 = false;

    public boolean isStop2() {
        return stop2;
    }

    public void setStop2(boolean stop2) {
        this.stop2 = stop2;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    List<Material> blocks = new ArrayList<Material>();

    HashMap<String, Material> playerBlock = new HashMap<String, Material>();

    HashMap<String, Boolean> blockFound = new HashMap<String, Boolean>();

    private final Commands commands = new Commands(this);

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand(commands.cmd1).setExecutor(commands);

        getServer().getPluginManager().registerEvents(this, this);

        getAvailableBlocks();

        for(Player player : getServer().getOnlinePlayers())
        {
            blockFound.put(player.getUniqueId().toString(), false);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void setRandomBlocks()
    {
        for(Player player : getServer().getOnlinePlayers())
        {
            Material randomBlock = blocks.get(new Random().nextInt(blocks.size()));
            playerBlock.put(player.getUniqueId().toString(), randomBlock);
            player.sendMessage(ChatColor.GREEN + "Your Random Block Is " + ChatColor.RED + randomBlock.name());
            getBlockFound().put(player.getUniqueId().toString(), false);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        Material playerBlock = getPlayerBlock().get(player.getUniqueId().toString());
        if(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == playerBlock) {
            if(!blockFound.get(player.getUniqueId().toString()))
            {
                getServer().broadcastMessage(ChatColor.GREEN + player.getDisplayName() + " has found their block");
                setBlockFound(player.getUniqueId().toString(), true);
                if(checkTrue())
                {
                    checkIfPlayersGotBlocks();
                    stop = true;
                    stop2 = true;
                }
            }
        }
    }

    public boolean checkTrue()
    {
        boolean oof = false;
        for(Player player : getServer().getOnlinePlayers())
        {
            if(blockFound.get(player.getUniqueId().toString()))
            {
                oof = true;
            }
            else
            {
                return false;
            }
        }
        return oof;
    }
    public void checkIfPlayersGotBlocks()
    {
        for(Player player : getServer().getOnlinePlayers())
        {
            if(!blockFound.get(player.getUniqueId().toString()))
            {
                getServer().broadcastMessage(ChatColor.RED + player.getDisplayName() + " did not find their block in time!");
                setRandomBlocks();
            }
            else
            {
                setRandomBlocks();
            }
        }
    }

    public HashMap<String, Boolean> getBlockFound() {
        return blockFound;
    }

    public void setBlockFound(String playerUUID, Boolean bool) {
        blockFound.put(playerUUID, bool);
    }

    public Material getRandomBlock()
    {
        return blocks.get(new Random().nextInt(blocks.size()));
    }

    public HashMap<String, Material> getPlayerBlock()
    {
        return playerBlock;
    }

    public void setPlayerBlock(String playerUUID, Material material)
    {
        playerBlock.put(playerUUID, material);
    }

    public void getAvailableBlocks()
    {
        for (Material block : Material.values()) {
            if (block.isBlock()) {
                blocks.add(block);
            }
        }
    }
}
