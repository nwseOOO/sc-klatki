package pl.yspar.cage.tab;

import com.mojang.authlib.GameProfile;
import org.bukkit.entity.Player;
import pl.yspar.cage.utils.Reflection;
import pl.yspar.cage.utils.Util;

import java.util.Collections;

public class TablistPacket {
    private static final Reflection.MethodInvoker HANDLE_METHOD_INVOKER;
    private static final Reflection.MethodInvoker SEND_PACKET_METHOD_INVOKER;
    private static final Reflection.MethodInvoker BASE_COMPONENT_METHOD_INVOKER;
    private static final Reflection.FieldAccessor<Object> PLAYER_CONNECTION_FIELD_ACCESSOR;
    private static final Reflection.FieldAccessor<Object> HEADER_FIELD_ACCESSOR;
    private static final Reflection.FieldAccessor<Object> FOOTER_FIELD_ACCESSOR;
    private static final Reflection.FieldAccessor<Object> ACTION_FIELD_ACCESSOR;
    private static final Reflection.FieldAccessor<Object> DATA_LIST_FIELD_ACCESSOR;
    private static final Reflection.ConstructorInvoker PLAYER_HEADER_FOOTER_PACKET_CONSTRUCTOR_INVOKER;
    private static final Reflection.ConstructorInvoker PLAYER_INFO_PACKET_CONSTRUCTOR_INVOKER;
    private static final Reflection.ConstructorInvoker PLAYER_INFO_DATA_CONSTRUCTOR_INVOKER;
    private static final Object[] PLAYER_INFO_ACTION_ENUM_CONSTANTS;
    private static final Object WORLD_SETTINGS_GAMEMODE_ENUM_CONSTANT;

    static {
        HANDLE_METHOD_INVOKER = Reflection.getMethod(Reflection.getCraftBukkitClass("entity.CraftEntity"), "getHandle", (Class<?>[]) new Class[0]);
        SEND_PACKET_METHOD_INVOKER = Reflection.getMethod(Reflection.getMinecraftClass("PlayerConnection"), "sendPacket", Reflection.getMinecraftClass("Packet"));
        BASE_COMPONENT_METHOD_INVOKER = Reflection.getTypedMethod(Reflection.getMinecraftClass("IChatBaseComponent$ChatSerializer"), "a", Reflection.getMinecraftClass("IChatBaseComponent"), String.class);
        PLAYER_CONNECTION_FIELD_ACCESSOR = Reflection.getSimpleField(Reflection.getMinecraftClass("EntityPlayer"), "playerConnection");
        HEADER_FIELD_ACCESSOR = Reflection.getSimpleField(Reflection.getMinecraftClass("PacketPlayOutPlayerListHeaderFooter"), "a");
        FOOTER_FIELD_ACCESSOR = Reflection.getSimpleField(Reflection.getMinecraftClass("PacketPlayOutPlayerListHeaderFooter"), "b");
        ACTION_FIELD_ACCESSOR = Reflection.getSimpleField(Reflection.getMinecraftClass("PacketPlayOutPlayerInfo"), "a");
        DATA_LIST_FIELD_ACCESSOR = Reflection.getSimpleField(Reflection.getMinecraftClass("PacketPlayOutPlayerInfo"), "b");
        PLAYER_HEADER_FOOTER_PACKET_CONSTRUCTOR_INVOKER = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutPlayerListHeaderFooter"), (Class<?>[]) new Class[0]);
        PLAYER_INFO_PACKET_CONSTRUCTOR_INVOKER = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutPlayerInfo"), (Class<?>[]) new Class[0]);
        PLAYER_INFO_DATA_CONSTRUCTOR_INVOKER = Reflection.getConstructor(Reflection.getMinecraftClass("PacketPlayOutPlayerInfo$PlayerInfoData"), Reflection.getMinecraftClass("PacketPlayOutPlayerInfo"), GameProfile.class, Integer.TYPE, Reflection.getMinecraftClass("WorldSettings$EnumGamemode"), Reflection.getMinecraftClass("IChatBaseComponent"));
        PLAYER_INFO_ACTION_ENUM_CONSTANTS = (Object[]) Reflection.getMinecraftClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction").getEnumConstants();
        WORLD_SETTINGS_GAMEMODE_ENUM_CONSTANT = Reflection.getMinecraftClass("WorldSettings$EnumGamemode").getEnumConstants()[1];
    }

    public static void send(final Player player, final Object... packets) {
        if (TablistPacket.HANDLE_METHOD_INVOKER == null || TablistPacket.SEND_PACKET_METHOD_INVOKER == null || TablistPacket.PLAYER_CONNECTION_FIELD_ACCESSOR == null) {
            return;
        }
        final Object playerConnection = TablistPacket.PLAYER_CONNECTION_FIELD_ACCESSOR.get(TablistPacket.HANDLE_METHOD_INVOKER.invoke(player, new Object[0]));
        for (final Object packet : packets) {
            TablistPacket.SEND_PACKET_METHOD_INVOKER.invoke(playerConnection, packet);
        }
    }

    public static void send(final Player player, final String header, final String footer) {
        final Object packet = TablistPacket.PLAYER_HEADER_FOOTER_PACKET_CONSTRUCTOR_INVOKER.invoke();
        TablistPacket.HEADER_FIELD_ACCESSOR.set(packet, toJson(header));
        TablistPacket.FOOTER_FIELD_ACCESSOR.set(packet, toJson(footer));
        send(player, packet);
    }

    public static void send(final Player player, final TabProfile profile, final String displayName, final PlayerInfoAction action) {
        final Object packet = TablistPacket.PLAYER_INFO_PACKET_CONSTRUCTOR_INVOKER.invoke();
        TablistPacket.ACTION_FIELD_ACCESSOR.set(packet, TablistPacket.PLAYER_INFO_ACTION_ENUM_CONSTANTS[action.getActionID()]);
        TablistPacket.DATA_LIST_FIELD_ACCESSOR.set(packet, Collections.singletonList(getInfo(packet, profile, displayName)));
        send(player, packet);
    }

    public static Object getInfo(final Object packet, final TabProfile profile, final String displayName) {
        return TablistPacket.PLAYER_INFO_DATA_CONSTRUCTOR_INVOKER.invoke(packet, profile, 9999, TablistPacket.WORLD_SETTINGS_GAMEMODE_ENUM_CONSTANT, toJson(displayName));
    }

    public static Object toJson(final String st) {
        return TablistPacket.BASE_COMPONENT_METHOD_INVOKER.invoke(null, "{\"text\": \"" + Util.fixColor(st) + "\"}");
    }
}
