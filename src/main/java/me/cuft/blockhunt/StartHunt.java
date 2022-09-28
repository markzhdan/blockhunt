package me.cuft.blockhunt;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class StartHunt
{
    private final BlockHunt main;
    private int counter = 10;
    private int timer = 600;

    public StartHunt(BlockHunt main)
    {
        this.main = main;
        oneTimeSetUp();
    }

    public void oneTimeSetUp()
    {
        main.setRandomBlocks();
        startGame();
    }

    public void startGame()
    {
        new BukkitRunnable() {
            @Override
            public void run() {
                //methods
                timer += 5;
                if(timer > 6000)
                {
                    timer = 0;
                    startTimer();
                }

                if(main.isStop2())
                {
                    timer = 0;
                    main.setStop2(false);
                }
            }
        }.runTaskTimer(main, 0, 5);
    }

    public void startTimer()
    {
        new BukkitRunnable() {
            @Override
            public void run() {
                //methods
                main.getServer().broadcastMessage(ChatColor.RED + "You have " + counter--  + " seconds left to find your block!");

                if(counter < 0)
                {
                    cancel();
                    main.checkIfPlayersGotBlocks();
                    counter = 10;
                }

                if(main.isStop())
                {
                    cancel();
                    counter = 10;
                    main.setStop(false);
                }
            }
        }.runTaskTimer(main, 0, 20);

    }
}
