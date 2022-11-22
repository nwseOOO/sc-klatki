package pl.yspar.cage.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import pl.yspar.cage.basic.Cage;
import pl.yspar.cage.basic.Party;
import pl.yspar.cage.basic.User;
import pl.yspar.cage.commands.manager.PlayerCommand;
import pl.yspar.cage.manager.CageManager;
import pl.yspar.cage.manager.PartyManager;
import pl.yspar.cage.manager.UserManager;
import pl.yspar.cage.types.CageType;
import pl.yspar.cage.utils.Util;

public class PartyCommand extends PlayerCommand {
    public PartyCommand() {
        super("party", "informacje o evencie", "/event", "cage.cmd.user", "gildie");
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        final Party party = PartyManager.getParty(p);
        final User user = UserManager.getUser(p);
        if (args.length < 1) {
            Util.sendMessage(p, "&8>> &7/party zaloz <tag> &8- &7Tworzy party");
            Util.sendMessage(p, "&8>> &7/party usun &8- &7Usun party");
            Util.sendMessage(p, "&8>> &7/party info &8- &7Informacje o party");
            Util.sendMessage(p, "&8>> &7/party zapros <gracz> &8- &7Zaprasza gracza do party");
            Util.sendMessage(p, "&8>> &7/party dolacz <tag> &8- &7Dolacza do party");
            Util.sendMessage(p, "&8>> &7/party opusc &8- &7Opuszcza party");
            Util.sendMessage(p, "&8>> &7/party wyrzuc <gracz> &8- &7Wyrzuca gracza z party");
            return false;
        }
        if (args[0].equals("info")) {
            if (party == null) {
                Util.sendMessage(p, "&8>> &cNie posiadasz party.");
                return false;
            }
            Util.sendMessage(p, "&8>> &7Nazwa party&8: &6" + party.getTag());
            Util.sendMessage(p, "&8>> &7Zalozyciel party&8: &6" + party.getOwner());
            Util.sendMessage(p, "&8>> &7Lista osob w party&8: &6" + StringUtils.join(CageManager.getMemberList(party.getMembers()), "&8, "));
            return false;
        } else if (args[0].equals("usun")) {
            if (party == null) {
                Util.sendMessage(p, "&8>> &cNie posiadasz party.");
                return false;
            }
            if (!party.isOwner(p)) {
                Util.sendTitleMessage(p, "", "&ctylko lider to moze...", 30, 70, 40);
                return false;
            }
            if (party.isFightingNow()) {
                Util.sendTitleMessage(p, "", "&cAktualnie walczysz!", 30, 70, 40);
                return false;
            }
            PartyManager.deleteParty(party);
            Util.sendMessage(p, "&8� &aUsunales party.");
            return false;
        } else if (args[0].equals("zaloz")) {
            if (args.length < 2) {
                Util.sendMessage(p, "&8>> &7/party zaloz <tag> &8- &7Tworzy party");
                Util.sendMessage(p, "&8>> &7/party usun &8- &7Usun party");
                Util.sendMessage(p, "&8>> &7/party info &8- &7Informacje o party");
                Util.sendMessage(p, "&8>> &7/party zapros <gracz> &8- &7Zaprasza gracza do party");
                Util.sendMessage(p, "&8>> &7/party dolacz <tag> &8- &7Dolacza do party");
                Util.sendMessage(p, "&8>> &7/party opusc &8- &7Opuszcza party");
                Util.sendMessage(p, "&8>> &7/party wyrzuc <gracz> &8- &7Wyrzuca gracza z party");
                return false;
            }
            final String tag = args[1];
            if (user.getParty() != null) {
                Util.sendMessage(p, "&8>> &cPosiadasz juz party.");
                return false;
            }
            if (Cage.statusCage == CageType.INGAME) {
                Util.sendMessage(p, "&8>> &cNie mozesz stworzyc party jezeli juz trwa event.");
                return false;
            }
            if (tag.length() > 7 || tag.length() < 2) {
                Util.sendMessage(p, "&8>> &cTag jest za dlugi lub za krotki.");
                return false;
            }
            if (PartyManager.getParty(tag) != null) {
                Util.sendMessage(p, "&8>> &cIstenieje juz takie party");
                return false;
            }
            final Party partyCreated = PartyManager.createParty(tag.toUpperCase(), p.getName(), p.getPlayer());
            partyCreated.addMember(p.getPlayer());
            Util.sendMessage(p, "&8>> &aPomyslnie zalozono party");
            return false;
        } else if (args[0].equals("zapros")) {
            if (args.length < 2) {
                Util.sendMessage(p, "&8>> &7/party zaloz <tag> &8- &7Tworzy party");
                Util.sendMessage(p, "&8>> &7/party usun &8- &7Usun party");
                Util.sendMessage(p, "&8>> &7/party info &8- &7Informacje o party");
                Util.sendMessage(p, "&8>> &7/party zapros <gracz> &8- &7Zaprasza gracza do party");
                Util.sendMessage(p, "&8>> &7/party dolacz <tag> &8- &7Dolacza do party");
                Util.sendMessage(p, "&8>> &7/party opusc &8- &7Opuszcza party");
                Util.sendMessage(p, "&8>> &7/party wyrzuc <gracz> &8- &7Wyrzuca gracza z party");
                return false;
            }
            if (party == null) {
                Util.sendTitleMessage(p, "", "&cNie posiadasz party", 30, 70, 40);
                return false;
            }
            if (!party.isLeader(p.getName())) {
                Util.sendTitleMessage(p, "", "&cNie jestes liderem party", 30, 70, 40);
                return false;
            }
            if (party.isFightingNow()) {
                Util.sendTitleMessage(p, "", "&cAktualnie walczysz!", 30, 70, 40);
                return false;
            }
            if (args.length != 2) {
                return Util.sendMessage(p.getPlayer(), "&cPoprawne uzycie: &c/party zapros <gracz>");
            }
            final User o = UserManager.getUser(args[1]);
            if (o == null) {
                Util.sendTitleMessage(p, "", "&4Blad! &cNie odnaleziono gracza", 30, 70, 40);
                return false;
            }
            if (o.getPlayer() == null) {
                Util.sendTitleMessage(p, "", "&4Blad! &cNie odnaleziono gracza", 30, 70, 40);
                return false;
            }
            final Party go = PartyManager.getParty(o.getPlayer());
            if (go != null) {
                Util.sendTitleMessage(p, "", "&4Blad! &cGracz posiada party", 30, 70, 40);
                return false;
            }
            party.getInvites().add(o.getPlayer());
            Util.sendMessage(o.getPlayer(), "&6" + p.getName() + " &7zaprosil Cie do party: &6" + party.getTag());
            Util.sendMessage(p, "&7>> &7Zaprosiles gracza do party!");
            return false;
        } else if (args[0].equals("dolacz")) {
            if (args.length < 2) {
                Util.sendMessage(p, "&8>> &7/party zaloz <tag> &8- &7Tworzy party");
                Util.sendMessage(p, "&8>> &7/party usun &8- &7Usun party");
                Util.sendMessage(p, "&8>> &7/party info &8- &7Informacje o party");
                Util.sendMessage(p, "&8>> &7/party zapros <gracz> &8- &7Zaprasza gracza do party");
                Util.sendMessage(p, "&8>> &7/party dolacz <tag> &8- &7Dolacza do party");
                Util.sendMessage(p, "&8>> &7/party opusc &8- &7Opuszcza party");
                Util.sendMessage(p, "&8>> &7/party wyrzuc <gracz> &8- &7Wyrzuca gracza z party");
                return false;
            }
            if (party != null) {
                Util.sendTitleMessage(p, " ", "&cPosiadasz juzparty!", 30, 70, 40);
                return false;
            }
            final Party g = PartyManager.getParty(args[1]);
            if (g == null) {
                Util.sendTitleMessage(p, " ", "&cParty o takim tagu nie istnieje", 30, 70, 40);
                return false;
            }
            if (!g.getInvites().contains(p)) {
                Util.sendTitleMessage(p, " ", "&cNie masz zaproszenia do tego party", 30, 70, 40);
                return false;
            }
            g.getInvites().remove(p);
            g.addMember(p);
            Util.sendTitleMessage(p, " ", "&7dolaczono do party: &6" + g.getTag(), 30, 70, 40);
            for (final Player x : g.getCageMembers()) {
                Util.sendMessage(x, "&7>> &7Gracz &6" + p.getName() + " &edolaczyl &7do &eparty&7.");
            }
            return false;
        } else if (args[0].equals("opusc")) {
            if (party == null) {
                return Util.sendMessage(p, "&eNie posiadasz party");
            }
            if (party.isFightingNow()) {
                Util.sendTitleMessage(p, "", "&cAktualnie walczysz!", 30, 70, 40);
                return false;
            }
            if (party.isOwner(p)) {
                return Util.sendMessage(p, "&cJestes liderem gildi...");
            }
            party.removeMember(p);
            Util.sendTitleMessage(p, " ", "&cOpuszczono party: &6" + party.getTag(), 30, 70, 40);
            for (final Player x2 : party.getCageMembers()) {
                Util.sendMessage(x2, "&7>> &7Gracz &c" + p.getName() + " &7odszedl z party.");
            }
            return false;
        } else {
            if (!args[0].equals("wyrzuc")) {
                return false;
            }
            if (party == null) {
                return Util.sendMessage(p, "&4BLAD: &eNie posiadasz gildii!");
            }
            if (!party.isLeader(p.getName())) {
                Util.sendTitleMessage(p, "&eGildie", "&eNie jestes liderem lub zastepca gildii!", 30, 70, 40);
                return false;
            }
            if (party.isFightingNow()) {
                Util.sendTitleMessage(p, "", "&cAktualnie walczysz!", 30, 70, 40);
                return false;
            }
            if (args.length != 2) {
                return Util.sendMessage(p, "&4BLAD: &ePoprawne uzycie: /t wyrzuc <gracz>");
            }
            final User o = UserManager.getUser(args[1]);
            if (o == null) {
                return Util.sendMessage(p, "&4BLAD: &eGracz nie istnieje!");
            }
            if (!party.isMember(o.getName())) {
                return Util.sendMessage(p, "&4BLAD: &eGracz nie jest w twojej gildii!");
            }
            if (party.isOwner(o.getName())) {
                return Util.sendMessage(p, "&4BLAD: &eNie mozesz wyrzuci zalozyciela");
            }
            if (p.getName().equals(o.getName())) {
                return Util.sendMessage(p, "&4BLAD: &eNie mozesz wyrzucic samego siebie!");
            }
            party.removeMember(o.getName());
            Util.sendTitleMessage(p, "", "&cZ party &6wyrzucono &cgracza &6" + o.getName(), 30, 70, 40);
            return false;
        }
    }
}
