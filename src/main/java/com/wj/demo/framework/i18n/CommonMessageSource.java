package com.wj.demo.framework.i18n;

import com.wj.demo.framework.common.constant.I18nConstant;
import com.wj.demo.framework.common.constant.SymbolicConstant;
import com.wj.demo.framework.common.utils.CollectionUtils;
import com.wj.demo.framework.common.utils.StringUtils;
import com.wj.demo.framework.i18n.entity.SysI18nEntity;
import com.wj.demo.framework.i18n.service.SysI18nService;
import com.wj.demo.framework.redis.service.RedisClient;
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
    private SysI18nService sysI18nService;

    @Resource
    private RedisClient redisClient;

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    /**
     * 初始化
     */
    private void init() {

        //  1.判断缓存是否存在
        if (CollectionUtils.isNotEmpty(redisClient.keys(I18nConstant.CACHE_KEY + SymbolicConstant.COLON + SymbolicConstant.ASTERISK))) {
            return;
        }

        log.debug("I18n cache init start.............");

        //  2.查询数据库
        List<SysI18nEntity> sysI18nEntityList = sysI18nService.list();
        Map<String, Map<String, String>> messageMap = sysI18nEntityList
                .stream()
                .collect(Collectors.groupingBy(
                        // 根据国家地区分组
                        SysI18nEntity::getLanguage,
                        // 收集为Map,key为code,value为信息
                        Collectors.toMap(SysI18nEntity::getCode, SysI18nEntity::getLabel)
                ));

        // 获取国家地区List
        List<Locale> localeList = messageMap.keySet().stream().map(lang -> Locale.of(lang.split(SymbolicConstant.UNDERLINE)[0], lang.split(SymbolicConstant.UNDERLINE)[1])).toList();
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

            // 保存本语言到缓存中
            redisClient.hashPutAll(I18nConstant.CACHE_KEY + SymbolicConstant.COLON + locale, codeAndMsgMap);
        }


        log.debug("I18n cache init end.............");
    }


    /**
     * 通过信息获取多语言信息
     *
     * @param msg 信息
     * @return 详情
     */
    public SysI18nEntity getLanguageByMsg(String msg) {
        // 获取所有语言对应的国际化信息
        Map<String, Map<String, String>> messageMap = redisClient.hashGetAll(I18nConstant.CACHE_KEY + SymbolicConstant.COLON + SymbolicConstant.ASTERISK);

        SysI18nEntity sysI18nEntity = new SysI18nEntity();
        sysI18nEntity.setLabel(msg);
        a:
        for (Map.Entry<String, Map<String, String>> entry : messageMap.entrySet()) {
            String currentLocale = entry.getKey();
            Map<String, String> codeAndMessageMap = entry.getValue();
            for (Map.Entry<String, String> itemEntry : codeAndMessageMap.entrySet()) {
                String currentCode = itemEntry.getKey();
                String currentMessage = itemEntry.getValue();
                if (msg.equals(currentMessage)) {
                    sysI18nEntity.setCode(currentCode);
                    sysI18nEntity.setLanguage(currentLocale);
                    break a;
                }
            }
        }

        return sysI18nEntity;
    }

    /**
     * 将信息转化到对应国际化
     *
     * @param msg          信息
     * @param targetLocale 目标语言
     * @return 转换后的信息
     */
    public String transferMsg(String msg, Locale targetLocale) {
        SysI18nEntity sysI18nEntity = getLanguageByMsg(msg);
        String language = sysI18nEntity.getLanguage();
        String code = sysI18nEntity.getCode();
        if (StringUtils.isEmpty(language) || StringUtils.isEmpty(code)) {
            return msg;
        }
        return getMessage(code, null, targetLocale);
    }

    /**
     * 更新本地缓存 批量
     *
     * @param entityList 批量数据
     */
    public void saveOrUpdateLocalCacheBatch(List<SysI18nEntity> entityList) {
        // 分组
        Map<String, List<SysI18nEntity>> i18nMap = entityList.stream().collect(Collectors.groupingBy(SysI18nEntity::getLanguage));
        // 更新缓存
        i18nMap.forEach((language, list) -> {
            Map<String, String> messageMap = redisClient.hashMultiGet(I18nConstant.CACHE_KEY + SymbolicConstant.COLON + language);
            list.forEach(entity -> messageMap.put(entity.getCode(), entity.getLabel()));
            redisClient.hashPutAll(I18nConstant.CACHE_KEY + SymbolicConstant.COLON + language, messageMap);
        });
    }

    /**
     * 更新本地缓存
     *
     * @param locale  语言
     * @param code    编码
     * @param message 信息
     */
    public void saveOrUpdateLocalCache(String locale, String code, String message) {
        Map<String, String> messageMap = redisClient.hashMultiGet(I18nConstant.CACHE_KEY + SymbolicConstant.COLON + locale);
        if (messageMap == null) {
            messageMap = new HashMap<>();
        }
        messageMap.put(code, message);
        redisClient.hashPutAll(I18nConstant.CACHE_KEY + SymbolicConstant.COLON + locale, messageMap);
    }

    /**
     * 获取语言编码
     *
     * @param locale 语言
     * @return 语言编码
     */
    public String getLanguageByLocale(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String language = getLanguageByLocale(locale);

        String message = sysI18nService.queryMessage(code, language);

        return new MessageFormat(message, locale);
    }

    /**
     * 对外提供方法 先判断配置文件有没有对应语言的默认值，有返回默认值，没有继续查询
     * 配备多级存储 1.静态文件 2.缓存 3.数据库
     *
     * @param code   编码
     * @param locale 语言
     * @return 信息
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
     * 批量查询
     *
     * @param codeList 批量查询
     * @param locale   语言
     * @return 批量结果
     */
    public Map<String, String> getMessagesPlus(List<String> codeList, Locale locale) {
        String language = getLanguageByLocale(locale);
        return sysI18nService.queryMessageList(codeList, language);
    }

}
