package com.example.indian_market_scraper.storage;


import com.example.indian_market_scraper.collector.NitterScraper;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.hadoop.ParquetWriter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.util.List;

public class ParquetWriterService {

    private static final String SCHEMA_JSON = "{\n" +
            "  \"type\": \"record\",\n" +
            "  \"name\": \"Tweet\",\n" +
            "  \"fields\": [\n" +
            "    {\"name\": \"username\", \"type\": \"string\"},\n" +
            "    {\"name\": \"timestamp\", \"type\": \"string\"},\n" +
            "    {\"name\": \"content\", \"type\": \"string\"},\n" +
            "    {\"name\": \"replies\", \"type\": \"int\"},\n" +
            "    {\"name\": \"retweets\", \"type\": \"int\"},\n" +
            "    {\"name\": \"likes\", \"type\": \"int\"}\n" +
            "  ]\n" +
            "}";

    private final Schema schema;

    public ParquetWriterService() {
        schema = new Schema.Parser().parse(SCHEMA_JSON);
    }

    public void writeTweets(List<NitterScraper.Tweet> tweets, String filePath) throws IOException {
        Path path = new Path(filePath);
        Configuration conf = new Configuration();

        try (ParquetWriter<GenericRecord> writer = AvroParquetWriter.<GenericRecord>builder(path)
                .withSchema(schema)
                .withConf(conf)
                .build()) {

            for (NitterScraper.Tweet t : tweets) {
                GenericRecord record = new GenericData.Record(schema);
                record.put("username", t.username);
                record.put("timestamp", t.timestamp);
                record.put("content", t.content);
                record.put("replies", t.replies);
                record.put("retweets", t.retweets);
                record.put("likes", t.likes);

                writer.write(record);
            }
        }
    }
}
