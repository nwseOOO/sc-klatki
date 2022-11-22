package pl.yspar.cage.tab;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import pl.yspar.cage.config.Config;

import java.util.UUID;

public class TabProfile extends GameProfile {
    public TabProfile(final UUID uuid, final String s1) {
        super(uuid, s1);
        this.setSkin();
    }

    private void setSkin() {
        this.getProperties().removeAll("textures");
        this.getProperties().put("textures", new Property("textures", Config.getInstance().tablist.tablistTextureValue, Config.getInstance().tablist.tablistTextureSignature));
    }
}
