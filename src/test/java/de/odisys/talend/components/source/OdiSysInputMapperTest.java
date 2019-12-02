package de.odisys.talend.components.source;

import java.io.IOException;
import java.util.List;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import de.odisys.talend.components.dataset.CustomDataset;
import de.odisys.talend.components.datastore.CustomDatastore;
import org.junit.*;
import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.junit.SimpleComponentRule;
import org.talend.sdk.component.junit.SimpleFactory;
import org.talend.sdk.component.runtime.input.Mapper;
import org.talend.sdk.component.runtime.manager.chain.Job;

import static org.junit.Assert.*;

public class OdiSysInputMapperTest {

    @ClassRule
    public static final SimpleComponentRule COMPONENT_FACTORY = new SimpleComponentRule("de.odisys.talend.components");

    @Test
    public void produce() throws IOException {
        CustomDatastore datastore = new CustomDatastore("url", "username", "password");

        final CustomDataset dataset = new CustomDataset(datastore, "projectId");

        final OdiSysInputMapperConfiguration configuration = new OdiSysInputMapperConfiguration();
        configuration.setDataset(dataset);

        final String uriConfig = SimpleFactory.configurationByExample()
                .forInstance(configuration)
                .configured().toQueryString();


        final Mapper mapper = COMPONENT_FACTORY.createMapper(OdiSysInputMapper.class, configuration);

        Job.components()
                .component("OdiSysInput", "OdiSysFamily://OdiSysInput?" + uriConfig)
                .component("collector", "test://collector")
                .connections()
                .from("OdiSysInput").to("collector")
                .build()
                .run();


        final List<Record> result = COMPONENT_FACTORY.collectAsList(Record.class, mapper);
        ReadContext json = JsonPath.parse(result.get(0).getString("data"));
        List<String> issues = json.read("issues");

        assertEquals(14, issues.size());
    }
}