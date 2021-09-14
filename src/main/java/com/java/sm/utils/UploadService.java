package com.java.sm.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.java.sm.common.http.AxiosStatus;
import com.java.sm.common.properties.UploadProperties;
import com.java.sm.exception.AdminExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import javax.validation.Constraint;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 开发者：辉哥
 * 特点： 辉哥很帅
 * 开发时间：2021/6/15 11:55
 * 文件说明：
 */
@Component
public class UploadService {

    @Autowired
    private UploadProperties uploadProperties;

    public String upload(Part part) {
        try {
            //判断上传的文件是否符合规则
            //查看文件属不属于图片
            BufferedImage read = ImageIO.read(part.getInputStream());
            if(read==null){
                throw new AdminExistException(AxiosStatus.NOT_IMAGE);
            }
            //如果是图片，判断是否是jpg或者png
            if(!uploadProperties.getUploadExt().contains(StringUtils.getFilenameExtension(part.getSubmittedFileName())))
               throw new AdminExistException(AxiosStatus.IMG_TYPE_ERROR);
            //判断图片大小
            long size = part.getSize() / 1024; //获取图片大小（kb）
            if(size>uploadProperties.getUploadSize()){
                throw new AdminExistException(AxiosStatus.IMG_TOO_LARGE);
            }
            //判断图片宽高
            int width = read.getWidth();
            int height = read.getHeight();
            if(width!=uploadProperties.getImgWidth() ||height!=uploadProperties.getImgHeight()){
                throw new AdminExistException(AxiosStatus.IMG_SIZE_ERR);
            }

            String fileName = UUID.randomUUID().toString()+"."+StringUtils.getFilenameExtension(part.getSubmittedFileName());

            OSS ossClient = new OSSClientBuilder().build(uploadProperties.getEndPoint(), uploadProperties.getAccessKeyId(), uploadProperties.getAccessKeySecret());
            //第一参数： 表示bucket名称
            //第二个参数： 文件名称 携带后缀
            ossClient.putObject(uploadProperties.getBucket(), fileName, part.getInputStream());
            ossClient.shutdown();
            String url = uploadProperties.getBaseUrl() + fileName;
            return url;
        } catch (IOException e) {
            throw  new AdminExistException(AxiosStatus.IMG_UPLOAD_ERROR);
        }
    }
}
