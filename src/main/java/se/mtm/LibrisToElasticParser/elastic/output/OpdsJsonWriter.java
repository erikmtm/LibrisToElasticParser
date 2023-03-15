/*
 * Copyright (c) 2023. Myndigheten för tillgängliga medier (MTM)
 */
package se.mtm.LibrisToElasticParser.elastic.output;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import se.mtm.LibrisToElasticParser.elastic.model.opds.OpdsItem;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Serializes an Opds dataset and write to files (.json)
 */
public class OpdsJsonWriter {

    public static void writeToJson(OpdsItem opdsItem) throws IOException {

        writeToFile(opdsItem, "./target/opds.json");
        writeToFile(opdsItem, "./target/opds-singles.json");
    }

    private static void writeToFile(OpdsItem data, String filePath) throws IOException {

        Gson gson = new GsonBuilder().create();
        Writer writer = new FileWriter(filePath);

        gson.toJson(data, writer);

        writer.flush();
        writer.close();
    }

    public enum Type {
        opds, opds_singles
    }

}
