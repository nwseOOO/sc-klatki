package pl.yspar.cage.manager;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.yspar.cage.basic.Cage;
import pl.yspar.cage.basic.Party;
import pl.yspar.cage.basic.User;
import pl.yspar.cage.config.Config;

public class WalkoverManager {
    public static void quitWalkover(final Player p) {
        final Party party = PartyManager.getParty(p);
        final Party partyOne = PartyManager.getParty(Cage.partySave.get(0));
        final Party partyTwo = PartyManager.getParty(Cage.partySave.get(1));
        party.removePoints(1);
        ++Cage.walkovers;
        CageManager.HideAllPlayer();
        for (final Player playerOnline : Bukkit.getOnlinePlayers()) {
            if (partyOne.getPoints() == 0) {
                if (Cage.partySave.size() == 2 && Cage.partyPass.size() == 0) {
                    for (final Player px : party.getCageMembers()) {
                        final User userWin = UserManager.getUser(px);
                        CageManager.checkWin(userWin, playerOnline, Config.getInstance().titlemessages.winWalkoverTitle, Config.getInstance().titlemessages.winWalkoverSubTitle.replace("{PARTY}", partyTwo.getTag()).replace("{MEMBERS}", StringUtils.join((Object[]) CageManager.getMemberList(partyTwo.getMembers()), "&8, ")));
                    }
                    CageManager.stop();
                    return;
                }
                if (Cage.partySave.size() >= 1 && Cage.partyPass.size() >= 1) {
                    Cage.partySave.remove(partyOne.getTag());
                    Cage.partySave.remove(partyTwo.getTag());
                    Cage.partyPass.add(partyTwo.getTag());
                    if (partyTwo.getFightAs() < 1) {
                        partyTwo.setFightAs(0);
                    }
                    partyTwo.setFightAs(Cage.partyPass.size());
                    CageManager.start();
                    return;
                }
            }
            if (partyTwo.getPoints() == 0) {
                if (Cage.partySave.size() == 2 && Cage.partyPass.size() == 0) {
                    for (final Player px : party.getCageMembers()) {
                        final User userWin = UserManager.getUser(px);
                        CageManager.checkWin(userWin, playerOnline, Config.getInstance().titlemessages.winWalkoverTitle, Config.getInstance().titlemessages.winWalkoverSubTitle.replace("{PARTY}", partyOne.getTag()).replace("{MEMBERS}", StringUtils.join((Object[]) CageManager.getMemberList(partyOne.getMembers()), "&8, ")));
                    }
                    CageManager.stop();
                    return;
                }
                if (Cage.partySave.size() >= 1 && Cage.partyPass.size() >= 1) {
                    Cage.partySave.remove(partyTwo.getTag());
                    Cage.partySave.remove(partyOne.getTag());
                    Cage.partyPass.add(partyOne.getTag());
                    if (partyOne.getFightAs() < 1) {
                        partyOne.setFightAs(0);
                    }
                    partyOne.setFightAs(Cage.partyPass.size());
                    CageManager.start();
                    return;
                }
            }
        }
    }
}
