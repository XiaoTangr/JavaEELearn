package site.icefox.javaeelearn.Mapper;

import site.icefox.javaeelearn.Entity.PersonEntity;

public interface PersonMapper {
    PersonEntity findPersonById(int id);
}
