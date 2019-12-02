package de.odisys.talend.components.source;

import java.io.Serializable;

import com.jayway.jsonpath.JsonPath;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.odisys.talend.components.service.OdiSysService;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Producer;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

@Documentation("")
public class OdiSysInputSource implements Serializable {
    private final OdiSysInputMapperConfiguration configuration;
    private final OdiSysService service;
    private final RecordBuilderFactory builderFactory;
    private boolean executed = false;

    public OdiSysInputSource(@Option("configuration") final OdiSysInputMapperConfiguration configuration,
                             final OdiSysService service,
                             final RecordBuilderFactory builderFactory) {
        this.configuration = configuration;
        this.service = service;
        this.builderFactory = builderFactory;
    }

    @Producer
    public Record next() {
        if (!executed) {
            String resString = "";
            boolean filter;
            String requestUrl = configuration.getDataset().getDatastore().getUrl() + "/rest/api/2/search?jql=";
            if (configuration.getDataset().getJql() == null) {
                requestUrl += "project=" + configuration.getDataset().getProjectId();
                filter = true;
            } else {
                requestUrl += configuration.getDataset().getJql();
                filter = false;
            }
            try {
                HttpResponse<String> res = Unirest.get(requestUrl)
                        .basicAuth(configuration.getDataset().getDatastore().getUsername(),
                                configuration.getDataset().getDatastore().getPassword())
                        .asString();
                resString = res.getBody();
            } catch (UnirestException e) {
                e.printStackTrace();
            }
            executed = true;

            resString = filterStatus(resString, filter);

            return builderFactory.newRecordBuilder().withString("data", resString).build();
        }
        return null;
    }

    public String filterStatus(String resString, boolean filter) {
        if (!filter || configuration.getDataset().getStatus().equals("All")){
            return JsonPath.read(resString, "$.issues[*]").toString();
        } else {
            return JsonPath.read(resString, "$.issues[?(@.fields.status.statusCategory.name =~ /.*" + configuration.getDataset().getStatus() + "/i)]").toString();
        }
    }
}