package com.capstone.moa.utils;

import com.capstone.moa.entity.GroupProfile;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ImageUtils {
    public static GroupProfile parseImageInfo(MultipartFile multipartFile) throws Exception {
        if (multipartFile.isEmpty())
            return null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());
        String absolutePath = new File("").getAbsolutePath() + "\\";
        String path = absolutePath + "photo" + File.separator + current_date;
        File file = new File(path);

        //해당 경로가 존재하지 않으면 디렉토리 생성
        if (!file.exists()) {
            file.mkdirs();
        }

        String originalFileExtension = "";
        String contentType = multipartFile.getContentType();

        if (ObjectUtils.isEmpty(contentType))
            return null;
        else  {
            if (contentType.contains("image/jpeg")) {
                originalFileExtension = ".jpg";
            } else if (contentType.contains("image/png")) {
                originalFileExtension = ".png";
            } else if (contentType.contains("image/gif")) {
                originalFileExtension = ".gif";
            } else {
                throw new IllegalStateException("Wrong type");
            }
        }

        //파일 이름이 겹치지 않도록 나노 초까지 동원하여 이름으로 사용
        String newFileName = System.nanoTime() + originalFileExtension;
        GroupProfile imageData = GroupProfile.builder()
                .origImgName(multipartFile.getOriginalFilename())
                .type(originalFileExtension)
                .imgPath(path + File.separator + newFileName)
                .imgSize(multipartFile.getSize())
                .build();

        //filePath에 파일 저장
        file = new File(path + File.separator + newFileName);
        multipartFile.transferTo(file);

        return imageData;
    }
}
