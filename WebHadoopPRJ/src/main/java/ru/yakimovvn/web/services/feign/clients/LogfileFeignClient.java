/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

package ru.yakimovvn.web.services.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "LogfileFeignClient", url = "${logfile.service.url}")
public interface LogfileFeignClient {

    @GetMapping(value = "/logfile")
    ResponseEntity<byte[]> getLogfile();
}
