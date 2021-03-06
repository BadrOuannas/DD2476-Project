2
https://raw.githubusercontent.com/devwckd/wckd-vips/master/src/main/java/co/wckd/vips/command/VipRankCommand.java
package co.wckd.vips.command;

import co.wckd.vips.VipsPlugin;
import co.wckd.vips.cache.VipPlayerCache;
import co.wckd.vips.entity.Vip;
import co.wckd.vips.entity.VipPlayer;
import co.wckd.vips.entity.VipType;
import co.wckd.vips.util.TimeUtils;
import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;
import me.saiintbrisson.commands.argument.Argument;
import org.bukkit.entity.Player;

public class VipRankCommand {

    private final VipsPlugin plugin;
    private final VipPlayerCache vipPlayerCache;

    public VipRankCommand(VipsPlugin plugin) {
        this.plugin = plugin;
        this.vipPlayerCache = plugin.getVipPlayerLifecycle().getVipPlayerCache();
    }

    @Command(
            name = "wickedvips.rank"
    )
    public void onWVRankCommand(Execution execution) {

        execution.sendMessage(new String[]{
                " ",
                " §6§lWICKEDVIPS §8- §fRanks Help.",
                " ",
                " §8➟ §e/wv rank give <player> <group> [duration] §8- §fGives a VIP rank to the player.",
                " §8➟ §e/wv rank remove <player> <group> [duration] §8- §fRemoves a VIP rank from the player.",
                " "
        });

    }

    @Command(
            name = "wickedvips.rank.give"
    )
    public void onWVRankGive(
            Execution execution,
            Player player,
            VipType type,
            @Argument(nullable = true) String timeString
    ) {

        VipPlayer vipPlayer = vipPlayerCache.find(player.getUniqueId());
        if (vipPlayer == null) {
            System.out.println("deu merda menó");
            return;
        }

        Long time = TimeUtils.millisFromString(timeString);
        if (time == null) {
            execution.sendMessage("Wrong time");
            return;
        }

        Vip vip = Vip
                .builder()
                .type(type)
                .time(time)
                .build();

        vip.activate(vipPlayer);

    }

}
