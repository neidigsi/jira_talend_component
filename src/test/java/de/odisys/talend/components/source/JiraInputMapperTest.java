package de.odisys.talend.components.source;

import java.io.IOException;
import java.util.List;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import de.odisys.talend.components.dataset.SearchQuery;
import de.odisys.talend.components.datastore.BasicAuth;
import org.junit.*;
import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.junit.SimpleComponentRule;
import org.talend.sdk.component.junit.SimpleFactory;
import org.talend.sdk.component.runtime.input.Mapper;
import org.talend.sdk.component.runtime.manager.chain.Job;

import static org.junit.Assert.*;

public class JiraInputMapperTest {

    @ClassRule
    public static final SimpleComponentRule COMPONENT_FACTORY = new SimpleComponentRule("de.odisys.talend.components");

    @Before
    public void init() {
        org.apache.log4j.BasicConfigurator.configure();
    }

    @Test
    public void testFilterFunction() {
        BasicAuth basicAuth = new BasicAuth("url", "username", "password");

        // Test with In Progress
        final SearchQuery searchQuery = new SearchQuery(basicAuth, "projectId", "In Progress");
        final String uriConfig = SimpleFactory.configurationByExample()
                .forInstance(searchQuery)
                .configured().toQueryString();


        final Mapper mapper = COMPONENT_FACTORY.createMapper(JiraInputMapper.class, searchQuery);

        Job.components()
                .component("JiraInput", "OdiSys://JiraInput?" + uriConfig)
                .component("collector", "test://collector")
                .connections()
                .from("JiraInput").to("collector")
                .build()
                .run();


        final List<Record> result = COMPONENT_FACTORY.collectAsList(Record.class, mapper);
        ReadContext json = JsonPath.parse(result.get(0).getString("data"));
        List<String> issues = json.read("$");

        assertEquals(2, issues.size());
    }

    @Test
    public void produce() throws IOException {
        BasicAuth basicAuth = new BasicAuth("url", "username", "password");
        final SearchQuery searchQuery = new SearchQuery(basicAuth, "projectId", "In Progress");
        final String uriConfig = SimpleFactory.configurationByExample()
                .forInstance(searchQuery)
                .configured().toQueryString();


        final Mapper mapper = COMPONENT_FACTORY.createMapper(JiraInputMapper.class, searchQuery);

        Job.components()
                .component("JiraInput", "OdiSys://JiraInput?" + uriConfig)
                .component("collector", "test://collector")
                .connections()
                .from("JiraInput").to("collector")
                .build()
                .run();


        final List<Record> result = COMPONENT_FACTORY.collectAsList(Record.class, mapper);
        String stringRes = result.get(0).getString("data");
        System.out.println(stringRes);
    }



}