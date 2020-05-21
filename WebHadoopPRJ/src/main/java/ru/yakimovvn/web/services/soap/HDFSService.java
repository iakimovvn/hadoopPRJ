package ru.yakimovvn.web.services.soap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yakimovvn.hdfsservice.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */


@Service
@RequiredArgsConstructor
public class HDFSService {

    public List<HDFSItem> listFolder(String path){
        HDFSPort hdfsPort = new HDFSPortService().getHDFSPortSoap11();

        GetHDFSRequest request = new GetHDFSRequest();
        request.setPath(path);
        GetHDFSResponse response;

        try {
            response = hdfsPort.listFolder(request);
        } catch (Exception ex) {
            response = null;
        }

        return response == null ? new ArrayList<>() : response.getHdfsItems();

    }
}
