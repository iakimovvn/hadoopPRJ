package ru.yakimovvn.soupHdfs.service;

import org.springframework.stereotype.Service;
import ru.yakimovvn.soap.hdfs.GetHDFSResponse;

import java.io.IOException;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Service
public class HDFSResponseService {

    private final HDFSService hdfsService;

    public HDFSResponseService(HDFSService hdfsService) {
        this.hdfsService = hdfsService;
    }

    public GetHDFSResponse gerErrorResponse(String msg){
        GetHDFSResponse response = new GetHDFSResponse();
        response.setResult(msg);
        return response;
    }

    public GetHDFSResponse getHDFSResponse(String action, String path) throws IOException {
        GetHDFSResponse response =  new GetHDFSResponse();

        switch (action){
            case "list":
                response.getHdfsItems().addAll(hdfsService.getListFolder(path));
                response.setResult("successfully");
                break;
        }

        return response;

    }

    public boolean isPathExists(String path) throws IOException {
        return hdfsService.isPathExists(path);
    }
}
