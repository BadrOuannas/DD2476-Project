3
https://raw.githubusercontent.com/Silthus/sLimits/master/src/test/java/net/silthus/slimits/limits/PlayerBlockPlacementLimitTest.java
package net.silthus.slimits.limits;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import lombok.Getter;
import lombok.SneakyThrows;
import net.silthus.slimits.LimitsManager;
import net.silthus.slimits.LimitsPlugin;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.assertj.core.groups.Tuple;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Path;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.byLessThan;

@DisplayName("player block placement limit")
public class PlayerBlockPlacementLimitTest {

    @Getter
    private static ServerMock server;
    @Getter
    private PlayerMock player;
    @Getter
    private PlayerBlockPlacementLimit playerLimit;

    @BeforeAll
    public static void setUp() {
        server = MockBukkit.mock();
    }

    @AfterAll
    public static void tearDown() {
        MockBukkit.unmock();
    }

    @SneakyThrows
    @BeforeEach
    public void beforeEach() {
        File storage = new File(LimitsPlugin.PLUGIN_PATH, "storage");
        FileUtils.deleteDirectory(storage);

        player = server.addPlayer();

        playerLimit = new PlayerBlockPlacementLimit(player);
    }

    @Test
    @DisplayName("should set player uuid and name in config")
    public void shouldSetPlayerData() {

        assertThat(getPlayerLimit().getPlayerUUID())
                .isEqualTo(player.getUniqueId());
        assertThat(getPlayerLimit().getPlayerName())
                .isEqualTo(player.getName());
    }

    @Test
    @DisplayName("addBlock() should increase block type count")
    public void shouldIncreaseBlockTypeCount() {

        assertThat(getPlayerLimit().getCounts()).isEmpty();

        Block block = getBlock();
        getPlayerLimit().addBlock(block);

        assertThat(getPlayerLimit().getCounts())
                .hasSize(1)
                .containsEntry(block.getType(), 1);
    }

    @Test
    @DisplayName("addBlock() should add block to location list")
    public void shouldAddBlockLocation() {

        assertThat(getPlayerLimit().getLocations()).isEmpty();


        Block block = getBlock();
        getPlayerLimit().addBlock(block);

        assertThat(getPlayerLimit().getLocations())
                .hasSize(1)
                .contains(block.getLocation());
    }

    @Test
    @DisplayName("getCount() should get correct count")
    public void shouldGetCorrectCountForType() {

        Block block = getBlock();
        assertThat(getPlayerLimit().getCount(block.getType()))
                .isEqualTo(0);

        getPlayerLimit().addBlock(block);

        assertThat(getPlayerLimit().getCount(block.getType()))
                .isEqualTo(1);
    }

    @Test
    @DisplayName("addBlock() should not count already placed blocks")
    public void shouldNotAddDuplicateBlocks() {

        Block block = getBlock();
        assertThat(getPlayerLimit().getCount(block.getType()))
                .isEqualTo(0);

        getPlayerLimit().addBlock(block);
        assertThat(getPlayerLimit().getCount(block.getType()))
                .isEqualTo(1);

        getPlayerLimit().addBlock(block);
        assertThat(getPlayerLimit().getCount(block.getType()))
                .isEqualTo(1);
    }

    @Test
    @DisplayName("hasPlacedBlock() should check placed blocks")
    public void testHasPlacedBlock() {

        Block block = getBlock();

        assertThat(getPlayerLimit().hasPlacedBlock(block))
                .isFalse();

        getPlayerLimit().addBlock(block);
        assertThat(getPlayerLimit().hasPlacedBlock(block))
                .isTrue();

        getPlayerLimit().removeBlock(block);
        assertThat(getPlayerLimit().hasPlacedBlock(block))
                .isFalse();
    }

    @Test
    @DisplayName("removeBlock() should decrease counter")
    public void shouldRemoveBlockFromCount() {

        assertThat(getPlayerLimit().getCounts())
                .isEmpty();

        for (int i = 0; i < 10; i++) {
            getPlayerLimit().addBlock(getBlock());
        }

        Block block = getBlock();
        getPlayerLimit().addBlock(block);
        assertThat(getPlayerLimit().getCounts())
                .hasSizeBetween(1, 11)
                .containsKeys(block.getType());

        int count = getPlayerLimit().getCount(block.getType());

        getPlayerLimit().removeBlock(block);
        assertThat(getPlayerLimit().getCount(block.getType()))
                .isEqualTo(count - 1);
    }

    @Test
    @DisplayName("removeBlock() should remove block from locations")
    public void shouldRemoveBlockFromLocations() {

        assertThat(getPlayerLimit().getLocations())
                .isEmpty();


        Block block = getBlock();
        getPlayerLimit().getLocations().add(block.getLocation());

        getPlayerLimit().removeBlock(block);
        assertThat(getPlayerLimit().getLocations())
                .isEmpty();
    }

    private Block getBlock() {
        World world = server.getWorld("world");
        assertThat(world).isNotNull();

        return world.getBlockAt(RandomUtils.nextInt(256), RandomUtils.nextInt(128), RandomUtils.nextInt(256));
    }
}