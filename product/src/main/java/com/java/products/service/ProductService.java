package com.java.products.service;

import com.java.products.dto.ProductDTO;
import com.java.products.model.Product;
import com.java.products.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return modelMapper.map(productList, new TypeToken<List<ProductDTO>>(){}.getType());
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product saved = productRepository.save(modelMapper.map(productDTO, Product.class));
        return modelMapper.map(saved, ProductDTO.class);
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product saved = productRepository.save(modelMapper.map(productDTO, Product.class));
        return modelMapper.map(saved, ProductDTO.class);
    }

    public String deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
        return "Product deleted successfully";
    }

    public ProductDTO getProductById(Integer productId) {
        Product product = productRepository.getProductById(productId);
        return modelMapper.map(product, ProductDTO.class);
    }

}
