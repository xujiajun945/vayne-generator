<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${config.targetDao + "." + model.mapperName}">
    <resultMap id="BaseResultMap" type="${config.targetModel + "." + model.modelName}">
        <id column="${model.primaryKeyColumnName}" property="${model.primaryKey}"/>
        <#list model.columnModelList as item>
        <#if item.isPrimaryKey == false>
        <result column="${item.columnName}" property="${item.name}"/>
        </#if>
        </#list>
    </resultMap>

    <sql id="Base_Column_List">
        ${model.baseColumnList}
    </sql>

    <select id="getBy${model.primaryKeyFistWordUppercase}" resultMap="BaseResultMap">
        SELECT
            <include refid="Base_Column_List"/>
        FROM
            ${model.tableName}
        WHERE
            ${model.primaryKeyColumnName} = ${"#{" + model.primaryKey + "}"}
    </select>

    <#if model.havingLogicDeleted == false>
    <delete id="deleteBy${model.primaryKeyFistWordUppercase}">
        DELETE FROM
            ${model.tableName}
        WHERE
            ${model.primaryKeyColumnName} = ${"#{" + model.primaryKey + "}"}
    </delete>

    </#if>
    <#if model.havingLogicDeleted == true>
    <update id="removeBy${model.primaryKeyFistWordUppercase}">
        UPDATE
            ${model.tableName}
        SET
            is_deleted = true
        WHERE
            ${model.primaryKeyColumnName} = ${"#{" + model.primaryKey + "}"}
    </update>

    </#if>
    <insert id="insert" useGeneratedKeys="true" keyProperty="${model.primaryKey}">
        INSERT INTO ${model.tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list model.columnModelList as item>
            <if test="${item.name} != null">
                ${item.columnName},
            </if>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list model.columnModelList as item>
            <if test="${item.name} != null">
                ${"#{" + item.name + "}"},
            </if>
            </#list>
        </trim>
    </insert>

    <update id="updateBy${model.primaryKeyFistWordUppercase}">
        UPDATE ${model.tableName}
        <set>
            <#list model.columnModelList as item>
            <if test="${item.name} != null">
                ${item.columnName} = ${"#{" + item.name + "},"}
            </if>
            </#list>
        </set>
        WHERE ${model.primaryKeyColumnName} = ${"#{" + model.primaryKey + "}"}
    </update>
</mapper>