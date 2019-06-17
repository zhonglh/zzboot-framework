package com.zzboot.framework.config;



import com.zzboot.framework.bean.CaptchaBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import static com.google.code.kaptcha.Constants.*;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES;


/**
 * 验证码配置
 */
@Slf4j
@Configuration
@ConditionalOnBean(CaptchaBean.class)
public class CaptchaConfig {


    @Autowired
    private CaptchaBean kaptcha;

    /**
     * 配置验证码
     *
     * @return DefaultKaptcha
     */
    @Bean
    public DefaultKaptcha captchaProducer() {
        DefaultKaptcha captchaProducer = new DefaultKaptcha();
        java.util.Properties properties = new java.util.Properties();
        properties.setProperty(KAPTCHA_BORDER, kaptcha.getBorder());
        properties.setProperty(KAPTCHA_IMAGE_WIDTH, kaptcha.getImageWidth());
        properties.setProperty(KAPTCHA_IMAGE_HEIGHT, kaptcha.getImageHeight());
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, kaptcha.getTextProducerFontColor());
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, kaptcha.getTextProducerFontSize());
        properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, kaptcha.getTextProducerImpl());
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, kaptcha.getTextProducerCharLength());
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, kaptcha.getTextProducerFontNames());
        Config config = new Config(properties);
        captchaProducer.setConfig(config);
        return captchaProducer;
    }


}
