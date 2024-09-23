package site.icefox.javaeelearn.Mapper;

import site.icefox.javaeelearn.Entity.ProductEntity;

public interface ProductMapper {
    ProductEntity findProductById(int id);
}
