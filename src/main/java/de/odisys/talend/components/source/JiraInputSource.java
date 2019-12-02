package de.odisys.talend.components.source;

import java.io.Serializable;
import java.io.StringReader;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.jayway.jsonpath.JsonPath;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.odisys.talend.components.dataset.SearchQuery;
import de.odisys.talend.components.datastore.BasicAuth;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Producer;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;


import de.odisys.talend.components.service.OdiSysService;

@Documentation("TODO fill the documentation for this source")
public class JiraInputSource implements Serializable {
    private final SearchQuery configuration;
    private final RecordBuilderFactory builderFactory;
    private boolean executed = false;

    public JiraInputSource(@Option("configuration") final SearchQuery configuration, final RecordBuilderFactory builderFactory) {
        this.configuration = configuration;
        this.builderFactory = builderFactory;
    }

    @PostConstruct
    public void init() {}

    @Producer
    public Record next() {
        if (!executed){
            String resString = "";
            try {
                HttpResponse<String> res = Unirest.get(configuration.getDatastore().getUrl() + "/rest/api/2/search?jql=project=" + configuration.getProjectId())
                        .basicAuth(configuration.getDatastore().getUsername(), configuration.getDatastore().getPassword())
                        .asString();;
                resString = res.getBody();
            } catch (UnirestException e) {
                e.printStackTrace();
            }
            executed = true;

            resString = filterStatus(resString);

            return builderFactory.newRecordBuilder().withString("data", resString).build();
        }
        return null;
    }

    public String filterStatus(String resString) {
        if (configuration.getStatus().equals("All")){
            return resString;
        } else {
            return JsonPath.read(resString, "$.issues[?(@.fields.status.statusCategory.name =~ /.*" + configuration.getStatus() + "/i)]").toString();
        }
    }

    @PreDestroy
    public void release() {}
}