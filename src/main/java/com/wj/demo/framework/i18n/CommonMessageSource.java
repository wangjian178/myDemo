package com.wj.demo.framework.i18n;

import com.wj.demo.framework.baseContext.BaseContextHolder;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.i18n.entity.SysLanguageEntity;
import com.wj.demo.framework.i18n.service.SysLanguageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author wj
 * @version 1.0
 * @Desc 如果静态文件初始化的话可以实现接口InitializingBean，初始化国际化信息到内存
 * @date 2024/4/17 15:41
 */
@Component
@Slf4j
public class CommonMessageSource extends AbstractMessageSource implements InitializingBean {

    @Value("${spring.messages.basename}")
    private String basename;

    @Resource
    private SysLanguageService sysLanguageService;

    /**
     * key1 = locale key2 = code ,value = message
     */
    private static final Map<String, Map<String, String>> LOCAL_CACHE = new HashMap<>();


    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        log.debug("LOCAL_CACHE init start.............");
        //  1.清空本地缓存
        LOCAL_CACHE.clear();

        //  2.查询数据库
        List<SysLanguageEntity> sysLanguageEntityList = sysLanguageService.list();
        Map<String, Map<String, String>> messageMap = sysLanguageEntityList
                .stream()
                .collect(Collectors.groupingBy(
                        // 根据国家地区分组
                        SysLanguageEntity::getLanguage,
                        // 收集为Map,key为code,value为信息
                        Collectors.toMap(
                                SysLanguageEntity::getCode
                                , SysLanguageEntity::getLabel
                        )
                ));

        // 获取国家地区List
        List<Locale> localeList = messageMap.keySet().stream().map(lang -> new Locale(lang.split("_")[0], lang.split("_")[1])).toList();
        for (Locale locale : localeList) {
            // 按照国家地区来读取本地的国际化资源文件,我们的国际化资源文件放在i18n文件夹之下
            ResourceBundle resourceBundle = ResourceBundle.getBundle(basename, locale);
            // 获取国际化资源文件中的key和value
            Set<String> keySet = resourceBundle.keySet();

            // 将 code=信息 格式的数据收集为 Map<code,信息> 的格式
            Map<String, String> localMsgMap = keySet.stream()
                    .collect(
                            Collectors.toMap(
                                    Function.identity(),
                                    resourceBundle::getString
                            )
                    );

            // 将本地的国际化信息和数据库中的国际化信息合并
            Map<String, String> codeAndMsgMap = messageMap.getOrDefault(locale.toString(), new HashMap<>());
            codeAndMsgMap.putAll(localMsgMap);
            messageMap.put(locale.toString(), codeAndMsgMap);
        }

        LOCAL_CACHE.putAll(messageMap);

        log.debug("LOCAL_CACHE init end.............");
    }


    /**
     * 通过信息获取多语言信息
     *
     * @param msg
     * @return
     */
    public SysLanguageEntity getLanguageByMsg(String msg) {
        SysLanguageEntity sysLanguageEntity = new SysLanguageEntity();
        sysLanguageEntity.setLabel(msg);
        a:
        for (Map.Entry<String, Map<String, String>> entry : LOCAL_CACHE.entrySet()) {
            String currentLocale = entry.getKey();
            Map<String, String> codeAndMessageMap = entry.getValue();
            b:
            for (Map.Entry<String, String> itemEntry : codeAndMessageMap.entrySet()) {
                String currentCode = itemEntry.getKey();
                String currentMessage = itemEntry.getValue();
                if (msg.equals(currentMessage)) {
                    sysLanguageEntity.setCode(currentCode);
                    sysLanguageEntity.setLanguage(currentLocale);
                    break a;
                }
            }
        }

        return sysLanguageEntity;
    }

    /**
     * 将信息转化到对应国际化
     *
     * @param msg
     * @param targetLocale
     * @return
     */
    public String transferMsg(String msg, Locale targetLocale) {
        SysLanguageEntity sysLanguageEntity = getLanguageByMsg(msg);
        String language = sysLanguageEntity.getLanguage();
        String code = sysLanguageEntity.getCode();
        if (StringUtils.isEmpty(language) || StringUtils.isEmpty(code)) {
            return msg;
        }
        return getMessage(code, null, BaseContextHolder.getBaseContext().getLocale());
    }

    /**
     * 更新本地缓存 批量
     *
     * @param entityList
     */
    public void saveOrUpdateLocalCacheBatch(List<SysLanguageEntity> entityList) {
        for (SysLanguageEntity entity : entityList) {
            Map<String, String> codeAndMsgMap = LOCAL_CACHE.getOrDefault(entity.getLanguage(), new HashMap<>());
            codeAndMsgMap.put(entity.getCode(), entity.getLabel());
        }
    }

    /**
     * 更新本地缓存
     *
     * @param locale
     * @param code
     * @param message
     */
    public void saveOrUpdateLocalCache(String locale, String code, String message) {
        Map<String, String> codeAndMsgMap = LOCAL_CACHE.getOrDefault(locale, new HashMap<>());
        codeAndMsgMap.put(code, message);
    }

    /**
     * 获取语言编码
     *
     * @param locale
     * @return
     */
    public String getLanguageByLocale(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String language = getLanguageByLocale(locale);

        String message = null;

        //  1.先取本地缓存
        if (LOCAL_CACHE.containsKey(language)) {
            Map<String, String> codeAndMsgMap = LOCAL_CACHE.getOrDefault(language, new HashMap<>());
            message = codeAndMsgMap.get(code);
        }

        //  2.再取redis缓存和数据库
        if (StringUtils.isEmpty(message)) {
            message = sysLanguageService.queryMessage(code, language);
        }

        return new MessageFormat(message, locale);
    }

    /**
     * todo 对外提供方法 先判断配置文件有没有对应语言的默认值，有返回默认值，没有继续查询
     * 配备多级存储 1.静态文件 2.缓存 3.数据库
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
        String language = getLanguageByLocale(locale);
        return sysLanguageService.queryMessageList(codeList, language);
    }

}
