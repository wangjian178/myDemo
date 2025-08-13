package com.wj.demo.framework.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @ClassName ImageTypeEnum
 * @Description: 图片类型
 * @Author: W.Jian
 * @CreateDate: 2025/8/13 23:03
 * @Version:
 */
@AllArgsConstructor
@Getter
public enum ImageTypeEnum {

    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    GIF("gif"),
    BMP("bmp"),
    WEBP("webp");

    private final String type;

    /**
     * 根据类型获取枚举
     * @param type  类型
     * @return 枚举
     */
    public static ImageTypeEnum getImageTypeEnum(String type) {
        return Arrays.stream(ImageTypeEnum.values()).filter(x -> x.getType().equals(type)).findFirst().orElse(null);
    }
}
