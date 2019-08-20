package ${config.targetDao};

import ${config.targetModel + "." + model.modelName};

/**
 * ${model.tableRemark}数据访问接口
 *
 * @author ${config.author}
 * @since ${config.date}
 */
public interface ${model.modelName + "Mapper"} {

    /**
     * 查询
     *
     * @param ${model.primaryKey} ${model.primaryKey}
     * @return ${model.tableRemark}
     */
    ${model.modelName} getBy${model.primaryKeyFistWordUppercase}(${model.primaryKeyType} ${model.primaryKey});

    <#if model.havingLogicDeleted == false>
    /**
     * 删除
     *
     * @param ${model.primaryKey} ${model.primaryKey}
     * @return 影响的条数
     */
    int deleteBy${model.primaryKeyFistWordUppercase}(${model.primaryKeyType} ${model.primaryKey});

    </#if>
    <#if model.havingLogicDeleted == true>
    /**
     * 逻辑删除
     *
     * @param ${model.primaryKey} ${model.primaryKey}
     * @return 影响的条数
     */
    int removeBy${model.primaryKeyFistWordUppercase}(${model.primaryKeyType} ${model.primaryKey});

    </#if>
    /**
     * 更新
     *
     * @param ${model.modelNameFirstWordLowercase} ${model.tableRemark}
     * @return 影响的条数
     */
    int updateBy${model.primaryKeyFistWordUppercase}(${model.modelName} ${model.modelNameFirstWordLowercase});

    /**
     * 插入
     *
     * @param ${model.modelNameFirstWordLowercase} ${model.tableRemark}
     * @return 影响的条数
     */
    int insert(${model.modelName} ${model.modelNameFirstWordLowercase});

}