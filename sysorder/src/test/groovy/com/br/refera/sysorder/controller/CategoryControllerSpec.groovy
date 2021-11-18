package com.br.refera.sysorder.controller

import com.br.refera.sysorder.entity.Category
import com.br.refera.sysorder.service.CategoryService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationFeature
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.util.concurrent.ThreadLocalRandom

class CategoryControllerSpec extends Specification {
    private CategoryService categoryService
    private CategoryController categoryController
    private MockMvc mockMvc

    void setup() {
        categoryService = Mock(CategoryService)
        categoryController = new CategoryController(categoryService)
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build()
    }

    def "Test Success for GET /categories/"() {
        given:
            categoryService.getListCategory() >> listCategories(4)
        when:
            def result = mockMvc.perform(MockMvcRequestBuilders.get("/categories"))
        then:
            result.andExpect(MockMvcResultMatchers.status().isOk())
    }

    def "Test Success for GET /categories/{id}"() {
        given:
            categoryService.getCategoryById(_ as Long) >> getCategory()
        when:
            def result = mockMvc.perform(MockMvcRequestBuilders.get("/categories/4"))
        then:
            result.andExpect(MockMvcResultMatchers.status().isOk())
    }

    def "Test Success for POST /categories"() {
        given:
            categoryService.createCategory(_ as Category) >> getCategory()
        when:
            def result = mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                    .content(getRequestJson(getCategory())).contentType(MediaType.APPLICATION_JSON_VALUE))
        then:
            result.andExpect(MockMvcResultMatchers.status().isCreated())
    }

    def "Test Success for PUT /categories/{id}"() {
        given:
            categoryService.updateCategory(_ as Category) >> getCategory()
        when:
            def result = mockMvc.perform(MockMvcRequestBuilders.put("/categories/3")
                    .content(getRequestJson(getCategory())).contentType(MediaType.APPLICATION_JSON_VALUE))
        then:
            result.andExpect(MockMvcResultMatchers.status().isOk())
    }

    def "Test Success for DELETE /categories/{id}"() {
        given:
            categoryService.deleteCategory(_ as Category) >> null
        when:
            def result = mockMvc.perform(MockMvcRequestBuilders.delete("/categories/4"))
        then:
            result.andExpect(MockMvcResultMatchers.status().isNoContent())
    }

    def getCategory() {
        return Category.builder()
                .id(ThreadLocalRandom.current().nextLong())
                .desc(RandomStringUtils.randomAlphabetic(15))
                .build()
    }

    def listCategories(qtd) {
        List<Category> categoryList = new ArrayList<>()
        for (int x = 0; x < qtd; x++) {
            categoryList.add(getCategory())
        }
        return  categoryList
    }

    def getRequestJson(param) {
        ObjectMapper mapper = new ObjectMapper()
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false)
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter()
        return ow.writeValueAsString(param)
    }
}
