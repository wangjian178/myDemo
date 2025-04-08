package com.wj.demo.framework.mybatis.page.interceptor;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ParameterUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.DialectModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.dialects.IDialect;
import com.wj.demo.framework.mybatis.page.PageContext;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DynamicPaginationInterceptor
 * @Description: 动态分页拦截器
 * @Author: W.Jian
 * @CreateDate: 2025/4/7 15:12
 * @Version:
 */
@Component
@Intercepts(
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        )
)
public class MybatisPaginationInterceptor extends PaginationInnerInterceptor {

    /**
     * 判断是否继续分页
     */
    @Override
    public boolean willDoQuery(Executor executor,
                               MappedStatement ms,
                               Object parameter,
                               RowBounds rowBounds,
                               ResultHandler resultHandler,
                               BoundSql boundSql) throws SQLException {

        //分页参数
        IPage<?> page = ParameterUtils.findPage(parameter).orElse(PageContext.getPage());

        //不分页
        if (page == null || page.getSize() < 0L || !page.searchCount() || Executor.NO_RESULT_HANDLER != resultHandler) {
            return true;
        }

        //分页
        MappedStatement countMs = this.buildCountMappedStatement(ms, page.countId());
        BoundSql countSql;
        if (countMs != null) {
            countSql = countMs.getBoundSql(parameter);
        } else {
            countMs = this.buildAutoCountMappedStatement(ms);
            String countSqlStr = this.autoCountSql(page, boundSql.getSql());
            PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);
            countSql = new BoundSql(countMs.getConfiguration(), countSqlStr, mpBoundSql.parameterMappings(), parameter);
            PluginUtils.setAdditionalParameter(countSql, mpBoundSql.additionalParameters());
        }

        //统计总数
        CacheKey cacheKey = executor.createCacheKey(countMs, parameter, rowBounds, countSql);
        List<Object> result = executor.query(countMs, parameter, rowBounds, resultHandler, cacheKey, countSql);
        long total = 0L;
        if (CollectionUtils.isNotEmpty(result)) {
            Object o = result.getFirst();
            if (o != null) {
                total = Long.parseLong(o.toString());
            }
        }

        page.setTotal(total);
        return this.continuePage(page);
    }

    /**
     * 处理分页sql
     */
    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        //分页参数
        IPage<?> page = ParameterUtils.findPage(parameter).orElse(PageContext.getPage());
        if (null == page) {
            return;
        }
        boolean addOrdered = false;
        String buildSql = boundSql.getSql();
        List<OrderItem> orders = page.orders();
        if (CollectionUtils.isNotEmpty(orders)) {
            addOrdered = true;
            buildSql = this.concatOrderBy(buildSql, orders);
        }

        Long limit = page.maxLimit() != null ? page.maxLimit() : this.maxLimit;
        if (page.getSize() < 0L && null == limit) {
            if (addOrdered) {
                PluginUtils.mpBoundSql(boundSql).sql(buildSql);
            }

        } else {
            this.handlerLimit(page, limit);
            IDialect dialect = this.findIDialect(executor);
            Configuration configuration = ms.getConfiguration();
            DialectModel model = dialect.buildPaginationSql(buildSql, page.offset(), page.getSize());
            PluginUtils.MPBoundSql mpBoundSql = PluginUtils.mpBoundSql(boundSql);
            List<ParameterMapping> mappings = mpBoundSql.parameterMappings();
            Map<String, Object> additionalParameter = mpBoundSql.additionalParameters();
            model.consumers(mappings, configuration, additionalParameter);
            mpBoundSql.sql(model.getDialectSql());
            mpBoundSql.parameterMappings(mappings);
        }
    }
}
