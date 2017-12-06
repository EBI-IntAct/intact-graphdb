package uk.ac.ebi.intact.graphdb.utils;

import org.apache.commons.pool2.BaseObjectPool;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import uk.ac.ebi.intact.graphdb.utils.objectpool.CommonUtilityFactory;

/**
 * Created by anjali on 29/11/17.
 */
public class Constants {

    public static final String IDENTITY = "identity";
    public static final String INTACT_DB = "intact";
    public static final ObjectPool<CommonUtility> COMMON_UTILITY_OBJECT_POOL =new GenericObjectPool<CommonUtility>(new CommonUtilityFactory(),new GenericObjectPoolConfig());

}
