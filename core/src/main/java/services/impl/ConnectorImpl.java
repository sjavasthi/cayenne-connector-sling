package services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import cayenne.demo.sling.*;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.configuration.server.ServerRuntimeBuilder;
import org.apache.cayenne.query.ObjectSelect;
import services.Connector;

import java.time.LocalDate;
import java.util.List;

@Component(service = ConnectorImpl.class, immediate = true)
public class ConnectorImpl implements Connector {
    @Activate
    protected void activate() {
        ServerRuntime runtime = ServerRuntimeBuilder.builder().addConfig("cayenne-project.xml").build();
        System.out.println("Context"+runtime.newContext());
        ObjectContext context = runtime.newContext();
    }

    @Override
    public List<? extends Object> getData(ObjectContext context){
        List<Painting> paintings1 = ObjectSelect.query(Painting.class).select(context);

        List<Painting> paintings2 = ObjectSelect.query(Painting.class)
                .where(Painting.NAME.likeIgnoreCase("gi%")).select(context);

        List<Painting> paintings3 = ObjectSelect.query(Painting.class)
                .where(Painting.ARTIST.dot(Artist.DATE_OF_BIRTH)
                        .lt(LocalDate.of(1900,1,1))).select(context);

        paintings3.forEach(p-> System.out.println("paintining "+p.getName()+" artist "+p.getArtist()));
        return paintings1;
    }
}
