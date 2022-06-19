package gareeva.ProductReviews;

import static org.junit.Assert.*;

import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class ProductReviewsTest {

    @Autowired
    private MockMvc mvc;

    public void createProductTest() throws Exception {
        String content = "{\"id\":1,\"description\":\"description1\",\"reviewList\":[],\"meanRating\":0.0}";
        mvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"description1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(content));

        content = "{\"id\":2,\"description\":\"description2\",\"reviewList\":[],\"meanRating\":0.0}";
        mvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"description2\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string(content));
    }

    public void createProductErrorTest() throws Exception {
        mvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(" Description can not be empty",
                        Objects.requireNonNull(result.getResolvedException()).getMessage().split("[:;]")[1]));
    }


    public void addReviewTest() throws Exception {
        String content = "{\"id\":1,\"message\":\"message1\",\"rating\":2,\"productId\":1}";
        mvc.perform(post("/product/1/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"message1\", \"rating\":2}"))
                .andExpect(status().isOk())
                .andExpect(content().string(content));

        content = "{\"id\":2,\"message\":\"message2\",\"rating\":5,\"productId\":1}";
        mvc.perform(post("/product/1/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"message2\", \"rating\":5}"))
                .andExpect(status().isOk())
                .andExpect(content().string(content));
    }

    public void addReviewErrorTest() throws Exception {
        mvc.perform(post("/product/100/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"message2\", \"rating\":5}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("400 BAD_REQUEST \"ProductId 100 not found\"",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));

        mvc.perform(post("/product/1/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"\", \"rating\":5}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(" Message can not be empty",
                        Objects.requireNonNull(result.getResolvedException()).getMessage().split("[:;]")[1]));

        mvc.perform(post("/product/1/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"message\", \"rating\":100}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(" Rating should be from 0 to 5",
                        Objects.requireNonNull(result.getResolvedException()).getMessage().split("[:;]")[1]));

        mvc.perform(post("/product/1/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"message\":\"message\", \"rating\":-100}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals(" Rating should be from 0 to 5",
                        Objects.requireNonNull(result.getResolvedException()).getMessage().split("[:;]")[1]));
    }

    public void getProductTest() throws Exception {
        String content = "{\"id\":1,\"description\":\"description1\",\"reviewList\":[{\"id\":1,\"message\":\"message1\",\"rating\":2,\"productId\":1},{\"id\":2,\"message\":\"message2\",\"rating\":5,\"productId\":1}],\"meanRating\":3.5}";
        mvc.perform(get("/product/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(content));

        content = "{\"id\":2,\"description\":\"description2\",\"reviewList\":[],\"meanRating\":0.0}";
        mvc.perform(get("/product/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(content));
    }

    public void getProductErrorTest() throws Exception {
        mvc.perform(get("/product/100"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("400 BAD_REQUEST \"ProductId 100 not found\"",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    public void deleteProductTest() throws Exception {
        mvc.perform(post("/product/2/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\":\"message3\", \"rating\":1}"));
        mvc.perform(post("/product/2/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\":\"message4\", \"rating\":5}"));

        String content = "{\"id\":2,\"description\":\"description2\",\"reviewList\":[{\"id\":3,\"message\":\"message3\",\"rating\":1,\"productId\":2},{\"id\":4,\"message\":\"message4\",\"rating\":5,\"productId\":2}],\"meanRating\":3.0}";
        mvc.perform(get("/product/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(content));

        mvc.perform(delete("/product/1"))
                .andExpect(status().isOk());

        mvc.perform(get("/product/1"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("400 BAD_REQUEST \"ProductId 1 not found\"",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
        mvc.perform(get("/product/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(content));
    }

    public void deleteErrorProductTest() throws Exception {
        mvc.perform(delete("/product/1"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertEquals("400 BAD_REQUEST \"ProductId 1 not found\"",
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void Test() throws Exception {
        createProductTest();
        createProductErrorTest();
        addReviewTest();
        addReviewErrorTest();
        getProductTest();
        getProductErrorTest();
        deleteProductTest();
        deleteErrorProductTest();
    }
}