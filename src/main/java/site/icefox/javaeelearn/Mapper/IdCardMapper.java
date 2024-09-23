package site.icefox.javaeelearn.Mapper;

import site.icefox.javaeelearn.Entity.IdCardEntity;

public interface IdCardMapper {
    IdCardEntity findCodeById(Integer id);
}
