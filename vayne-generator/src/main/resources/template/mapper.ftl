<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${daoReference}">
    <resultMap id="BaseResultMap" type="${modelReference}">
        <id column="${primaryKeyColumn.underlineColumnName}" property="${primaryKeyColumn.columnName}"/>
        <#list javaColumnList as item>
            <#if item.primaryKey == false>
                <result column="${item.underlineColumnName}" property="${item.columnName}"/>
            </#if>
        </#list>
    </resultMap>

    <sql id="Base_Column_List">
        ${baseColumnList}
    </sql>

    <select id="getBy${primaryKeyColumn.columnNameFistWordUppercase}" parameterType="${primaryKeyColumn.javaFullTypeName}" resultMap="BaseResultMap">
        select
            <include refid="Base_Column_List"/>
        from
            ${tableName}
        where
            ${primaryKeyColumn.underlineColumnName} = ${"#{" + primaryKeyColumn.columnName + "}"}
    </select>

    <delete id="deleteBy${primaryKeyColumn.columnNameFistWordUppercase}" parameterType="${primaryKeyColumn.javaFullTypeName}">
        delete from
            ${tableName}
        where
            ${primaryKeyColumn.underlineColumnName} = ${"#{" + primaryKeyColumn.columnName + "}"}
    </delete>

    <#if havingLogicDeleted?? && (havingLogicDeleted == true)>
        <update id="removeBy${primaryKeyColumn.columnNameFistWordUppercase}" parameterType="${primaryKeyColumn.javaFullTypeName}">
            update
                ${tableName}
            set
                is_deleted = true
            where
                ${primaryKeyColumn.underlineColumnName} = ${"#{" + primaryKeyColumn.columnName + "}"}
        </update>

    </#if>
    <insert id="insert" parameterType="${modelReference}" useGeneratedKeys="true" keyProperty="${primaryKeyColumn.columnName}">
        insert into ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list javaColumnList as item>
            <if test="${item.columnName} != null">
                ${item.underlineColumnName},
            </if>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list javaColumnList as item>
            <if test="${item.columnName} != null">
                ${"#{" + item.columnName + "}"},
            </if>
            </#list>
        </trim>
    </insert>

    <update id="updateBy${primaryKeyColumn.columnNameFistWordUppercase}" parameterType="${modelReference}">
        update ${tableName}
        <set>
            <#list javaColumnList as item>
            <if test="${item.columnName} != null">
                ${item.underlineColumnName} = ${"#{" + item.columnName + "},"}
            </if>
            </#list>
        </set>
        where ${primaryKeyColumn.underlineColumnName} = ${"#{" + primaryKeyColumn.columnName + "}"}
    </update>
</mapper>