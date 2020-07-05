package ru.yakimovvn.soupHdfs.endpoint;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.yakimovvn.soap.hdfs.GetHDFSRequest;
import ru.yakimovvn.soap.hdfs.GetHDFSResponse;
import ru.yakimovvn.soupHdfs.service.HDFSResponseService;

import java.io.IOException;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Endpoint
public class HDFSServiceEndpoint {

    private final String NAMESPACE = "http://www.yakimovvn.ru/HDFSService";

    @Value("${hdfs.token}")
    private String token;

    private final HDFSResponseService  hdfsResponseService;

    public HDFSServiceEndpoint(HDFSResponseService hdfsResponseService) {
        this.hdfsResponseService = hdfsResponseService;
    }


    @PayloadRoot(namespace = NAMESPACE, localPart = "getHDFSRequest")
    @ResponsePayload
    public GetHDFSResponse getHDFSResponse(@RequestPayload final GetHDFSRequest request) throws IOException {

        GetHDFSResponse response;

        String token = request.getToken();
        String action = request.getAction();
        String path = request.getPath();

        if(token == null || !token.equals(this.token)){
            response =  hdfsResponseService.gerErrorResponse("Permission denied");
        }else if (action == null || action.isEmpty()){
            response =  hdfsResponseService.gerErrorResponse("Action is not recognized");
        }else if (path == null || path.isEmpty() || !hdfsResponseService.isPathExists(path)){
            response =  hdfsResponseService.gerErrorResponse("Path is not recognized");
        }else{
            response = hdfsResponseService.getHDFSResponse(action, path);
        }
        return response;

    }
}
