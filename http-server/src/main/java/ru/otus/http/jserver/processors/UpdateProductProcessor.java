package ru.otus.http.jserver.processors;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import ru.otus.http.jserver.HttpRequest;
import ru.otus.http.jserver.application.Product;
import ru.otus.http.jserver.application.ProductsService;
import ru.otus.http.jserver.application.ResponseBody;

public class UpdateProductProcessor implements RequestProcessor {


    private ProductsService productsService;
    private ResponseBody responseBody;

    public UpdateProductProcessor(ProductsService productsService) {
        this.productsService = productsService;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String jsonResult;
        Gson gson = new Gson();
        Product updatedProduct = gson.fromJson(request.getBody(), Product.class);
        if(productsService.updateProductById(updatedProduct)) {
            responseBody = new ResponseBody("Продукт успешно обновлён");
        } else {
            responseBody = new ResponseBody("Продукт с id не найден");
        }
        jsonResult = gson.toJson(responseBody);
        String response = "" +
                          "HTTP/1.1 200 OK\r\n" +
                          "Content-Type: text/html\r\n" +
                          "\r\n" +
                          jsonResult;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
