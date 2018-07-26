package uk.ac.ebi.intact.graphdb.repositories;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.graphdb.model.nodes.GraphProtein;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class InteractionRepositoryTest {

    public static final String P12345 = "P12345";
    public static final String P12346 = "P12346";
    public static final String P12347 = "P12347";

//    @Autowired
//    private Session session;

    @Autowired
    private ProteinRepository proteinRepository;
    @Autowired
    private GraphInteractorRepository graphInteractorRepository;
    @Autowired
    private GraphInteractionEvidenceRepository interactionRepository;

    @Before
    public void setUp() throws Exception {

        proteinRepository.deleteAll();
        graphInteractorRepository.deleteAll();

//
        GraphProtein p12345 = new GraphProtein(P12345);
        GraphProtein p12346 = new GraphProtein(P12346);
        GraphProtein p12347 = new GraphProtein(P12347);

//        Interactor p12345 = new Interactor(P12345);
//        Interactor p12346 = new Interactor(P12346);
//        Interactor p12347 = new Interactor(P12347);

        // automatically persisted
        proteinRepository.save(p12345);
        proteinRepository.save(p12346);
        proteinRepository.save(p12347);

        // we can create all the relationships and persist or create the relationships in one direction and
        // retrieve the previous persisted entity to avoid overwrite it

//        p12345.interactsWith(p12346);
//        p12345.interactsWith(p12347);
//
//        p12346.interactsWith(p12347);
//        p12346.interactsWith(p12345);
//
//        p12347.interactsWith(p12346);
//        p12347.interactsWith(p12345);
//
//        interactorRepository.save(p12345);
//        interactorRepository.save(p12346);
//        interactorRepository.save(p12347);

        //OR

//        interactorRepository.save(p12345);
//        interactorRepository.save(p12346);
//        interactorRepository.save(p12347);

        p12345 = (GraphProtein) proteinRepository.findByShortName(p12345.getShortName());
//        p12345 = interactorRepository.findByAccession(p12345.getAccession());
//        p12345.interactsWith(p12346, 0.0);
//        p12345.interactsWith(p12347, 0.0);
        proteinRepository.save(p12345);

        p12346 = (GraphProtein) proteinRepository.findByShortName(p12346.getShortName());
//        p12346 = interactorRepository.findByAccession(p12346.getAccession());
//        p12346.interactsWith(p12347, 0.0);

        // We already know that p12346 works with p12345
        proteinRepository.save(p12346);

        // We already know p12347 works with p12346 and p12345

    }

    @After
    public void tearDown() throws Exception {
//        session.purgeDatabase();
    }

//    @Test
//    public void testInteractions() throws Exception {
//
//        long count = interactionRepository.count();
//        Assert.assertEquals(3, count);
//        Page<BinaryInteractionEvidence> result = interactionRepository.findAll(new PageRequest(0, 10));
//        Assert.assertEquals(3, result.getContent().size());
//        for (Interaction interaction : interactionRepository.findAll()) {
//            System.out.println(interaction);
//        }
//
//        Page<BinaryInteractionEvidence> interactionsPage = interactionRepository.findAll(new PageRequest(0, 10));
//        Assert.assertEquals(3, interactionsPage.getContent().size());
//        Assert.assertEquals(3, interactionsPage.getTotalElements());
//        for (Interaction interaction : interactionsPage) {
//            System.out.println(interaction);
//        }
//
//        result = interactionRepository.findByInteractorB_ShortName(new PageRequest(0, 10), P12345);
//        Assert.assertEquals(0, result.getNumberOfElements());
//
//        for (Interaction interaction : result) {
//            if (interaction instanceof GraphBinaryInteractionEvidence) {
//                System.out.println(
//                        ((GraphBinaryInteractionEvidence) interaction).getInteractorA().getShortName() +
//                                " interacts with " +
//                                ((GraphBinaryInteractionEvidence) interaction).getInteractorB().getShortName() + ".");
//            }
//        }
//
//        result = interactionRepository.findByInteractorA_ShortName(new PageRequest(0, 10), P12345);
//        Assert.assertEquals(0, result.getNumberOfElements());
//
//        for (Interaction interaction : result) {
//            System.out.println(
//                    ((GraphBinaryInteractionEvidence) interaction).getInteractorA().getShortName() +
//                            " interacts with " +
//                            ((GraphBinaryInteractionEvidence) interaction).getInteractorB().getShortName() + ".");
//        }
//
//    }

}