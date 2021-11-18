package com.br.refera.sysorder.service

import com.br.refera.sysorder.entity.Category
import com.br.refera.sysorder.repository.CategoryRepository
import com.br.refera.sysorder.service.impl.CategoryServiceImpl
import org.apache.commons.lang3.RandomStringUtils
import spock.lang.Specification

import java.util.concurrent.ThreadLocalRandom

class CategoryServiceSpec extends Specification {
    private CategoryRepository categoryRepository
    private CategoryService categoryService

    void setup() {
        categoryRepository = Mock(CategoryRepository)
        categoryService = new CategoryServiceImpl(categoryRepository)
    }

    def "Test Success for getListCategory method"() {
        given:
            categoryRepository.findAll() >> listCategories(4)
        when:
            def result = categoryService.getListCategory()
        then:
            result != null
            result instanceof List
            result.size() == 4
    }

    def "Test Exception for getListCategory method - there are no result to response"() {
        given:
            categoryRepository.findAll() >> Collections.emptyList()
        when:
            categoryService.getListCategory()
        then:
            final ApiException apiException = thrown()
            apiException.getStatusCode() == 404
    }

    def "Test Success for getCategoryById method"() {
        given:
            categoryRepository.findById(_ as Long) >> Optional.of(getCategory())
        when:
            def result = categoryService.getCategoryById(4)
        then:
            result != null
            result instanceof Category
            result.id != null
            result.desc != null
    }

    def "Test Exception for getCategoryById method - register not found"() {
        given:
            categoryRepository.findById(_ as Long) >> Optional.empty()
        when:
            categoryService.getCategoryById(4)
        then:
            final ApiException apiException = thrown()
            apiException.getStatusCode() == 404
    }

    def "Test Success for createCategory method"() {
        given:
            categoryRepository.save(_ as Category) >> getCategory()
        when:
            def result = categoryService.createCategory(Category.builder()
                    .desc(RandomStringUtils.randomAlphabetic(15))
                    .build())
        then:
            result != null
            result instanceof Category
            result.id != null
            result.desc != null
    }

    def "Test Exception for createCategory method - category already exist"() {
        given:
            categoryRepository.findById(_ as Long) >> Optional.of(getCategory())
        when:
            categoryService.createCategory(getCategory())
        then:
            final ApiException apiException = thrown()
            apiException.getStatusCode() == 400
    }

    def "Test Success for updateCategory method"() {
        given:
            categoryRepository.findById(_ as Long) >> Optional.of(getCategory())
            categoryRepository.save(_ as Category) >> getCategory()
        when:
            def result = categoryService.updateCategory(getCategory())
        then:
            result != null
            result instanceof Category
            result.id != null
            result.desc != null
    }

    def "Test Exception for updateCategory method - "() {
        given:
            categoryRepository.findById(_ as Long) >> Optional.empty()
        when:
            categoryService.updateCategory(getCategory())
        then:
            final ApiException apiException = thrown()
            apiException.getStatusCode() == 404
    }

    def "Test Success for deleteCategory method"() {
        given:
            categoryRepository.findById(_ as Long) >> Optional.of(getCategory())
            categoryRepository.delete(_ as Category) >> null
        when:
            categoryService.deleteCategory(getCategory())
        then:
            noExceptionThrown()
    }

    def "Test Exception for deleteCategory method - Category don't exist"() {
        given:
            categoryRepository.findById(_ as Long) >> Optional.empty()
        when:
            categoryService.deleteCategory(getCategory())
        then:
            final ApiException apiException = thrown()
            apiException.getStatusCode() == 404
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
}
