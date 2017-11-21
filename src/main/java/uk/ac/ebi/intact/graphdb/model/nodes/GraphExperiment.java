package uk.ac.ebi.intact.graphdb.model.nodes;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import psidev.psi.mi.jami.model.*;
import psidev.psi.mi.jami.utils.CvTermUtils;

import java.util.ArrayList;
import java.util.Collection;

@NodeEntity
public class GraphExperiment implements Experiment {

    @GraphId
    protected Long id;

    private Publication publication;
    private Collection<Xref> xrefs;
    private Collection<Annotation> annotations;
    private CvTerm interactionDetectionMethod;
    private Organism hostOrganism;
    private Collection<InteractionEvidence> interactions;

    private Collection<Confidence> confidences;
    private Collection<VariableParameter> variableParameters;

    public GraphExperiment() {
    }

    public GraphExperiment(Publication publication) {
        this.publication = publication;
        this.interactionDetectionMethod = CvTermUtils.createUnspecifiedMethod();
    }

    public GraphExperiment(Publication publication, CvTerm interactionDetectionMethod) {
        this.publication = publication;
        if (interactionDetectionMethod == null) {
            this.interactionDetectionMethod = CvTermUtils.createUnspecifiedMethod();
        } else {
            this.interactionDetectionMethod = interactionDetectionMethod;
        }
    }

    public GraphExperiment(Publication publication, CvTerm interactionDetectionMethod, Organism organism) {
        this(publication, interactionDetectionMethod);
        this.hostOrganism = organism;
    }

    protected void initialiseXrefs() {
        this.xrefs = new ArrayList<Xref>();
    }

    protected void initialiseAnnotations() {
        this.annotations = new ArrayList<Annotation>();
    }

    protected void initialiseInteractions() {
        this.interactions = new ArrayList<InteractionEvidence>();
    }


    protected void initialiseConfidences() {
        this.confidences = new ArrayList<Confidence>();
    }

    protected void initialiseVariableParameters() {
        this.variableParameters = new ArrayList<VariableParameter>();
    }


    public Publication getPublication() {
        return this.publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public void setPublicationAndAddExperiment(Publication publication) {
        if (this.publication != null) {
            this.publication.removeExperiment(this);
        }

        if (publication != null) {
            publication.addExperiment(this);
        }
    }

    public Collection<Xref> getXrefs() {
        if (xrefs == null) {
            initialiseXrefs();
        }
        return this.xrefs;
    }

    public Collection<Annotation> getAnnotations() {
        if (annotations == null) {
            initialiseAnnotations();
        }
        return this.annotations;
    }

    public Collection<Confidence> getConfidences() {
        if (confidences == null) {
            initialiseConfidences();
        }
        return confidences;
    }

    public CvTerm getInteractionDetectionMethod() {
        return this.interactionDetectionMethod;
    }

    public void setInteractionDetectionMethod(CvTerm term) {
        if (term == null) {
            this.interactionDetectionMethod = CvTermUtils.createUnspecifiedMethod();
        } else {
            this.interactionDetectionMethod = term;
        }
    }

    public Organism getHostOrganism() {
        return this.hostOrganism;
    }

    public void setHostOrganism(Organism organism) {
        this.hostOrganism = organism;
    }

    public Collection<InteractionEvidence> getInteractionEvidences() {
        if (interactions == null) {
            initialiseInteractions();
        }
        return this.interactions;
    }

    public boolean addInteractionEvidence(InteractionEvidence evidence) {
        if (evidence == null) {
            return false;
        }

        if (getInteractionEvidences().add(evidence)) {
            evidence.setExperiment(this);
            return true;
        }
        return false;
    }

    public boolean removeInteractionEvidence(InteractionEvidence evidence) {
        if (evidence == null) {
            return false;
        }

        if (getInteractionEvidences().remove(evidence)) {
            evidence.setExperiment(null);
            return true;
        }
        return false;
    }

    public boolean addAllInteractionEvidences(Collection<? extends InteractionEvidence> evidences) {
        if (evidences == null) {
            return false;
        }

        boolean added = false;
        for (InteractionEvidence ev : evidences) {
            if (addInteractionEvidence(ev)) {
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllInteractionEvidences(Collection<? extends InteractionEvidence> evidences) {
        if (evidences == null) {
            return false;
        }

        boolean removed = false;
        for (InteractionEvidence ev : evidences) {
            if (removeInteractionEvidence(ev)) {
                removed = true;
            }
        }
        return removed;
    }

    public Collection<VariableParameter> getVariableParameters() {
        if (variableParameters == null) {
            initialiseVariableParameters();
        }
        return variableParameters;
    }

    public boolean addVariableParameter(VariableParameter variableParameter) {
        if (variableParameter == null) {
            return false;
        }

        if (getVariableParameters().add(variableParameter)) {
            variableParameter.setExperiment(this);
            return true;
        }
        return false;
    }

    public boolean removeVariableParameter(VariableParameter variableParameter) {
        if (variableParameter == null) {
            return false;
        }

        if (getVariableParameters().remove(variableParameter)) {
            variableParameter.setExperiment(null);
            return true;
        }
        return false;
    }

    public boolean addAllVariableParameters(Collection<? extends VariableParameter> variableParameters) {
        if (variableParameters == null) {
            return false;
        }

        boolean added = false;
        for (VariableParameter param : variableParameters) {
            if (addVariableParameter(param)) {
                added = true;
            }
        }
        return added;
    }

    public boolean removeAllVariableParameters(Collection<? extends VariableParameter> variableParameters) {
        if (variableParameters == null) {
            return false;
        }

        boolean removed = false;
        for (VariableParameter param : variableParameters) {
            if (removeVariableParameter(param)) {
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public String toString() {
        return "Experiment: "
                + (getPublication() != null ? getPublication().toString() : "no publication")
                + "( " + getInteractionDetectionMethod().toString()
                + (getHostOrganism() != null ? ", " + getHostOrganism().toString() : "") + " )";
    }
}
