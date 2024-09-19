package site.icefox.javaeelearn.Mapper;

import site.icefox.javaeelearn.Entity.StudentsEntity;

import java.util.List;

public interface StudentsMapper {
    List<StudentsEntity> findAllStudents();
}
