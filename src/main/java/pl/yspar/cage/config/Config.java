package pl.yspar.cage.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

public class Config {
    private static Config instance;
    public Licence licence;
    public Database database;
    public Messages messages;
    public TabList tablist;
    public ActionMessages actionmessages;
    public TitleMessages titlemessages;
    public Sidebar sidebar;
    public Inventory inventory;
    public NameTag nametag;

    public Config() {
        this.licence = new Licence();
        this.database = new Database();
        this.messages = new Messages();
        this.actionmessages = new ActionMessages();
        this.titlemessages = new TitleMessages();
        this.sidebar = new Sidebar();
        this.inventory = new Inventory();
        this.nametag = new NameTag();
        this.tablist = new TabList();
    }

    public static Config getInstance() {
        if (Config.instance == null) {
            Config.instance = fromDefaults();
        }
        return Config.instance;
    }

    public static void init(final File filePath) {
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
    }

    public static void load(final File file) {
        Config.instance = fromFile(file);
        if (Config.instance == null) {
            Config.instance = fromDefaults();
        }
    }

    public static void load(final String file) {
        load(new File(file));
    }

    private static Config fromDefaults() {
        final Config config = new Config();
        return config;
    }

    private static Config fromFile(final File configFile) {
        try {
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8));
            return gson.fromJson(reader, Config.class);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public void toFile(final String file) {
        this.toFile(new File(file));
    }

    public void toFile(final File file) {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final String jsonconfig = gson.toJson(this);
        final CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
        encoder.onMalformedInput(CodingErrorAction.REPORT);
        encoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        try {
            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoder));
            writer.write(jsonconfig);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public static class Licence {
        public String key;
        public String numericAddressIP;

        public Licence() {
            this.key = "SC-Cage-1239137";
            this.numericAddressIP = "127.0.0.1";
        }
    }

    public static class Database {
        public String hostname;
        public int port;
        public String name;
        public String user;
        public String password;
        public String prefix;

        public Database() {
            this.hostname = "localhost";
            this.port = 3306;
            this.name = "laveriatest";
            this.user = "nactive";
            this.password = "laveriapl";
            this.prefix = "scTeam_";
        }
    }

    public static class Sidebar {
        public String header;
        public String line1;
        public String line2;
        public String line3;
        public String line4;
        public String line5;
        public String line6;

        public Sidebar() {
            this.header = "&eHacked by dawidkamil81";
            this.line1 = "&fZapisany: &7{SAVE}";
            this.line2 = "&fTwoj ping: &7{PING}";
            this.line3 = "&fWalczysz za: &7{FIGHTAS}";
            this.line4 = "&fParty: &7{PARTY}";
            this.line5 = "&fZapisani: &7{SIZE}";
            this.line6 = "&fPrzechodza dalej: &7{SIZE}";
        }
    }

    public static class Messages {
        public String dontHavePermision;
        public String killMessages;
        public String savePartyMessages;
        public String savePartyGlobalMessages;
        public String changeTypeMessages;
        public String changeEquipMessages;
        public String changeTimeMessages;
        public String winRound;
        public String pluginRestart;

        public Messages() {
            this.dontHavePermision = "&8>> &7Nie mozesz odpalic klatek poniewaz nie masz takiej rangi!";
            this.killMessages = "&8>> &7Gracz &c{KILLER} &7zabil gracza &6{PLAYER} &7serca: &c{HP}";
            this.savePartyMessages = "&8>> &fZapisano na klatki, twoje party walczy za &e{ROUND} &frund.";
            this.savePartyGlobalMessages = "&8>> &7Party &f(&e{PARTY}&f &7dolaczylo do &6eventu&7.";
            this.changeTypeMessages = "&8>> &7Typ klatek zostal zmieniony na &7{TYPE}vs{TYPE}";
            this.changeEquipMessages = "&8>> &7Sety zostaly zmienione na &7{TYPE}";
            this.changeTimeMessages = "&8>> &7Czas oczekiwania na klatki zostal ustawiony na &7{TIME}";
            this.winRound = "&7Party &6{PARTY} &7przechodzi dalej.";
            this.pluginRestart = "&cPlugin jest restartowany, wejdz ponownie!";
        }
    }

    public static class ActionMessages {
        public String cageEnding;
        public String cageWaiting;
        public String cageInGame;
        public String newRound;

        public ActionMessages() {
            this.cageEnding = "&cAktualnie nie trwa zaden event.";
            this.cageWaiting = "&7Trwaja zapisy na klatki &7uzyj: &6/party zaloz <nazwa> &7oraz &6/event zapisz&7!";
            this.cageInGame = "&7Aktualnie trwa turniej &e{TYPE}&7vs&e{TYPE}";
            this.newRound = "&6Rozpoczynamy nowa ture eventu.";
        }
    }

    public static class TitleMessages {
        public String nowFighting;
        public String newRound;
        public String dontHaveOpponent;
        public String waiting;
        public String startRequirements;
        public String killTitle;
        public String winRound;
        public String winDontHaveOpponentTitle;
        public String winDontHaveOpponentSubTitle;
        public String winTitle;
        public String winSubTitle;
        public String winWalkoverTitle;
        public String winWalkoverSubTitle;

        public TitleMessages() {
            this.nowFighting = "&6{TEAM1} &7VS &6{TEAM2}";
            this.newRound = "&eRozpoczela sie nowa tura eventu, powodzenia.";
            this.dontHaveOpponent = "&fParty &e{PARTY} &fprzechodzi dalej bez przeciwnika.";
            this.waiting = "&fStart klatek za &e{TIME} &7{TYPE}vs{TYPE}";
            this.startRequirements = "&cZa malo osob zapisalo sie na event";
            this.killTitle = "&7Zabiles gracza &e{PLAYER}";
            this.winRound = "&7Party &6{PARTY} &7przechodzi dalej bez przeciwnika.";
            this.winDontHaveOpponentTitle = "&6Klatki wygrywa";
            this.winDontHaveOpponentSubTitle = "&7Party &e{PARTY} {MEMBERS} &7Poniewaz nie ma przeciwnika";
            this.winTitle = "&6Klatki wygrywa";
            this.winSubTitle = "&7Party &6{PARTY} &e{MEMBERS}";
            this.winWalkoverTitle = "&6Klatki wygrywa &6Walkowerem";
            this.winWalkoverSubTitle = "&7Party &6{PARTY} &e{MEMBERS}";
        }
    }

    public static class Inventory {
        public String hostTitle;
        public String xvsxTitle;
        public String equipTitle;
        public String timeTitle;

        public Inventory() {
            this.hostTitle = "&cklatki";
            this.xvsxTitle = "&cklatki ile vs ile?";
            this.equipTitle = "&cklatki sety?";
            this.timeTitle = "&cklatki czas?";
        }
    }

    public static class NameTag {
        public String friendlyColor;
        public String enemyColor;

        public NameTag() {
            this.friendlyColor = "&a";
            this.enemyColor = "&c";
        }
    }

    public static class TabList {
        public String mainColor;
        public String tablistHeader;
        public String tablistFooter;
        public String tablistTextureValue;
        public String tablistTextureSignature;

        public TabList() {
            this.mainColor = "&6";
            this.tablistHeader = "&eHacked by dawidkamil81";
            this.tablistFooter = "&8>> &7Graczy na klatkach: &e{ONLINE}";
            this.tablistTextureValue = "ewogICJ0aW1lc3RhbXAiIDogMTY1MjUxODc3NDA4NiwKICAicHJvZmlsZUlkIiA6ICI0NGNlMmMwZTFjNTM0ZDhmYmExNmNkNDhlYjkyYTUxZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJ4WGlyYW56dThYeCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9hMmQ4YTU1YmQ0Yzg2NGFjYjNjMTZiNGE2YzA3ZmUzMWQyMWQ5MDExNzZmNWI4NDM4MThhOWI2OWJhZjFjY2UyIgogICAgfQogIH0KfQ==";
            this.tablistTextureSignature = "jXO1Bi3DkHr19gQbY2JLxAwVRgPEXLQwghgSMu2/LjzC+lnkKIYhMAtn0qVXFQG5h0nQHBN5vjEDqFx0hZLFkNbzgB0Khm0gYU/PvnBYr5zAfh0R4HHKmn1z8LQPKRWfsCRQrDD8D91YrncCExxr9JjU514PLQ5kszv79SNpT9Fft/DGutVSmwvtmsxDmdXqeplBUDssgkyxkqin/FMw1m4QqWY/jE3GWEhgUsVBcTv7Ouo1IA4RsPzY3fZUjjlHMDltiHRfyRj+RC20otDqNcGK3gM+1ksq+t8fCNCg8fV8cTfh9/cgd5wCPzH6y+oU2O6HUCfpV2hpH1W8PIj8wEm5wATbInKS2JmbQI51ek73mA/LOURgyIEkuocpucv5MgvzhT24RLbd/a0cj1pPTV/ZycyQ0yBYiVDywToqppyzNot+FwIgXXy2mkl39Gy/taHTnFDd6eE+Pcq11GMnF1vlEAMckFzpiIlAtV/f1GpJoY2GrcKauUJx9liGtNtb3f7ZHR3Znyi6xd1VF3C83dR56NuSOvs+kzYvV+Ap+oH3PNrCLhfJq3ssaVwExCfMfMvOi3cIq0OirvOr4GFNV/0FEhn2h4DK2gGXssF8N+DcVBpuuiO4BCgF5vNxHxJi72/sbZkTC7HgqoPQBXxupavJC8ZH6zyAcs2NfJviwJM=";
        }
    }
}
