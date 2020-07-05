package ru.yakimovvn.web.services.soap;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.yakimovvn.hdfsservice.*;

import java.util.List;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */


@Service
@RequiredArgsConstructor
public class HDFSService {

    @Value("${hadoopprj.hdfs.token}")
    private String token;

    public List<HDFSItem> listFolder(String path) throws Exception {
        HDFSPort hdfsPort = new HDFSPortService().getHDFSPortSoap11();

        GetHDFSRequest request = new GetHDFSRequest();
        request.setPath(path);
        request.setToken(token);
        request.setAction("list");
        GetHDFSResponse response;

        try {
            response = hdfsPort.listFolder(request);
        } catch (Exception ex) {
            response = null;
        }



        if(response!= null && response.getResult().equals("successfully")){
            response.getHdfsItems().forEach(v -> v.setPath(deleteHost(v.getPath())));
        }else {
            // отредактировать когда буду добавлять Exception
            throw new Exception("Hdfs exception:" + (response == null ? "response is null" : response.getResult()));
        }

        return response.getHdfsItems();

    }

    public String deleteHost(String str){
        return str.split("8020")[1];
    }
}
