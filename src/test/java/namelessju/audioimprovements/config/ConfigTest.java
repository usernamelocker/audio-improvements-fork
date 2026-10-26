package namelessju.audioimprovements.config;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ConfigTest
{
    @TempDir Path directory;

    private static class TestConfig extends Config
    {
        final BooleanEntry enabled = new BooleanEntry(this, "enabled", true);

        TestConfig(Path directory)
        {
            super(directory, "test.json");
        }
    }

    @Test
    void createsMissingDirectoryAndRoundTripsValues()
    {
        TestConfig config = new TestConfig(directory.resolve("nested"));
        config.load();
        assertTrue(Files.exists(config.file.toPath()));
        config.enabled.value = false;
        config.save();

        TestConfig reloaded = new TestConfig(directory.resolve("nested"));
        reloaded.load();
        assertFalse(reloaded.enabled.value);
    }

    @Test
    void repairsInvalidEntryInMemoryAndOnDisk() throws Exception
    {
        TestConfig config = new TestConfig(directory);
        Files.writeString(config.file.toPath(), "{\"enabled\":\"invalid\"}");
        config.enabled.value = false;
        config.load();
        assertTrue(config.enabled.value);
        assertTrue(JsonParser.parseString(Files.readString(config.file.toPath()))
            .getAsJsonObject().get("enabled").getAsBoolean());
        // Windows rejects this operation if the reader was left open.
        Files.delete(config.file.toPath());
    }

    @Test
    void malformedJsonIsPreservedAndReaderIsClosed() throws Exception
    {
        TestConfig config = new TestConfig(directory);
        String malformed = "{\"enabled\":";
        Files.writeString(config.file.toPath(), malformed);
        config.enabled.value = false;
        config.load();
        assertFalse(config.enabled.value);
        assertEquals(malformed, Files.readString(config.file.toPath()));
        Files.delete(config.file.toPath());
    }
}
