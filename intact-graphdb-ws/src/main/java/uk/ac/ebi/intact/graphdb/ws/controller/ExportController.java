package uk.ac.ebi.intact.graphdb.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import psidev.psi.mi.jami.binary.expansion.InteractionEvidenceSpokeExpansion;
import psidev.psi.mi.jami.bridges.exception.BridgeFailedException;
import psidev.psi.mi.jami.bridges.ols.CachedOlsOntologyTermFetcher;
import psidev.psi.mi.jami.commons.MIWriterOptionFactory;
import psidev.psi.mi.jami.commons.PsiJami;
import psidev.psi.mi.jami.datasource.InteractionWriter;
import psidev.psi.mi.jami.factory.InteractionWriterFactory;
import psidev.psi.mi.jami.json.InteractionViewerJson;
import psidev.psi.mi.jami.json.MIJsonOptionFactory;
import psidev.psi.mi.jami.json.MIJsonType;
import psidev.psi.mi.jami.model.ComplexType;
import psidev.psi.mi.jami.model.InteractionCategory;
import psidev.psi.mi.jami.tab.MitabVersion;
import psidev.psi.mi.jami.xml.PsiXmlVersion;
import uk.ac.ebi.intact.graphdb.model.nodes.GraphBinaryInteractionEvidence;
import uk.ac.ebi.intact.graphdb.model.nodes.GraphInteractionEvidence;
import uk.ac.ebi.intact.graphdb.service.GraphInteractionService;
import uk.ac.ebi.intact.graphdb.ws.controller.model.InteractionExportFormat;
import uk.ac.ebi.intact.search.interactions.model.SearchInteraction;
import uk.ac.ebi.intact.search.interactions.service.InteractionSearchService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ntoro on 02/08/2017.
 */
@RestController
@RequestMapping("/export")
public class ExportController {

    private static final int FIRST_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 100;

    private final GraphInteractionService graphInteractionService;
    private final InteractionSearchService interactionSearchService;

    @Autowired
    public ExportController(GraphInteractionService graphInteractionService, InteractionSearchService interactionSearchService) {
        this.graphInteractionService = graphInteractionService;
        this.interactionSearchService = interactionSearchService;

        PsiJami.initialiseAllInteractionWriters();
        InteractionViewerJson.initialiseAllMIJsonWriters();

    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/interaction/list")
    public ResponseEntity<StreamingResponseBody> exportInteraction(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "batchSearch", required = false) boolean batchSearch,
            @RequestParam(value = "interactorSpeciesFilter", required = false) Set<String> interactorSpeciesFilter,
            @RequestParam(value = "interactorTypesFilter", required = false) Set<String> interactorTypesFilter,
            @RequestParam(value = "interactionDetectionMethodsFilter", required = false) Set<String> interactionDetectionMethodsFilter,
            @RequestParam(value = "interactionTypesFilter", required = false) Set<String> interactionTypesFilter,
            @RequestParam(value = "interactionHostOrganismsFilter", required = false) Set<String> interactionHostOrganismsFilter,
            @RequestParam(value = "negativeFilter", required = false) boolean negativeFilter,
            @RequestParam(value = "mutationFilter", required = false) boolean mutationFilter,
            @RequestParam(value = "minMIScore", defaultValue = "0", required = false) double minMIScore,
            @RequestParam(value = "maxMIScore", defaultValue = "1", required = false) double maxMIScore,
            @RequestParam(value = "interSpecies", required = false) boolean interSpecies,
            @RequestParam(value = "binaryInteractionIds", required = false) Set<Long> binaryInteractionIds,
            @RequestParam(value = "interactorAcs", required = false) Set<String> interactorAcs,
            @RequestParam(value = "format", defaultValue = "miJSON", required = false) InteractionExportFormat format) {

        //TODO Sort the code repetition

        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm'." + format.getExtension() + "'").format(new Date());
        try {
            long results = interactionSearchService.countInteractionResult(
                    query,
                    batchSearch,
                    interactorSpeciesFilter,
                    interactorTypesFilter,
                    interactionDetectionMethodsFilter,
                    interactionTypesFilter,
                    interactionHostOrganismsFilter,
                    negativeFilter,
                    mutationFilter,
                    minMIScore,
                    maxMIScore,
                    interSpecies,
                    binaryInteractionIds,
                    interactorAcs);
            if (results == 0) {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }

        StreamingResponseBody responseBody = response -> {

                InteractionWriter writer = createInteractionEvidenceWriterFor(format, response);

                Page<SearchInteraction> interactionIdentifiers;
                Pageable interactionsPage = PageRequest.of(FIRST_PAGE, DEFAULT_PAGE_SIZE);

                try {
                    writer.start();

                    // TODO check when we have the binary identifiers if we need to check from duplicated interactions. Probably not
                    do {
                        interactionIdentifiers = interactionSearchService.findInteractionIdentifiers(
                                query,
                                batchSearch,
                                interactorSpeciesFilter,
                                interactorTypesFilter,
                                interactionDetectionMethodsFilter,
                                interactionTypesFilter,
                                interactionHostOrganismsFilter,
                                negativeFilter,
                                mutationFilter,
                                minMIScore,
                                maxMIScore,
                                interSpecies,
                                binaryInteractionIds,
                                interactorAcs,
                                interactionsPage);

                        // do processing
                        Set<Long> acs = new HashSet<>();
                        for (SearchInteraction interactionIdentifier : interactionIdentifiers) {
                            acs.add(interactionIdentifier.getBinaryInteractionId());
                        }

                        Slice<GraphBinaryInteractionEvidence> graphInteractionEvidences;
                        Pageable identifierPage = PageRequest.of(FIRST_PAGE, DEFAULT_PAGE_SIZE);


                        do {
                            graphInteractionEvidences = graphInteractionService.findByBinaryInteractionIds(acs, identifierPage);

                            for (GraphInteractionEvidence graphInteractionEvidence : graphInteractionEvidences) {
                                writer.write(graphInteractionEvidence);
                            }

                            //advance to next page
                            identifierPage = identifierPage.next();

                        } while (graphInteractionEvidences.hasNext());

                        //advance to next page
                        interactionsPage = interactionsPage.next();

                    } while (interactionIdentifiers.hasNext());

                    writer.end();

                } finally {
                    if (writer != null) {
                        writer.close();
                    }
                }
            };

        return ResponseEntity.ok()
                .contentType(format.getContentType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .header("X-Clacks-Overhead", "GNU Terry Pratchett") //In memory of Sir Terry Pratchett)
                .body(responseBody);

//        //For debugging
//        return ResponseEntity.ok()
//                .contentType(formatEnum.getContentType())
//                .header("X-Clacks-Overhead", "GNU Terry Pratchett") //In memory of Sir Terry Pratchett)
//                .body(responseBody);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/interaction/{ac}")
    public ResponseEntity<StreamingResponseBody> exportInteraction(@PathVariable String ac,
                                                                   @RequestParam(value = "format",
                                                                           defaultValue = "miJSON",
                                                                           required = false) InteractionExportFormat format) {

        //TODO Find a way to avoid the sorting of the interaction. It is needed for comparison in the tests
        try {
            GraphInteractionEvidence interactionEvidence = graphInteractionService.findByInteractionAcForMiJson(ac);

            // For helping to extract graph ids for test
            // System.out.println(interactionEvidence.getGraphId());
            // interactionEvidence.getBinaryInteractionEvidences().stream().map(GraphDatabaseObject::getGraphId).forEach(System.out::println);

            if (interactionEvidence == null) {
                return ResponseEntity.notFound().build();
            }

            StreamingResponseBody responseBody = response -> {
                InteractionWriter writer = createInteractionEvidenceWriterFor(format, response);
                try {
                    writer.start();
                    writer.write(interactionEvidence);
                    writer.end();
                } finally {
                    if (writer != null) {
                        writer.close();
                    }
                }
            };
            return ResponseEntity.ok()
                    .contentType(format.getContentType())
                    .header("X-Clacks-Overhead", "GNU Terry Pratchett") //In memory of Sir Terry Pratchett)
                    .body(responseBody);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    private InteractionWriter createInteractionEvidenceWriterFor(InteractionExportFormat format, Object output) {

        InteractionWriterFactory writerFactory = InteractionWriterFactory.getInstance();
        MIWriterOptionFactory optionFactory = MIWriterOptionFactory.getInstance();
        MIJsonOptionFactory miJsonOptionFactory = MIJsonOptionFactory.getInstance();

        InteractionWriter writer = null;

        switch (format) {
            /* For the XML formats we are going to write in expanded format (not compact) to ease the streaming */
            case miXML25:
                writer = writerFactory.getInteractionWriterWith(optionFactory.getDefaultExpandedXmlOptions(output, InteractionCategory.evidence,
                        ComplexType.n_ary, PsiXmlVersion.v2_5_4));
                break;
            case miXML30:
                writer = writerFactory.getInteractionWriterWith(optionFactory.getDefaultExpandedXmlOptions(output, InteractionCategory.evidence,
                        ComplexType.n_ary, PsiXmlVersion.v3_0_0));
                break;
            case miTab25:
                writer = writerFactory.getInteractionWriterWith(optionFactory.getMitabOptions(output, InteractionCategory.evidence,
                        ComplexType.n_ary, new InteractionEvidenceSpokeExpansion(), true, MitabVersion.v2_5, false));
                break;
            case miTab26:
                writer = writerFactory.getInteractionWriterWith(optionFactory.getMitabOptions(output, InteractionCategory.evidence,
                        ComplexType.n_ary, new InteractionEvidenceSpokeExpansion(), true, MitabVersion.v2_6, false));
                break;
            case miTab27:
                writer = writerFactory.getInteractionWriterWith(optionFactory.getMitabOptions(output, InteractionCategory.evidence, ComplexType.n_ary,
                        new InteractionEvidenceSpokeExpansion(), true, MitabVersion.v2_7, false));
                break;
            case miJSON:
            default:
                try {
                    writer = writerFactory.getInteractionWriterWith(miJsonOptionFactory.getJsonOptions(output, InteractionCategory.evidence, null,
                            MIJsonType.n_ary_only, new CachedOlsOntologyTermFetcher(), null));
                } catch (BridgeFailedException e) {
                    writer = writerFactory.getInteractionWriterWith(miJsonOptionFactory.getJsonOptions(output, InteractionCategory.evidence, null,
                            MIJsonType.n_ary_only, null, null));
                }
                break;
        }
        return writer;
    }
}