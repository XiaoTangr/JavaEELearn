package site.icefox.javaeelearn.Entity;

import lombok.Data;

@Data
public class IdCardEntity {
    private Integer id;                 // 主键id
    private String code;              // 身份证号码
}