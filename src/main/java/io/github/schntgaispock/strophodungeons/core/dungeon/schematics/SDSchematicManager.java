package io.github.schntgaispock.strophodungeons.core.dungeon.schematics;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Biome;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.github.schntgaispock.strophodungeons.core.dungeon.schematics.data.SDSFace;
import io.github.schntgaispock.strophodungeons.core.dungeon.schematics.data.SDSchematic;
import lombok.Getter;

public class SDSchematicManager {

    private static SDSchematicManager instance;
    
    private static final @Getter ObjectMapper JSONObjectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    private static final String SAVED_SCHEMATIC_PATH = "data-storage/StrophoDungeons/schematics/";
    private static final String DEFAULT_SCHEMATIC_PATH = "plugins/StrophoDungeons/schematics/";

    private final @Getter Map<String, SDSchematic> loadedSchematics = new HashMap<>();

    public static SDSchematicManager getInstance() {
        if (instance == null) {
            instance = new SDSchematicManager();
        }

        return instance;
    }

    public void generateSchematic(
            String name,
            Location corner, int xLen, int yLen, int zLen,
            SDSFace northFace, SDSFace eastFace, SDSFace southFace, SDSFace westFace
    ) {
        getLoadedSchematics().put(name, new SDSchematic(1, corner, xLen, yLen, zLen, Biome.LUSH_CAVES, northFace, eastFace, southFace, westFace));
    }

    public SDSchematic readFromFile(String fullPath) {
        try {
            SDSchematic test = getJSONObjectMapper().readValue(new File(fullPath), SDSchematic.class);
            System.out.println(test);
            return test;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SDSchematic readFromDefault(String filename) {
        return readFromFile(DEFAULT_SCHEMATIC_PATH, filename);
    }

    public SDSchematic readFromSaved(String filename) {
        return readFromFile(SAVED_SCHEMATIC_PATH, filename);
    }

    public SDSchematic readFromFile(String path, String filename) {
        return readFromFile(path + filename);
    }
    

    private void saveToFile(String fullPath, String schematicKey) {
        try {
            File file = new File(fullPath);
            file.getParentFile().mkdirs();
            file.createNewFile();
            getJSONObjectMapper().writeValue(file, loadedSchematics.get(schematicKey));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveToDefault(String filename, String schematicKey) {
        saveToFile(DEFAULT_SCHEMATIC_PATH, filename, schematicKey);
    }

    public void saveToSaved(String filename, String schematicKey) {
        saveToFile(SAVED_SCHEMATIC_PATH, filename, schematicKey);
    }

    private void saveToFile(String path, String filename, String schematicKey) {
        saveToFile(path + filename, schematicKey);
    }

}
