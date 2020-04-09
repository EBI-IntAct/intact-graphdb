package uk.ac.ebi.intact.graphdb.utils;

import uk.ac.ebi.intact.graphdb.model.relationships.RelationshipTypes;

/**
 * Created by anjali on 01/03/18.
 */
public class CypherQueries {

    /*Common BinaryInteractionEvidences of any two interactors*/

    public static final String COMM_NEIGH_OF_INTOR =
            "MATCH (interactorA:GraphInteractor)<-[:" + RelationshipTypes.INTERACTORS + "]-(binaryIE:GraphBinaryInteractionEvidence)-[:" + RelationshipTypes.INTERACTORS + "]->(interactorB:GraphInteractor)" +
                    " WITH  COLLECT(binaryIE) as interactions,interactorA,interactorB" +
                    " UNWIND interactions as interaction" +
                    " MATCH (interaction:GraphBinaryInteractionEvidence) -[experiment:" + RelationshipTypes.EXPERIMENT + "] ->(graphExperiment:GraphExperiment)-[interactionDetectionMethod:" + RelationshipTypes.INTERACTION_DETECTION_METHOD + "]->(dm_cvterm:GraphCvTerm)," +
                    " (interaction:GraphBinaryInteractionEvidence) - [interactionType:" + RelationshipTypes.INTERACTION_TYPE + "] -> (it_cvterm:GraphCvTerm)," +
                    " (graphExperiment:GraphExperiment)<-[publication:" + RelationshipTypes.PUB_EXP + "]-(graphpublication:GraphPublication)," +
                    " (graphpublication)-[pubmedXrefR:" + RelationshipTypes.PMID + "]-(pubmedXrefN:GraphXref)" +
                    " WHERE  (ID(interactorA)<ID(interactorB)) OR (ID(interactorA) = ID(interactorB))" +
                    " RETURN interactorA,interactorB,COLLECT(interaction) as interactions,COLLECT(experiment) as experiments,COLLECT(graphExperiment) as graphExperiments," +
                    " COLLECT(interactionDetectionMethod) as interactionDetectionMethods,COLLECT(dm_cvterm) as dm_cvterms,COLLECT(interactionType) as interactionTypes,COLLECT(it_cvterm) as it_cvterms," +
                    " COLLECT(publication) as dm_publications,COLLECT(graphpublication) as publications,COLLECT(pubmedXrefR) as pubmedXrefRs,COLLECT(pubmedXrefN) as pubmedXrefNs ORDER BY interactorA.ac";


    public static final String COMM_NEIGH_OF_INTOR_SELF_INTERACTION_CASE =
            "MATCH (interactorA:GraphInteractor)<-[:" + RelationshipTypes.INTERACTOR_A + "]-(binaryIE:GraphBinaryInteractionEvidence) -[:" + RelationshipTypes.INTERACTOR_B + "]->(interactorB:GraphInteractor)" +
                    " WITH  COLLECT(binaryIE) as interactions,interactorA,interactorB" +
                    " UNWIND interactions as interaction" +
                    " MATCH (interaction:GraphBinaryInteractionEvidence) -[experiment:" + RelationshipTypes.EXPERIMENT + "] ->(graphExperiment:GraphExperiment)-[interactionDetectionMethod:" + RelationshipTypes.INTERACTION_DETECTION_METHOD + "]->(dm_cvterm:GraphCvTerm)," +
                    " (interaction:GraphBinaryInteractionEvidence) - [interactionType:" + RelationshipTypes.INTERACTION_TYPE + "] -> (it_cvterm:GraphCvTerm)," +
                    " (graphExperiment:GraphExperiment)<-[publication:" + RelationshipTypes.PUB_EXP + "]-(graphpublication:GraphPublication)," +
                    " (graphpublication)-[pubmedXrefR:" + RelationshipTypes.PMID + "]-(pubmedXrefN:GraphXref)" +
                    " WHERE  (ID(interactorA)=ID(interactorB))" +
                    " RETURN interactorA,interactorB,COLLECT(interaction) as interactions,COLLECT(experiment) as experiments,COLLECT(graphExperiment) as graphExperiments," +
                    " COLLECT(interactionDetectionMethod) as interactionDetectionMethods,COLLECT(dm_cvterm) as dm_cvterms,COLLECT(interactionType) as interactionTypes,COLLECT(it_cvterm) as it_cvterms," +
                    " COLLECT(publication) as dm_publications,COLLECT(graphpublication) as publications,COLLECT(pubmedXrefR) as pubmedXrefRs,COLLECT(pubmedXrefN) as pubmedXrefNs ORDER BY interactorA.ac";

    public static final String COMM_NEIGH_OF_INTOR_SELF_PUTATIVE_CASE =
            "MATCH (interactorA:GraphInteractor)-[:" + RelationshipTypes.INTERACTOR_A + "]-(binaryIE:GraphBinaryInteractionEvidence)" +
                    " WHERE  (NOT (binaryIE)-[:" + RelationshipTypes.INTERACTOR_B + "]-(:GraphInteractor))" +
                    " WITH  COLLECT(binaryIE) as interactions,interactorA" +
                    " UNWIND interactions as interaction" +
                    " MATCH (interaction:GraphBinaryInteractionEvidence) -[experiment:" + RelationshipTypes.EXPERIMENT + "] ->(graphExperiment:GraphExperiment)-[interactionDetectionMethod:" + RelationshipTypes.INTERACTION_DETECTION_METHOD + "]->(dm_cvterm:GraphCvTerm)," +
                    " (interaction:GraphBinaryInteractionEvidence) - [interactionType:" + RelationshipTypes.INTERACTION_TYPE + "] -> (it_cvterm:GraphCvTerm)," +
                    " (graphExperiment:GraphExperiment)<-[publication:" + RelationshipTypes.PUB_EXP + "]-(graphpublication:GraphPublication)," +
                    " (graphpublication)-[pubmedXrefR:" + RelationshipTypes.PMID + "]-(pubmedXrefN:GraphXref)" +
                    " RETURN interactorA,COLLECT(interaction) as interactions,COLLECT(experiment) as experiments,COLLECT(graphExperiment) as graphExperiments," +
                    " COLLECT(interactionDetectionMethod) as interactionDetectionMethods,COLLECT(dm_cvterm) as dm_cvterms,COLLECT(interactionType) as interactionTypes,COLLECT(it_cvterm) as it_cvterms," +
                    " COLLECT(publication) as dm_publications,COLLECT(graphpublication) as publications,COLLECT(pubmedXrefR) as pubmedXrefRs,COLLECT(pubmedXrefN) as pubmedXrefNs ORDER BY interactorA.ac";
    /*
    * Equivalent Query String : MATCH (interactorA:GraphInteractor)<-[:interactorA]-(binaryIE:GraphInteractionEvidence)-[:interactorB]->(interactorB:GraphInteractor)
                                WITH  COLLECT(binaryIE) as interactions,interactorA,interactorB
                                WHERE  (ID(interactorA)<ID(interactorB)) OR (ID(interactorA) = ID(interactorB)) RETURN COUNT(*)
    * */
    public static final String INTERACTOR_PAIR_COUNT =
            "MATCH (interactorA:GraphInteractor)<-[:" + RelationshipTypes.INTERACTORS + "]-(binaryIE:GraphBinaryInteractionEvidence) -[:" + RelationshipTypes.INTERACTORS + "]->(interactorB:GraphInteractor) " +
                    " WITH  COLLECT(binaryIE) as interactions,interactorA,interactorB" +
                    " WHERE  (ID(interactorA)<ID(interactorB)) OR (ID(interactorA) = ID(interactorB)) " +
                    " WITH COUNT(*) as binaryCount   MATCH (interactorA:GraphInteractor)<-[:" + RelationshipTypes.INTERACTOR_A + "]-(binaryIE:GraphBinaryInteractionEvidence)" +
                    " OPTIONAL MATCH (binaryIE)-[:" + RelationshipTypes.INTERACTOR_B + "]->(interactorB:GraphInteractor) " +
                    " WITH  COLLECT(binaryIE) as interactions,interactorA,interactorB,binaryCount" +
                    " WHERE  (ID(interactorA) = ID(interactorB)) OR interactorB is null  " +
                    " RETURN COUNT(*)+binaryCount as totalCount";

    public static final String GET_CLUSTERED_INTERACTION =
            "MATCH (:GraphInteractor { ac:{0}})<--(n:GraphClusteredInteraction)-->(:GraphInteractor{ ac:{1}}) return (n)";

    public static final String GET_FEATURES_BY_INTERACTION_AC_COUNT =
            "MATCH (binaryIEN:GraphInteractionEvidence{ ac: {0} }) --(graphParticipantEvidenceN:GraphParticipantEvidence)--(graphFeaturesN:GraphFeatureEvidence)" +
                    "RETURN COUNT(DISTINCT graphFeaturesN)";


    public static final String GET_FEATURES_BY_INTERACTION_AC =
            "MATCH (binaryIEN:GraphInteractionEvidence{ ac: {0} })--(graphParticipantEvidenceN:GraphParticipantEvidence)-[graphFeaturesR:" + RelationshipTypes.PARTICIPANT_FEATURE + "]-(graphFeaturesN:GraphFeatureEvidence)" +
                    " RETURN graphFeaturesN";

    public static final String GET_HOST_ORGANISM_BY_EXPERIMENT_AC =
            "MATCH (e:GraphExperiment{ ac: {0} })-[r:hostOrganism]->(n:GraphOrganism) RETURN n;";

    public static final String GET_INTERACTION_DETMETHOD_BY_EXPERIMENT_AC =
            "MATCH (e:GraphExperiment{ac:{0} })-[r:interactionDetectionMethod]->(n:GraphCvTerm) RETURN n;";

    public static final String CYTOSCAPE_APP_QUERY_FOR_NODES =
            " MATCH (interactor:GraphInteractor)-[identifiersFR:" + RelationshipTypes.IDENTIFIERS + "]->(identifiersFN:GraphXref) WHERE ((identifiersFN.identifier IN {identifiers}) OR {identifiers} is null)" +
                    " MATCH (interactor)-[organismR:" + RelationshipTypes.ORGANISM + "]->(organismN:GraphOrganism) WHERE ((organismN.taxId IN {species}) OR {species} is null)" +
                    " MATCH (interactor)<-[interactorsFR:" + RelationshipTypes.INTERACTORS + "]-(interaction:GraphBinaryInteractionEvidence)" +
                    " WITH COLLECT(distinct interaction) as interactionCollection" +
                    " UNWIND interactionCollection as interactionN " +

                    " MATCH (interactionN)-[interactorsR:" + RelationshipTypes.INTERACTORS + "]->(interactorsN:GraphInteractor)" +
                    " WITH COLLECT(distinct interactorsN) as interactorsCollection" +
                    " UNWIND interactorsCollection as interactorN " +
                    " MATCH (interactorN)-[organismR:" + RelationshipTypes.ORGANISM + "]->(organismN:GraphOrganism) WHERE ((organismN.taxId IN {species}) OR {species} is null)" +
                    " MATCH (interactorN)-[identifierR:" + RelationshipTypes.PREFERRED_IDENTIFIER + "]->(identifierN:GraphXref) " +
                    " WITH interactorN,identifierN,organismN" +
                    " OPTIONAL MATCH (interactorN)-[interactorTypeR:" + RelationshipTypes.INTERACTOR_TYPE + "]->(interactorTypeN:GraphCvTerm)" +
                    " WITH interactorN,identifierN,organismN,interactorTypeN" +
                    " OPTIONAL MATCH (interactorN)-[interactorXrefsR:" + RelationshipTypes.IDENTIFIERS + "]->(interactorXrefsN:GraphXref)" +
                    " WITH" +
                    "       interactorN.ac as ac ," +
                    "       identifierN.identifier as identifier," +
                    "       organismN.scientificName as scientificName," +
                    "       organismN.taxId as taxId ," +
                    "       (interactorN.preferredName +'('+identifierN.identifier+')') as label," +
                    "       interactorTypeN.shortName as shortName," +
                    "       interactorTypeN.mIIdentifier as mIIdentifier," +
                    "       interactorTypeN.mODIdentifier as mODIdentifier," +
                    "       interactorTypeN.pARIdentifier as pARIdentifier," +
                    "       interactorN.preferredName as preferredName," +
                    "       COLLECT(interactorXrefsN) as interactorXrefsNCollection" +
                    " UNWIND interactorXrefsNCollection as interactorXref" +
                    " MATCH (interactorXref)-[interactorXrefDatabaseR:database]->(interactorXrefDatabaseN:GraphCvTerm)" +
                    " WITH" +
                    "       ac," +
                    "       identifier," +
                    "       scientificName," +
                    "       taxId," +
                    "       label," +
                    "       shortName," +
                    "       mIIdentifier," +
                    "       mODIdentifier," +
                    "       pARIdentifier," +
                    "       preferredName," +
                    " COLLECT({" + CyAppJsonNodeParamNames.XREF_DB_NAME + ":interactorXrefDatabaseN.shortName," + CyAppJsonNodeParamNames.XREF_MI + ":interactorXrefDatabaseN.mIIdentifier," + CyAppJsonNodeParamNames.XREF_ID + ":interactorXref.identifier}) as xrefs" +

                    " RETURN " +
                    "       DISTINCT" +
                    "       ac as " + CyAppJsonNodeParamNames.ID + "," +
                    "       identifier as " + CyAppJsonNodeParamNames.PREFERRED_ID + "," +
                    "       scientificName as " + CyAppJsonNodeParamNames.SPECIES + "," +
                    "       taxId as " + CyAppJsonNodeParamNames.TAXID + "," +
                    "       label as " + CyAppJsonNodeParamNames.LABEL + "," +
                    "       shortName as " + CyAppJsonNodeParamNames.TYPE + "," +
                    "       mIIdentifier as " + CyAppJsonNodeParamNames.TYPE_MI_IDENTIFIER + "," +
                    "       mODIdentifier as " + CyAppJsonNodeParamNames.TYPE_MOD_IDENTIFIER + "," +
                    "       pARIdentifier as " + CyAppJsonNodeParamNames.TYPE_PAR_IDENTIFIER + "," +
                    "       preferredName as " + CyAppJsonNodeParamNames.INTERACTOR_NAME + "," +
                    "       xrefs as " + CyAppJsonNodeParamNames.XREFS;

    public static final String CYTOSCAPE_APP_QUERY_FOR_EDGES =
            " MATCH (interactor:GraphInteractor)-[identifiersFR:" + RelationshipTypes.IDENTIFIERS + "]->(identifiersFN:GraphXref) WHERE ((identifiersFN.identifier IN {identifiers}) OR {identifiers} is null)" +
                    " WITH interactor, COLLECT(identifiersFN) as identifiersFNCollection " +
                    " MATCH (interactor)-[organismR:" + RelationshipTypes.ORGANISM + "]->(organismN:GraphOrganism) WHERE ((organismN.taxId IN {species}) OR {species} is null)" +
                    " MATCH (interactor)<-[interactorsFR:" + RelationshipTypes.INTERACTORS + "]-(interaction:GraphBinaryInteractionEvidence)" +
                    " WITH COLLECT(distinct interaction) as interactionCollection" +
                    " UNWIND interactionCollection as interactionN" +
                    " MATCH (interactionN)-[:" + RelationshipTypes.INTERACTION_TYPE + "]-(interactionTypeN:GraphCvTerm)" +
                    " MATCH (interactionN)-[:" + RelationshipTypes.INTERACTIONS + "]-(clusteredInteractionN:GraphClusteredInteraction)" +
                    " MATCH (interactionN)-[:" + RelationshipTypes.INTERACTION_EVIDENCE + "]-(graphInteractionEvidenceN:GraphInteractionEvidence)" +
                    " MATCH (graphInteractionEvidenceN)-[:" + RelationshipTypes.EXPERIMENT + "]-(experimentN:GraphExperiment)" +

                    " MATCH (experimentN)-[:" + RelationshipTypes.INTERACTION_DETECTION_METHOD + "]-(interactionDetectionMethodN:GraphCvTerm)" +
                    " MATCH (experimentN)-[:" + RelationshipTypes.PUB_EXP + "]-(publicationN:GraphPublication)" +
                    " MATCH (publicationN)-[:" + RelationshipTypes.PMID + "]-(pmIdN:GraphXref)" +
                    " OPTIONAL MATCH (experimentN)-[:" + RelationshipTypes.HOST_ORGANISM + "]-(hostOrganismN:GraphOrganism)" +

                    " OPTIONAL MATCH (interactionN)-[:" + RelationshipTypes.COMPLEX_EXPANSION + "]-(complexExpansionN:GraphCvTerm) " +

                    " OPTIONAL MATCH (interactionN)-[:" + RelationshipTypes.INTERACTOR_A + "]-(interactorAN:GraphInteractor)" +
                    "                -[:" + RelationshipTypes.ORGANISM + "]->(organismAN:GraphOrganism) WHERE ((organismAN.taxId IN {species}) OR {species} is null)" +
                    " OPTIONAL MATCH (interactionN)-[:" + RelationshipTypes.INTERACTOR_B + "]-(interactorBN:GraphInteractor) " +
                    "                -[:" + RelationshipTypes.ORGANISM + "]->(organismBN:GraphOrganism) WHERE ((organismBN.taxId IN {species}) OR {species} is null)" +

                    " OPTIONAL MATCH (interactionN)-[:" + RelationshipTypes.BIE_PARTICIPANT_A + "]-(entityAN:GraphEntity) " +
                    " OPTIONAL MATCH (entityAN)-[:" + RelationshipTypes.BIOLOGICAL_ROLE + "]-(biologicalRoleAN:GraphCvTerm) " +
                    " OPTIONAL MATCH (entityAN)-[:" + RelationshipTypes.PARTICIPANT_FEATURE + "]-(featuresAN:GraphFeature) " +
                    " OPTIONAL MATCH (featuresAN)-[:" + RelationshipTypes.TYPE + "]-(featureTypeAN:GraphCvTerm) " +

                    " WITH interactionN," +
                    "      interactorAN.ac as interactor_A_ac," +
                    "      interactorBN.ac as interactor_B_ac," +
                    "      interactionTypeN.shortName as type_short_name," +
                    "      interactionTypeN.mIIdentifier as type_mi," +
                    "      interactionDetectionMethodN.shortName as detection_method_short_name," +
                    "      interactionDetectionMethodN.mIIdentifier as detection_method_mi," +
                    "      clusteredInteractionN.miscore as mi_score," +
                    "      hostOrganismN.taxId as host_organism_tax_id," +
                    "      hostOrganismN.scientificName as host_organism_scientific_name," +
                    "      pmIdN.identifier as pmid,complexExpansionN.shortName as expansion_type," +
                    "      graphInteractionEvidenceN," +
                    "      biologicalRoleAN.shortName as biological_role_A_short_name," +
                    "      biologicalRoleAN.mIIdentifier as biological_role_A_mi," +
                    " COLLECT({feature_name:featuresAN.shortName,feature_type:featureTypeAN.shortName," +
                    "         feature_type_mi_identifier:featureTypeAN.mIIdentifier}) as source_features" +

                    " OPTIONAL MATCH (interactionN)-[:" + RelationshipTypes.BIE_PARTICIPANT_B + "]-(entityBN:GraphEntity) " +
                    " OPTIONAL MATCH (entityBN)-[:" + RelationshipTypes.BIOLOGICAL_ROLE + "]-(biologicalRoleBN:GraphCvTerm) " +
                    " OPTIONAL MATCH (entityBN)-[:" + RelationshipTypes.PARTICIPANT_FEATURE + "]-(featuresBN:GraphFeature) " +
                    " OPTIONAL MATCH (featuresBN)-[:" + RelationshipTypes.TYPE + "]-(featureTypeBN:GraphCvTerm) " +
                    " WITH ID(interactionN) as id," +
                    "      interactionN.ac as interaction_ac," +
                    "      interactor_A_ac," +
                    "      interactor_B_ac," +
                    "      type_short_name," +
                    "      type_mi," +
                    "      detection_method_short_name," +
                    "      detection_method_mi," +
                    "      mi_score," +
                    "      host_organism_tax_id," +
                    "      host_organism_scientific_name," +
                    "      pmid," +
                    "      expansion_type," +
                    "      graphInteractionEvidenceN," +
                    "      biological_role_A_short_name," +
                    "      biological_role_A_mi," +
                    "      biologicalRoleBN.shortName as biological_role_B_short_name," +
                    "      biologicalRoleBN.mIIdentifier as biological_role_B_mi," +
                    "      source_features," +
                    " COLLECT({feature_name:featuresBN.shortName,feature_type:featureTypeBN.shortName," +
                    "        feature_type_mi_identifier:featureTypeBN.mIIdentifier}) as target_features" +

                    " OPTIONAL MATCH (graphInteractionEvidenceN)-[" + RelationshipTypes.ANNOTATIONS + "]->(annotationsN:GraphAnnotation)" +
                    " OPTIONAL MATCH (annotationsN)-[" + RelationshipTypes.TOPIC + "]->(topicN:GraphCvTerm)" +
                    " WITH id," +
                    "      interaction_ac," +
                    "      interactor_A_ac," +
                    "      interactor_B_ac," +
                    "      type_short_name," +
                    "      type_mi," +
                    "      detection_method_short_name," +
                    "      detection_method_mi," +
                    "      mi_score," +
                    "      host_organism_tax_id," +
                    "      host_organism_scientific_name," +
                    "      pmid," +
                    "      expansion_type," +
                    "      graphInteractionEvidenceN," +
                    "      biological_role_A_short_name," +
                    "      biological_role_A_mi," +
                    "      biological_role_B_short_name," +
                    "      biological_role_B_mi," +
                    "      source_features," +
                    "      target_features," +
                    " COLLECT({annotation_value:annotationsN.value,annotation_topic:topicN.shortName,annotation_topic_mi_identifier:topicN.mIIdentifier}) as annotations" +

                    " OPTIONAL MATCH (graphInteractionEvidenceN)-[" + RelationshipTypes.PARAMETERS + "]-(parametersN:GraphParameter)" +
                    " OPTIONAL MATCH (parametersN)-[" + RelationshipTypes.TYPE + "]-(parameterTypeN:GraphCvTerm)" +
                    " OPTIONAL MATCH (parametersN)-[" + RelationshipTypes.UNIT + "]-(parameterUnitN:GraphCvTerm)" +
                    " OPTIONAL MATCH (parametersN)-[" + RelationshipTypes.VALUE + "]-(parameterValueN:GraphCvTerm)" +
                    " WITH id," +
                    "      interaction_ac," +
                    "      interactor_A_ac," +
                    "      interactor_B_ac," +
                    "      type_short_name," +
                    "      type_mi," +
                    "      detection_method_short_name," +
                    "      detection_method_mi," +
                    "      mi_score," +
                    "      host_organism_tax_id," +
                    "      host_organism_scientific_name," +
                    "      pmid," +
                    "      expansion_type," +
                    "      graphInteractionEvidenceN," +
                    "      biological_role_A_short_name," +
                    "      biological_role_A_mi," +
                    "      biological_role_B_short_name," +
                    "      biological_role_B_mi," +
                    "      source_features," +
                    "      target_features," +
                    "      annotations," +
                    "COLLECT({parameter_type:parameterTypeN.shortName,paramter_type_mi_identifier:parameterTypeN.mIIdentifier," +
                    "        parameter_unit:parameterUnitN.shortName,parameter_unit_mi_identifier:parameterUnitN.mIIdentifier," +
                    "        parameter_value_base:parameterValueN.base,parameter_value_factor:parameterValueN.factor,parameter_value_exponent:parameterValueN.exponent})" +
                    "        as parameters" +


                    "" +

                    " RETURN " +
                    "       id as " + CyAppJsonEdgeParamNames.ID + ", " +
                    "       interaction_ac as " + CyAppJsonEdgeParamNames.AC + ", " +
                    "       interactor_A_ac as " + CyAppJsonEdgeParamNames.SOURCE + "," +
                    "       interactor_B_ac as " + CyAppJsonEdgeParamNames.TARGET + "," +
                    "       type_short_name as " + CyAppJsonEdgeParamNames.INTERACTION_TYPE + "," +
                    "       type_mi as " + CyAppJsonEdgeParamNames.INTERACTION_TYPE_MI_IDENTIFIER + "," +
                    "       detection_method_short_name as " + CyAppJsonEdgeParamNames.INTERACTION_DETECTION_METHOD + "," +
                    "       detection_method_mi as " + CyAppJsonEdgeParamNames.INTERACTION_DETECTION_METHOD_MI_IDENTIFIER + "," +
                    "       mi_score as " + CyAppJsonEdgeParamNames.MI_SCORE + "," +
                    "       host_organism_scientific_name as " + CyAppJsonEdgeParamNames.HOST_ORGANISM + "," +
                    "       host_organism_tax_id as " + CyAppJsonEdgeParamNames.HOST_ORGANISM_TAX_ID + "," +
                    "       pmid as " + CyAppJsonEdgeParamNames.PUBMED_ID + "," +
                    "       expansion_type as " + CyAppJsonEdgeParamNames.EXPANSION_TYPE + "," +
                    "       biological_role_A_short_name as " + CyAppJsonEdgeParamNames.SOURCE_BIOLOGICAL_ROLE + "," +
                    "       biological_role_A_mi as " + CyAppJsonEdgeParamNames.SOURCE_BIOLOGICAL_ROLE_MI_IDENTIFIER + "," +
                    "       biological_role_B_short_name as " + CyAppJsonEdgeParamNames.TARGET_BIOLOGICAL_ROLE + "," +
                    "       biological_role_B_mi as " + CyAppJsonEdgeParamNames.TARGET_BIOLOGICAL_ROLE_MI_IDENTIFIER + "," +
                    "       source_features as " + CyAppJsonEdgeParamNames.SOUCRCE_FEATURES + "," +
                    "       target_features as " + CyAppJsonEdgeParamNames.TARGET_FEATURES + "," +
                    "       annotations as " + CyAppJsonEdgeParamNames.ANNOTATIONS + "," +
                    "       parameters as " + CyAppJsonEdgeParamNames.PARAMS + "" +
                    "";


}
