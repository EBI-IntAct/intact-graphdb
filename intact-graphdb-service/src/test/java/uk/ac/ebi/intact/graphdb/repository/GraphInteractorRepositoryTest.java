package uk.ac.ebi.intact.graphdb.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.graphdb.model.nodes.GraphInteractor;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional(readOnly = true)
public class GraphInteractorRepositoryTest {

     @Autowired
     private GraphInteractorRepository graphInteractorRepository;

     @Test
     public void testGraphInteractorRepositoryPagination() {
          int pageNumber = 0;
          int totalElements = 0;
          int pageSize = 10;
          int depth = 0;
          Page<GraphInteractor> page;

          do{
               page = graphInteractorRepository.findAll(PageRequest.of(pageNumber, pageSize), depth);
               Assert.assertNotNull("Page is Null", page);
               totalElements = totalElements + page.getNumberOfElements();
               pageNumber++;
          } while (page.hasNext());

          Assert.assertEquals(pageNumber, page.getTotalPages());
          Assert.assertEquals(totalElements, page.getTotalElements());
          Assert.assertEquals(655, totalElements);
          Assert.assertTrue(pageNumber > 1);

     }
}