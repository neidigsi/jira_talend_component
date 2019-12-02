package de.odisys.talend.components.source;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
    private String baseUrl;
    private String username;
    private String password;
    private String projectId;


    @ClassRule
    public static final SimpleComponentRule COMPONENT_FACTORY = new SimpleComponentRule("de.odisys.talend.components");

    @Test
    public void produce() throws IOException {
        InputStream inputStream;
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            baseUrl = prop.getProperty("BASEURL");
            username = prop.getProperty("USER");
            password = prop.getProperty("PASSWORD");
            projectId = prop.getProperty("PROJECTID");

            inputStream.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }

        CustomDatastore datastore = new CustomDatastore(baseUrl, username, password);

        final CustomDataset dataset = new CustomDataset(datastore, projectId);

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