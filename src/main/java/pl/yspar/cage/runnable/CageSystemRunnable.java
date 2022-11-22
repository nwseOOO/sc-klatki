package pl.yspar.cage.runnable;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.yspar.cage.CagePlugin;
import pl.yspar.cage.basic.Cage;
import pl.yspar.cage.basic.Party;
import pl.yspar.cage.basic.User;
import pl.yspar.cage.config.Config;
import pl.yspar.cage.manager.CageManager;
import pl.yspar.cage.manager.PartyManager;
import pl.yspar.cage.manager.UserManager;
import pl.yspar.cage.types.CageType;
import pl.yspar.cage.utils.Util;

import java.util.Iterator;

public class CageSystemRunnable extends BukkitRunnable {

    @Override
    public void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                new BukkitRunnable() {
                    public void run() {
                        if (Cage.statusCage == CageType.INGAME && Cage.partySave.size() == 2 && Cage.partyPass.size() == 0) {
                            final Party partyOne = PartyManager.getParty(Cage.partySave.get(0));
                            final Party partyTwo = PartyManager.getParty(Cage.partySave.get(1));
                            if (partyOne.getPoints() == 0) {
                                final Iterator<Player> iterator = partyTwo.getCageMembers().iterator();
                                if (iterator.hasNext()) {
                                    final Player p = iterator.next();
                                    final User userWin = UserManager.getUser(p);
                                    //final User u;
                                    final Party party = userWin.getParty();
                                    Bukkit.getOnlinePlayers().forEach(playerOnline -> CageManager.checkWin(userWin, playerOnline, Config.getInstance().titlemessages.winTitle, Config.getInstance().titlemessages.winSubTitle.replace("{PARTY}", party.getTag()).replace("{MEMBERS}", StringUtils.join(CageManager.getMemberList(party.getMembers()), "&8, "))));
                                    CageManager.stop();
                                    return;
                                }
                                CageManager.stop();
                                return;
                            } else if (partyTwo.getPoints() == 0) {
                                final Iterator<Player> iterator2 = partyOne.getCageMembers().iterator();
                                if (iterator2.hasNext()) {
                                    final Player p = iterator2.next();
                                    final User userWin = UserManager.getUser(p);
                                    //final User u2;
                                    final Party party2 = userWin.getParty();
                                    Bukkit.getOnlinePlayers().forEach(playerOnline -> CageManager.checkWin(userWin, playerOnline, Config.getInstance().titlemessages.winTitle, Config.getInstance().titlemessages.winSubTitle.replace("{PARTY}", party2.getTag()).replace("{MEMBERS}", StringUtils.join(CageManager.getMemberList(party2.getMembers()), "&8, "))));
                                    CageManager.stop();
                                    return;
                                }
                                CageManager.stop();
                                return;
                            }
                        }
                        if (Cage.statusCage == CageType.INGAME && Cage.partySave.size() >= 2 && Cage.partyPass.size() >= 0) {
                            final Party partyOne = PartyManager.getParty(Cage.partySave.get(0));
                            final Party partyTwo = PartyManager.getParty(Cage.partySave.get(1));
                            if (partyOne.getPoints() == 0) {
                                //final Party partyTwo2;
                                Bukkit.getOnlinePlayers().forEach(playerOnline -> Util.sendTitleMessage(playerOnline, "", Config.getInstance().titlemessages.winRound.replace("{PARTY}", partyTwo.getTag()), 0, 10, 0));
                                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.winRound.replace("{PARTY}", partyTwo.getTag())));
                                partyTwo.setPoints(partyTwo.getCageMembers().size());
                                Cage.partySave.remove(partyOne.getTag());
                                Cage.partySave.remove(partyTwo.getTag());
                                Cage.partyPass.add(partyTwo.getTag());
                                Bukkit.getOnlinePlayers().forEach(playerOnline -> {
                                    playerOnline.setGameMode(GameMode.SPECTATOR);
                                    playerOnline.getInventory().clear();
                                    playerOnline.getInventory().setHelmet(null);
                                    playerOnline.getInventory().setChestplate(null);
                                    playerOnline.getInventory().setLeggings(null);
                                    playerOnline.getInventory().setBoots(null);
                                });
                                CageManager.start();
                                return;
                            }
                            if (partyTwo.getPoints() == 0) {
                                //final Party partyOne2;
                                Bukkit.getOnlinePlayers().forEach(playerOnline -> Util.sendTitleMessage(playerOnline, "", Config.getInstance().titlemessages.winRound.replace("{PARTY}", partyOne.getTag()), 0, 10, 0));
                                Bukkit.broadcastMessage(Util.fixColor(Config.getInstance().messages.winRound.replace("{PARTY}", partyOne.getTag())));
                                partyOne.setPoints(partyOne.getCageMembers().size());
                                Cage.partySave.remove(partyTwo.getTag());
                                Cage.partySave.remove(partyOne.getTag());
                                Cage.partyPass.add(partyOne.getTag());
                                Bukkit.getOnlinePlayers().forEach(playerOnline -> {
                                    playerOnline.setGameMode(GameMode.SPECTATOR);
                                    playerOnline.getInventory().clear();
                                    playerOnline.getInventory().setHelmet(null);
                                    playerOnline.getInventory().setChestplate(null);
                                    playerOnline.getInventory().setLeggings(null);
                                    playerOnline.getInventory().setBoots(null);
                                });
                                CageManager.start();
                                return;
                            }
                        }
                        if (Cage.partySave.size() == 0 && Cage.partyPass.size() > 1) {
                            for (final Party g : PartyManager.getparty().values()) {
                                if (Cage.partyPass.contains(g.getTag())) {
                                    ++Cage.round;
                                    Bukkit.getOnlinePlayers().forEach(playerOnline -> Util.sendMessage(playerOnline, Util.fixColor(Config.getInstance().actionmessages.newRound)));
                                    Bukkit.getOnlinePlayers().forEach(playerOnline -> Util.sendTitleMessage(playerOnline, "", Util.fixColor(Config.getInstance().titlemessages.newRound), 0, 50, 0));
                                    Cage.partySave.addAll(Cage.partyPass);
                                    Cage.partyPass.clear();
                                    CageManager.start();
                                }
                            }
                        }
                        if (Cage.partyPass.size() >= 1 && Cage.partySave.size() == 1) {
                            Bukkit.getOnlinePlayers().forEach(playerOnline -> Util.sendActionBar(playerOnline, Util.fixColor(Config.getInstance().titlemessages.dontHaveOpponent).replace("{PARTY}", Cage.partySave.get(0))));
                            Cage.partyPass.add(0, Cage.partySave.get(0));
                            Cage.partySave.remove(0);
                            CageManager.start();
                        }
                    }
                }.runTask(CagePlugin.getPlugin());
            }
        }.runTaskAsynchronously(CagePlugin.getPlugin());
    }

}