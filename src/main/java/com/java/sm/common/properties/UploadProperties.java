package com.java.sm.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 722A-08-CXB
 * @version 1.0.0
 * @ClassName UploadProperties.java
 * @DescriPtion 阿里云文件上传配置类
 * @CreateTime 2021年06月26日 20:18:00
 */
@Component
@Data
@ConfigurationProperties(prefix = "aliyun")
public class UploadProperties {
    private String endPoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucket;
    private String baseUrl;
    private List<String> uploadExt;
    private Long uploadSize;
    private Integer imgWidth;
    private Integer imgHeight;
}
