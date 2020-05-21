package ru.yakimovvn.soupHdfs.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import ru.yakimovvn.soap.hdfs.GetHDFSRequest;
import ru.yakimovvn.soap.hdfs.GetHDFSResponse;
import ru.yakimovvn.soupHdfs.service.HDFSService;

import java.io.IOException;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Endpoint
public class HDFSServiceEndpoint {

    private final String NAMESPACE = "http://www.yakimovvn.ru/HDFSService";

    private final HDFSService hdfsService;

    public HDFSServiceEndpoint(HDFSService hdfsService) {
        this.hdfsService = hdfsService;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getHDFSRequest")
    public GetHDFSResponse getHDFSResponse(@RequestPayload final GetHDFSRequest request) throws IOException {
        GetHDFSResponse response = new GetHDFSResponse();
        response.getHdfsItems().addAll(hdfsService.getListFolder(request.getPath()));
        return response;
    }
}
