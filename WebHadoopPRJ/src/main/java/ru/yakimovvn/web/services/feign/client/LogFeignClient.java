/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

package ru.yakimovvn.web.services.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "LogFeignClient", url = "${logfile.service.url}")
public interface LogFeignClient {


    @GetMapping(value = "/get?path={path}")
    ResponseEntity<byte[]> getLog(@PathVariable String path);

    @GetMapping(value = "/create?path={path}")
    ResponseEntity<String> createLog(@PathVariable String path);





}
