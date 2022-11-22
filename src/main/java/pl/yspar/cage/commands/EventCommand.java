package pl.yspar.cage.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.yspar.cage.basic.Cage;
import pl.yspar.cage.basic.Party;
import pl.yspar.cage.commands.manager.PlayerCommand;
import pl.yspar.cage.config.Config;
import pl.yspar.cage.manager.PartyManager;
import pl.yspar.cage.types.CageType;
import pl.yspar.cage.utils.Util;

public class EventCommand extends PlayerCommand {
    public EventCommand() {
        super("event", "informacje o evencie", "/event", "cage.cmd.user", "klatki");
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        final Party g = PartyManager.getParty(p);
        if (args.length != 1) {
            Util.sendMessage(p, "&8\u27ba &7/event zapisz &8- &7zapisuje na event");
            Util.sendMessage(p, "&8\u27ba &7/obstaw &8- &7obstawia walki");
            return false;
        }
        if (args[0].equals("info")) {
            Util.sendMessage(p, "&6Zapisani" + Cage.partySave + "&7Test: " + g.getFightAs() + "&eTesst2: " + Cage.partySave.size());
            Util.sendMessage(p, "&6Przechodza" + Cage.partyPass);
            return false;
        }
        if (!args[0].equals("zapisz")) {
            return false;
        }
        if (g == null) {
            Util.sendTitleMessage(p, "", "&cAby zapisac sie na klatki musisz posiadac party.", 30, 70, 40);
            return false;
        }
        if (Cage.statusCage != CageType.WAITING) {
            Util.sendTitleMessage(p, "", "&cBrak eventu lub juz wystartowal.", 30, 70, 40);
            return false;
        }
        if (Cage.partySave.contains(g.getTag())) {
            Util.sendMessage(p, "&cTwoje party jest juz zapisane na klatki");
            return false;
        }
        if (!g.isOwner(p)) {
            Util.sendTitleMessage(p, "", "&cTylko lider party to moze zrobic", 30, 70, 40);
            return false;
        }
        if (g.getMembers().size() > Cage.xvsx) {
            Util.sendMessage(p, "&cPosiadasz za duzo osob party");
            return false;
        }
        if (g.getMembers().size() < Cage.xvsx) {
            Util.sendMessage(p, "&cPosiadasz za malo osob party");
            return false;
        }
        final int fightAs = Cage.partySave.size();
        Cage.partySave.add(g.getTag());
        g.setPoints(g.getMembers().size());
        g.setFightAs(fightAs);
        Util.sendTitleMessage(p, "", Util.fixColor(Config.getInstance().messages.savePartyMessages).replace("{ROUND}", Integer.toString(g.getFightAs())), 30, 70, 40);
        Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.savePartyGlobalMessages).replace("{PARTY}", g.getTag()));
        return false;
    }
}
