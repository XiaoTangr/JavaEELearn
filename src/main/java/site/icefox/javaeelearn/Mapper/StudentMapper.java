package site.icefox.javaeelearn.Mapper;

import site.icefox.javaeelearn.Entity.StudentEntity;

import java.util.List;

public interface StudentMapper {
    List<StudentEntity> findAllStudents();
}
