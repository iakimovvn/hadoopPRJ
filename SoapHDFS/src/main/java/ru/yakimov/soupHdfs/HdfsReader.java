package ru.yakimov.soupHdfs;

import com.google.inject.internal.cglib.core.$DuplicatesPredicate;
import org.apache.commons.io.IOUtils;
import org.apache.curator.shaded.com.google.common.io.Files;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public class HdfsReader {


    private FileSystem fs;

    public HdfsReader() throws URISyntaxException, IOException {
        Configuration hadoopConfig = new Configuration();
        hadoopConfig.set("fs.hdfs.impl",
                org.apache.hadoop.hdfs.DistributedFileSystem.class.getName()
        );
        hadoopConfig.set("fs.file.impl",
                org.apache.hadoop.fs.LocalFileSystem.class.getName()
        );
        fs = FileSystem.get(new URI(NAME_NODE), new Configuration());
    }

    private static final String NAME_NODE = "hdfs://localhost:8020";//nameNomeHost = localhost if you use hadoop in local mode

        public static void main(String[] args) throws URISyntaxException, IOException {

            HdfsReader hdfsReader = new HdfsReader();
            FileSystem fs = hdfsReader.fs;

            RemoteIterator<FileStatus> fileStatusRemoteIterator = fs.listStatusIterator(new Path("/"));
            while (fileStatusRemoteIterator.hasNext()){
                FileStatus fileStatus = fileStatusRemoteIterator.next();
                hdfsReader.print(fileStatus, 0);
            }


//            String fileContent = IOUtils.toString(fs.open(new Path(fileInHdfs)), "UTF-8");
//            System.out.println("File content - " + fileContent);


    }


    public  void print (FileStatus file, int num) throws IOException {
        for (int i = 0; i < num; i++) {
            System.out.print("----");
        }

        System.out.println(file.getPath().getName());

        if(file.isDirectory()){
            RemoteIterator<FileStatus> fileStatusRemoteIterator = fs.listStatusIterator(file.getPath());
            num++;
            while (fileStatusRemoteIterator.hasNext()){
                FileStatus fileStatus = fileStatusRemoteIterator.next();
                print(fileStatus, num);
            }
        }


    }

}
