package uk.ac.ebi.intact.graphdb.ws.controller.model;

import java.util.Collection;

/**
 * Created by anjali on 24/04/20.
 */
public class NetworkEdgeDetails {

    private Collection<Annotation> annotations;
    private Collection<Parameter> parameters;

    public NetworkEdgeDetails(Collection<Annotation> annotations, Collection<Parameter> parameters) {
        this.annotations = annotations;
        this.parameters = parameters;
    }

    public Collection<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Collection<Annotation> annotations) {
        this.annotations = annotations;
    }

    public Collection<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(Collection<Parameter> parameters) {
        this.parameters = parameters;
    }
}