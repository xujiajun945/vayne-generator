package ${config.targetModel};

import java.io.Serializable;
<#list model.importPackages as item>
import ${item};
</#list>

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * ${model.modelName}实体类
 *
 * @author ${config.author}
 * @since ${config.date}
 */
@Getter
@Setter
@ToString
public class ${model.modelName} implements Serializable {

    private static final long serialVersionUID = 1L;

<#list model.columnModelList as item>
    /**
     * ${item.remark}
     */
    private ${item.type} ${item.name};

</#list>
}