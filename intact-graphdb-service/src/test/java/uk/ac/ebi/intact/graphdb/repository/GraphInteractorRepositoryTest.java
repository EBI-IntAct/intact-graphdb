package uk.ac.ebi.intact.graphdb.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.driver.internal.util.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.graphdb.model.nodes.GraphInteractor;
import uk.ac.ebi.intact.graphdb.utils.NetworkNodeParamNames;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

        do {
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

    @Test
    public void testCytoscapeAppNodesQuery() {

        Set<String> identifiers = new HashSet<>();
        identifiers.add("Q9BZD4");
        identifiers.add("O14777");

        Set<Integer> species = new HashSet<>();
        species.add(9606);

        boolean neighboursRequired = true;

        Iterable<Map<String, Object>> nodesIterable1 = graphInteractorRepository.findNetworkNodes(identifiers, null, neighboursRequired);
        Assert.assertEquals(8, Iterables.count(nodesIterable1));//152

        boolean interactorsPresent1 = false;
        Iterator<Map<String, Object>> iterator1 = nodesIterable1.iterator();
        try {
            while (iterator1.hasNext()) {
                Map<String, Object> map = iterator1.next();
                for (Map identifierMap : (Map[]) map.get(NetworkNodeParamNames.IDENTIFIERS)) {
                    if (identifierMap.get(NetworkNodeParamNames.XREF_ID).equals("Q9BZD4") || identifierMap.get(NetworkNodeParamNames.XREF_ID).equals("O14777")) {
                        interactorsPresent1 = true;
                    }
                }
            }
        } catch (Exception e) {
            Assert.assertTrue("A map with the key value was expected", false);
        }

        Assert.assertTrue("Queried Interactors should have been present", interactorsPresent1);

        Iterable<Map<String, Object>> nodesIterable2 = graphInteractorRepository.findNetworkNodes(identifiers, species, neighboursRequired);
        Assert.assertEquals(8, Iterables.count(nodesIterable2));//141

        Map<String, Object> mapToBeTested = null;
        boolean interactorsPresent = false;
        Iterator<Map<String, Object>> iterator2 = nodesIterable2.iterator();
        try {
            while (iterator2.hasNext()) {
                Map<String, Object> map = iterator2.next();
                if (map.get(NetworkNodeParamNames.ID).equals("EBI-949451")) {
                    mapToBeTested = map;
                }

                if (!map.get(NetworkNodeParamNames.SPECIES).equals("Homo sapiens")) {
                    Assert.assertTrue("Only Human species records were expected", false);
                }

                for (Map identifierMap : (Map[]) map.get(NetworkNodeParamNames.IDENTIFIERS)) {
                    if (identifierMap.get(NetworkNodeParamNames.XREF_ID).equals("Q9BZD4") || identifierMap.get(NetworkNodeParamNames.XREF_ID).equals("O14777")) {
                        interactorsPresent = true;
                    }
                }
            }
        } catch (Exception e) {
            Assert.assertTrue("A map with the key value was expected", false);
        }

        if (mapToBeTested == null) {
            Assert.assertTrue("An interactor with id 'EBI-949451' was expected", false);
        }
        Assert.assertTrue("Queried Interactors should have been present", interactorsPresent);
        Assert.assertTrue(mapToBeTested.get(NetworkNodeParamNames.PREFERRED_ID).equals("P07199"));
        Assert.assertTrue(mapToBeTested.get(NetworkNodeParamNames.PREFERRED_ID_DB_NAME).equals("uniprotkb"));
        Assert.assertTrue(mapToBeTested.get(NetworkNodeParamNames.PREFERRED_ID_DB_MI).equals("MI:0486"));
        Assert.assertTrue(mapToBeTested.get(NetworkNodeParamNames.SPECIES).equals("Homo sapiens"));
        Assert.assertTrue(mapToBeTested.get(NetworkNodeParamNames.TAXID).equals(9606));
        Assert.assertTrue(mapToBeTested.get(NetworkNodeParamNames.LABEL).equals("CENPB(P07199)"));
        Assert.assertTrue(mapToBeTested.get(NetworkNodeParamNames.TYPE).equals("protein"));
        Assert.assertTrue(mapToBeTested.get(NetworkNodeParamNames.TYPE_MI_IDENTIFIER).equals("MI:0326"));
        Assert.assertTrue(mapToBeTested.get(NetworkNodeParamNames.TYPE_MOD_IDENTIFIER) == null);
        Assert.assertTrue(mapToBeTested.get(NetworkNodeParamNames.TYPE_PAR_IDENTIFIER) == null);
        Assert.assertTrue(mapToBeTested.get(NetworkNodeParamNames.INTERACTOR_NAME).equals("CENPB"));
        Assert.assertEquals(3, ((Map[]) mapToBeTested.get(NetworkNodeParamNames.IDENTIFIERS)).length);

        Iterable<Map<String, Object>> nodesIterable3 = graphInteractorRepository.findNetworkNodes(null, species, neighboursRequired);
        Assert.assertEquals(473, Iterables.count(nodesIterable3));// 30179

        Iterator<Map<String, Object>> iterator3 = nodesIterable3.iterator();
        try {
            while (iterator3.hasNext()) {
                Map<String, Object> map = iterator3.next();

                if (!map.get(NetworkNodeParamNames.SPECIES).equals("Homo sapiens")) {
                    Assert.assertTrue("Only Human species records were expected", false);
                }
            }
        } catch (Exception e) {
            Assert.assertTrue("A map with the key value was expected", false);
        }

        // Without Neighbours

        /*Set<String> identifiers1 = new HashSet<>();
        identifiers1.add("Q9BZD4");
        identifiers1.add("O14777");

        Set<Integer> species1 = new HashSet<>();
        species.add(9606);

        boolean neighboursRequired1=false;

        Iterable<Map<String, Object>> nodesIterable4 = graphInteractorRepository.findNetworkNodes(identifiers1, null,neighboursRequired1);
        Assert.assertEquals(2, Iterables.count(nodesIterable4));*/
    }
}