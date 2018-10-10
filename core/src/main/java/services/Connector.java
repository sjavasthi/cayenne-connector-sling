package services;

import org.apache.cayenne.ObjectContext;
import java.util.List;


public interface Connector{
    List<? extends Object> getData(ObjectContext context);
}
