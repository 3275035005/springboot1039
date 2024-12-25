
package com.lyxy.studentdocument.controller;

import com.lyxy.studentdocument.advice.ExceptionEnums;
import com.lyxy.studentdocument.advice.MyException;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Slf4j
@Api(tags = "文件上传接口")
@RestController
@RequestMapping(value = "api/uploadFile")
public class UploadController {

    @Value("${file.servicePath}")
    private String baseUrl;

    @PostMapping("upload")
    public ResponseEntity<String> upload(MultipartFile file, HttpServletRequest request){
        String uuid= System.currentTimeMillis()+".";
        String originalFilename = file.getOriginalFilename();
        String fileName= uuid + originalFilename.substring(originalFilename.lastIndexOf(".") + 1);;
        try {
            File fileDir = new File(System.getProperty("user.dir")+"\\image");
            if (!fileDir.exists()) {
                if (!fileDir.mkdirs()) {
                    throw new MyException(ExceptionEnums.UPLOAD_FAIL);
                }
            }

            File file2 = new File(fileDir.getAbsolutePath() +"/"+fileName);
            if (file2.exists()) {
                if (!file2.delete()) {
                    throw new MyException(ExceptionEnums.UPLOAD_FAIL);
                }
            }
            if (file2.createNewFile()) {
                file.transferTo(file2);
                return ResponseEntity.ok(baseUrl+"/download?fileName="+fileName);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        throw new MyException(ExceptionEnums.UPLOAD_FAIL);
    }


    /**
     * 文件下载
     * @param imageName
     * @param response
     * @throws IOException
     */
    @GetMapping("/download")
    public void getImage(@RequestParam("fileName") String imageName,
                         HttpServletResponse response) throws IOException {
        String basePath = System.getProperty("user.dir");
        File fileDir = new File(basePath+"\\image");
        File image=new File(fileDir.getAbsolutePath() +"/"+imageName);
        if (image.exists()){
            FileInputStream fileInputStream=new FileInputStream(image);
            byte[] bytes=new byte[fileInputStream.available()];
            if (fileInputStream.read(bytes)>0){
                OutputStream outputStream=response.getOutputStream();
                outputStream.write(bytes);
                outputStream.close();
            }
            fileInputStream.close();
        }
    }
}
