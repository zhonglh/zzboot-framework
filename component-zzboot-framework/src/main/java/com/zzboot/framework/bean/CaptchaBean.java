package com.zzboot.framework.bean;


import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@ConditionalOnResource(resources={"captcha.properties"})

@ConfigurationProperties(prefix="captcha")
@PropertySource("classpath:captcha.properties")
@Component
public class CaptchaBean {


    /**
     * 是否有边框
     */
    private String border = "no";


    /**
     * 图像宽度
     */
    private String imageWidth = "240";

    /**
     * 图像高度
     */
    private String imageHeight = "80";


    /**
     * 文本生产者字体颜色
     */
    private String textProducerFontColor = "black";

    /**
     * 文字字体大小
     */
    private String textProducerFontSize = "60";

    /**
     * 文字制片人实现
     */
    private String textProducerImpl = "com.google.code.kaptcha.impl.ShadowGimpy";

    /**
     * 文本字符长度
     */
    private String textProducerCharLength = "4";


    /**
     * 文本字体名称
     */
    private String textProducerFontNames = "宋体,楷体,微软雅黑";


    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getTextProducerFontColor() {
        return textProducerFontColor;
    }

    public void setTextProducerFontColor(String textProducerFontColor) {
        this.textProducerFontColor = textProducerFontColor;
    }

    public String getTextProducerFontSize() {
        return textProducerFontSize;
    }

    public void setTextProducerFontSize(String textProducerFontSize) {
        this.textProducerFontSize = textProducerFontSize;
    }

    public String getTextProducerImpl() {
        return textProducerImpl;
    }

    public void setTextProducerImpl(String textProducerImpl) {
        this.textProducerImpl = textProducerImpl;
    }

    public String getTextProducerCharLength() {
        return textProducerCharLength;
    }

    public void setTextProducerCharLength(String textProducerCharLength) {
        this.textProducerCharLength = textProducerCharLength;
    }

    public String getTextProducerFontNames() {
        return textProducerFontNames;
    }

    public void setTextProducerFontNames(String textProducerFontNames) {
        this.textProducerFontNames = textProducerFontNames;
    }
}
