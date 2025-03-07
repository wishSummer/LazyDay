package io.github.wishsummer.common.log.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum BusinessTypeEnum {

    /**
     * 其它
     */
    OTHER(0, "其他"),

    /**
     * 新增
     */
    INSERT(1, "新增"),

    /**
     * 查询
     */
    SELECT(2, "查询"),

    /**
     * 修改
     */
    UPDATE(3, "修改"),

    /**
     * 删除
     */
    DELETE(4, "删除"),

    /**
     * 授权
     */
    GRANT(5, "授权"),

    /**
     * 导出
     */
    EXPORT(6, "导出"),

    /**
     * 导入
     */
    IMPORT(7, "导入"),

    /**
     * 强退
     */
    FORCE(8, "强退"),

    /**
     * 清空数据
     */
    CLEAN(9, "清空数据");

    private final Integer code;

    private final String info;

}
