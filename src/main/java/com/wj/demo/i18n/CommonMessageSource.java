package com.wj.demo.i18n;

import com.wj.demo.i18n.service.LanguageService;
import jakarta.annotation.Resource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author wj
 * @version 1.0
 * @Desc 如果静态文件初始化的话可以实现接口InitializingBean，初始化国际化信息到内存
 * @date 2024/4/17 15:41
 */
@Component
public class CommonMessageSource extends AbstractMessageSource {

    @Resource
    private LanguageService languageService;

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String language = getLanguageFromLocale(locale);
        String message = languageService.queryMessage(code, language);
        return new MessageFormat(message, locale);
    }

    /**
     * todo 对外提供方法 先判断配置文件有没有对应语言的默认值，有返回默认值，没有继续查询
     * 配备多级存储 1静态文件 2缓存 3数据库
     *
     * @param code
     * @param locale
     * @return
     */
    public String getMessagePlus(String code, Locale locale) {
        String msg = this.getMessageInternal(code, null, locale);
        if (msg != null) {
            return msg;
        } else {
            String fallback = this.getDefaultMessage(code);
            if (fallback != null) {
                return fallback;
            } else {
                throw new NoSuchMessageException(code, locale);
            }
        }
    }

    /**
     * todo 批量查询 思路同 getMessagePlus
     *
     * @param codeList
     * @param locale
     * @return
     */
    public Map<String, String> getMessagesPlus(List<String> codeList, Locale locale) {
        String language = getLanguageFromLocale(locale);
        return languageService.queryMessageList(codeList, language);
    }

    /**
     * 获取语言编码
     *
     * @param locale
     * @return
     */
    public String getLanguageFromLocale(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();
    }
}
