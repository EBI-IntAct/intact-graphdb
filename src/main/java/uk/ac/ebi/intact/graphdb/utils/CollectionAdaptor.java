package uk.ac.ebi.intact.graphdb.utils;

import psidev.psi.mi.jami.model.*;
import uk.ac.ebi.intact.graphdb.model.nodes.*;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by anjali on 22/11/17.
 */
public class CollectionAdaptor {

    public static Collection<GraphCvTerm> convertCvTermIntoGraphModel(Collection<CvTerm> cvTerms) {
        return cvTerms.stream().map(p -> new GraphCvTerm(p, false)).collect(Collectors.toList());
    }

    public static Collection<GraphXref> convertXrefIntoGraphModel(Collection<Xref> xrefs) {
        return xrefs.stream().map(GraphXref::new).collect(Collectors.toList());
    }

    public static Collection<GraphChecksum> convertChecksumIntoGraphModel(Collection<Checksum> checksums) {
        return checksums.stream().map(GraphChecksum::new).collect(Collectors.toList());
    }

    public static Collection<GraphAlias> convertAliasIntoGraphModel(Collection<Alias> aliases) {
        return aliases.stream().map(GraphAlias::new).collect(Collectors.toList());
    }

    public static Collection<GraphAnnotation> convertAnnotationIntoGraphModel(Collection<Annotation> annotations) {
        return annotations.stream().map(GraphAnnotation::new).collect(Collectors.toList());
    }

    public static Collection<GraphParameter> convertParameterIntoGraphModel(Collection<Parameter> parameters) {
        return parameters.stream().map(p -> new GraphParameter(p, false)).collect(Collectors.toList());
    }

    public static Collection<GraphCooperativityEvidence> convertCooperativityEvidenceIntoGraphModel(Collection<CooperativityEvidence> cooperativityEvidences) {
        return cooperativityEvidences.stream().map(GraphCooperativityEvidence::new).collect(Collectors.toList());
    }

    public static Collection<GraphModelledInteraction> convertModelledInteractionIntoGraphModel(Collection<ModelledInteraction> modelledInteractions) {
        return modelledInteractions.stream().map(GraphModelledInteraction::new).collect(Collectors.toList());
    }

    public static Collection<GraphInteractionEvidence> convertInteractionEvidenceIntoGraphModel(Collection<InteractionEvidence> interactionEvidences) {
        return interactionEvidences.stream().map(p -> new GraphInteractionEvidence(p, false)).collect(Collectors.toList());
    }

    public static Collection<GraphModelledConfidence> convertModelledConfidenceIntoGraphModel(Collection<ModelledConfidence> confidences) {
        return confidences.stream().map(GraphModelledConfidence::new).collect(Collectors.toList());
    }

    public static Collection<GraphModelledParameter> convertModelledParameterIntoGraphModel(Collection<ModelledParameter> parameters) {
        return parameters.stream().map(GraphModelledParameter::new).collect(Collectors.toList());
    }

    public static Collection<GraphCooperativeEffect> convertCooperativeEffectIntoGraphModel(Collection<CooperativeEffect> cooperativeEffects) {
        return cooperativeEffects.stream().map(GraphCooperativeEffect::new).collect(Collectors.toList());
    }

    public static Collection<GraphConfidence> convertConfidenceIntoGraphModel(Collection<Confidence> confidences) {
        return confidences.stream().map(p -> new GraphConfidence(p, false)).collect(Collectors.toList());
    }

    public static Collection<GraphVariableParameterValueSet> convertVariableParameterValueSetIntoGraphModel(Collection<VariableParameterValueSet> variableParameterValueSets) {
        return variableParameterValueSets.stream().map(GraphVariableParameterValueSet::new).collect(Collectors.toList());
    }

    public static Collection<GraphVariableParameterValue> convertVariableParameterValueIntoGraphModel(Collection<VariableParameterValue> variableParameterValue) {
        return variableParameterValue.stream().map(GraphVariableParameterValue::new).collect(Collectors.toList());
    }

    public static Collection<GraphVariableParameter> convertVariableParameterIntoGraphModel(Collection<VariableParameter> variableParameters) {
        return variableParameters.stream().map(GraphVariableParameter::new).collect(Collectors.toList());
    }

    public static Collection<GraphParticipantEvidence> convertParticipantEvidenceIntoGraphModel(Collection<ParticipantEvidence> participantEvidences) {
        return participantEvidences.stream().map(GraphParticipantEvidence::new).collect(Collectors.toList());
    }

    public static Collection<GraphModelledParticipant> convertModelledParticipantIntoGraphModel(Collection<ModelledParticipant> participantEvidences) {
        return participantEvidences.stream().map(GraphModelledParticipant::new).collect(Collectors.toList());
    }

    public static Collection<GraphCausalRelationship> convertCausalRelationshipIntoGraphModel(Collection<CausalRelationship> causalRelationships) {
        return causalRelationships.stream().map(GraphCausalRelationship::new).collect(Collectors.toList());
    }

    public static Collection<GraphRange> convertRangeIntoGraphModel(Collection<Range> ranges, String featureUniqueKey) {
        return ranges.stream().map(p -> new GraphRange(p, featureUniqueKey)).collect(Collectors.toList());
    }

    public static Collection<GraphModelledRange> convertModelledRangeIntoGraphModel(Collection<Range> ranges, String featureUniqueKey) {
        return ranges.stream().map(p -> new GraphModelledRange(p, featureUniqueKey)).collect(Collectors.toList());
    }

    public static Collection<GraphFeatureEvidence> convertFeatureEvidenceIntoGraphModel(Collection<FeatureEvidence> featureEvidences) {
        return featureEvidences.stream().map(GraphFeatureEvidence::new).collect(Collectors.toList());
    }

    public static Collection<GraphModelledFeature> convertModelledFeatureIntoGraphModel(Collection<ModelledFeature> featureEvidences) {
        return featureEvidences.stream().map(GraphModelledFeature::new).collect(Collectors.toList());
    }

     public static Collection<GraphFeature> convertFeatureIntoGraphModel(Collection<Feature> featureEvidences) {
        return featureEvidences.stream().map(p -> new GraphFeature(p,false)).collect(Collectors.toList());
    }


    public static Collection<GraphExperiment> convertExperimentIntoGraphModel(Collection<Experiment> experiments) {
        return experiments.stream().map(GraphExperiment::new).collect(Collectors.toList());
    }

    public static Collection<GraphAuthor> convertAuthorStringIntoGraphModel(Collection<String> authors) {
        return authors.stream().map(GraphAuthor::new).collect(Collectors.toList());
    }
}

