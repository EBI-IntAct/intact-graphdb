package uk.ac.ebi.intact.graphdb.repository;


import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.intact.graphdb.model.nodes.GraphInteractor;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: noedelta
 * Date: 26/03/2014
 * Time: 20:32
 */
@Repository
public interface GraphInteractorRepository extends Neo4jRepository<GraphInteractor, String> {

    GraphInteractor findByShortName(@Param("shortName") String shortName);

    Optional<GraphInteractor> findByAc(@Param("ac") String ac, @Depth int depth);

    @Query("MATCH (a:Interactor)<-[i:INTERACTS_IN]-(b:Interactor) RETURN i,a,b LIMIT {limit}")
    Collection<GraphInteractor> graph(@Param("limit") int limit);

}
