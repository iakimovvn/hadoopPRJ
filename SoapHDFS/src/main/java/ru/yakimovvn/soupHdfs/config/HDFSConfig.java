package ru.yakimovvn.soupHdfs.config;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Configuration
public class HDFSConfig {

    private static final String NAME_NODE = "hdfs://localhost:8020";

    @Bean
    public org.apache.hadoop.conf.Configuration hadoopConfiguration(){
        org.apache.hadoop.conf.Configuration hadoopConfig = new org.apache.hadoop.conf.Configuration();
        hadoopConfig.set("fs.hdfs.impl",
                org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()
        );
        hadoopConfig.set("fs.file.impl",
                org.apache.hadoop.fs.LocalFileSystem.class.getName()
        );
        return hadoopConfig;
    }


    @Bean
    public FileSystem fileSystem() throws URISyntaxException, IOException {
        return FileSystem.get(new URI(NAME_NODE),hadoopConfiguration());
    }
}
