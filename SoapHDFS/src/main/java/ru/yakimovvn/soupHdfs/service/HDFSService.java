package ru.yakimovvn.soupHdfs.service;

import org.apache.hadoop.fs.*;
import org.springframework.stereotype.Service;
import ru.yakimovvn.soap.hdfs.HDFSItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Service
public class HDFSService {


    private final FileSystem fileSystem;

    public HDFSService(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public List<HDFSItem> getListFolder(String pathStr) throws IOException {

        Path path = new Path(pathStr);
        List <HDFSItem> hdfsItems = new ArrayList<>();

        if(!fileSystem.exists(path) && fileSystem.isFile(path))
            return hdfsItems;

        RemoteIterator<FileStatus> fileStatusRemoteIterator = fileSystem.listStatusIterator(path);

        while (fileStatusRemoteIterator.hasNext()){
            FileStatus fileStatus = fileStatusRemoteIterator.next();
            hdfsItems.add(
                    fileStatusToHDFSItem(fileStatus)
            );

        }
        return hdfsItems;
    }


    public HDFSItem fileStatusToHDFSItem(FileStatus fileStatus) {
        HDFSItem item = new HDFSItem();
        item.setName(fileStatus.getPath().getName());

        Path path = fileStatus.getPath();

        item.setPath(path.toString());
        item.setParent(path.getParent().toString());
        item.setIsDir(fileStatus.isDirectory());
        item.setLength(fileStatus.getLen());
        item.setBlocksize(fileStatus.getBlockSize());
        item.setModificationTime(fileStatus.getModificationTime());
        item.setAccessTime(fileStatus.getAccessTime());
        item.setPermission(fileStatus.getPermission().toString());
        item.setOwner(fileStatus.getOwner());
        item.setGroup(fileStatus.getGroup());
        return item;
    }
}
