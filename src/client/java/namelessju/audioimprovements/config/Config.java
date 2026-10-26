package namelessju.audioimprovements.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import namelessju.audioimprovements.AudioImprovements;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Config implements Iterable<ConfigEntry<?>>
{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public final File file;
    
    private final List<ConfigEntry<?>> entries = new ArrayList<>();
    
    public Config(Path configDir, String fileName)
    {
        this.file = configDir.resolve(fileName.replaceAll("[\\\\/]", "")).toFile();
    }
    
    void addEntry(ConfigEntry<?> entry)
    {
        entries.add(entry);
    }
    
    public void load()
    {
        if (!file.exists())
        {
            for (ConfigEntry<?> entry : this) entry.reset();
            save();
            return;
        }
        
        JsonElement json;
        try (Reader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8))
        {
            json = JsonParser.parseReader(reader);
        }
        catch (Exception e)
        {
            AudioImprovements.LOGGER.error("Error while reading or parsing config {}", file, e);
            return;
        }
        
        if (!json.isJsonObject())
        {
            AudioImprovements.LOGGER.error("Error while loading config: JSON root is not an object");
            return;
        }
        
        JsonObject jsonObject = json.getAsJsonObject();
        boolean saveRequired = false;
        
        for (ConfigEntry<?> entry : this)
        {
            JsonElement element = jsonObject.get(entry.key);
            if (element != null)
            {
                if (entry.loadFromJsonElement(element)) continue;
                logInvalidEntryValue(entry, element.toString());
                saveRequired = true;
            }
            else if (entry.updateFormat(jsonObject))
            {
                saveRequired = true;
                AudioImprovements.LOGGER.info("Updated config format for entry \"{}\"", entry.key);
                continue;
            }
            else
            {
                saveRequired = true;
                AudioImprovements.LOGGER.info("Added missing config key \"{}\" to file", entry.key);
            }
            entry.reset();
        }
        
        if (saveRequired)
        {
            save();
        }
    }
    
    protected void logInvalidEntryValue(ConfigEntry<?> entry, String details)
    {
        AudioImprovements.LOGGER.warn("Invalid value in config file for entry \"{}\": {}", entry.key, details);
    }
    
    public void save()
    {
        JsonObject json = new JsonObject();
        for (ConfigEntry<?> entry : this)
        {
            json.add(entry.key, entry.saveToJsonElement());
        }
        
        try
        {
            Files.createDirectories(file.toPath().toAbsolutePath().getParent());
            try (Writer writer = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8))
            {
                GSON.toJson(json, writer);
            }
        }
        catch (IOException e)
        {
            AudioImprovements.LOGGER.error("Error while saving config {}", file, e);
        }
    }
    
    @Override
    public @NotNull Iterator<ConfigEntry<?>> iterator()
    {
        return entries.iterator();
    }
}
