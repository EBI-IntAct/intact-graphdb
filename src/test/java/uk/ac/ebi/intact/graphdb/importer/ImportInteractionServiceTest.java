package uk.ac.ebi.intact.graphdb.importer;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.ebi.intact.graphdb.services.ImportInteractionService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ImportInteractionServiceTest {

    private static final String HUMAN = "9606";
    private static final String PROTEIN_A = "A";
    private static final String PROTEIN_B = "B";

    @Autowired
    private ImportInteractionService importInteractionService;
//

//
//    @Before
//    public void setUp() throws Exception {
////        interactionRepository.deleteAll();
//        interactionProvider = mock(InteractionProvider.class);
//        GraphProtein A = new GraphProtein("A");
//        GraphProtein B = new GraphProtein("B");
//        Interaction interaction = mock(Interaction.class);
//        List<Interaction> interactions = (List<Interaction>) mock(List.class);
//
//        when(interaction.getInteractorA()).thenReturn(A);
//        when(interaction.getInteractorB()).thenReturn(B);
//        when(interactions.get(0)).thenReturn(interaction);
//        when(interactionProvider.getInteractome(HUMAN)).thenReturn(interactions);
//    }

    @Test
    public void testImportInteractions() throws Exception {

        //   InteractionProvider interactionProvider = mock(InteractionProvider.class);
//
//        GraphProtein A = new GraphProtein("A");
//        GraphProtein B = new GraphProtein("B");
//        GraphBinaryInteractionEvidence interaction = mock(GraphBinaryInteractionEvidence.class);
//        List<GraphBinaryInteractionEvidence> interactions = (List<GraphBinaryInteractionEvidence>) mock(List.class);
//
//        when(interaction.getInteractorA()).thenReturn(A);
//        when(interaction.getInteractorB()).thenReturn(B);
//        when(interactions.get(0)).thenReturn(interaction);
        // when(interactionProvider.getInteractions()).thenReturn(interactions);

        //   ImportInteractionService importInteractionService = new ImportInteractionService(interactionProvider);
//        importInteractionService.importInteractions();
//        verify(interactionProvider, times(1)).getInteractions();

    }

}