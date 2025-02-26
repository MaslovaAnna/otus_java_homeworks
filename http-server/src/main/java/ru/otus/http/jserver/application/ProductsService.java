package ru.otus.http.jserver.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ProductsService {
    private List<Product> products;

    public ProductsService() {
        this.products = new ArrayList<>(Arrays.asList(
                new Product(1L, "Milk"),
                new Product(2L, "Bread"),
                new Product(3L, "Cheese")
        ));
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public Product getProductById(Long id) {
        return products.stream().filter(p -> p.getId().equals(id)).findFirst().get();
    }

    public void createNewProduct(Product product) {
        Long newId = products.stream().mapToLong(Product::getId).max().getAsLong() + 1;
        products.add(new Product(newId, product.getTitle()));
    }
    public boolean deleteProductById(Long id) {
        int index = getIndexOfProductById(id);
        if(index != -1) {
            products.removeIf(p -> p.getId().equals(id));
            return true;
        }
        return false;
    }

    public void deleteAllProducts() {
        products.clear();
    }

    public boolean updateProductById(Product product) {
        Long id = product.getId();
        int index = getIndexOfProductById(id);
        if(index != -1) {
            products.set(index, product);
            return true;
        }
        return false;
    }

    public int getIndexOfProductById(Long id) {
        int index = IntStream.range(0, products.size())
                .filter(i -> products.get(i).getId().equals(id))
                .findFirst()
                .orElse(-1);

        return index;
    }
}