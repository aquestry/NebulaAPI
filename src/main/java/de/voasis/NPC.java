package de.voasis;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.*;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
import net.minestom.server.network.packet.server.play.PlayerInfoUpdatePacket;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Map;

public class NPC extends Entity {

    private final String NPCname;

    public NPC(String name, Instance instance, Pos spawn) {
        super(EntityType.PLAYER);
        NPCname = name;
        setBoundingBox(0, 0, 0);
        setNoGravity(true);
        setInstance(instance);
        scheduleNextTick(entity -> teleport(spawn));
        NebulaAPI.createNametag(this, NPCname);
    }

    @Override
    public void updateNewViewer(@NotNull Player player) {
        var properties = new ArrayList<PlayerInfoUpdatePacket.Property>();
        var entry = new PlayerInfoUpdatePacket.Entry(getUuid(), NPCname, properties, false, 0, GameMode.CREATIVE, null, null, 1);
        player.sendPacket(new PlayerInfoUpdatePacket(PlayerInfoUpdatePacket.Action.ADD_PLAYER, entry));
        super.updateNewViewer(player);
        player.sendPackets(new EntityMetaDataPacket(getEntityId(), Map.of(17, Metadata.Byte((byte) 127))));
    }
}