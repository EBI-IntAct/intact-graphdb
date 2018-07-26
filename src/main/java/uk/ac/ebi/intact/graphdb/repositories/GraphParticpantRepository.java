package uk.ac.ebi.intact.graphdb.repositories;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.ac.ebi.intact.graphdb.model.nodes.GraphParticipantEvidence;

/**
 * Created by anjali on 23/07/18.
 */
@RepositoryRestResource(collectionResourceRel = "participants", path = "participants")
public interface GraphParticpantRepository extends Neo4jRepository<GraphParticipantEvidence, String> {
}