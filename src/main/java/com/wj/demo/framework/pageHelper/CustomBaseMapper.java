package com.wj.demo.framework.pageHelper;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.FlexConsts;
import com.mybatisflex.core.field.FieldQueryBuilder;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Row;
import com.mybatisflex.core.util.MapperUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cursor.Cursor;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @ClassName CustomBaseMapper
 * @Description: 自定义BaseMapper，解决Flex链式查询的时候链式查询不能使用分页的问题 todo 尝试增加where标签搞定
 * @Author: W.Jian
 * @CreateDate: 2025/8/22 23:13
 * @Version:
 */
public interface CustomBaseMapper<T> extends BaseMapper<T> {

    @Override
    List<T> selectListByQuery(@Param(FlexConsts.QUERY) QueryWrapper queryWrapper);

    @Override
    Cursor<T> selectCursorByQuery(@Param(FlexConsts.QUERY) QueryWrapper queryWrapper);

    @Override
    List<Row> selectRowsByQuery(@Param(FlexConsts.QUERY) QueryWrapper queryWrapper);
}
