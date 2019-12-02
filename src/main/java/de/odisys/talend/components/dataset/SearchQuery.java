package de.odisys.talend.components.dataset;

import java.io.SequenceInputStream;
import java.io.Serializable;

import de.odisys.talend.components.datastore.BasicAuth;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.action.Proposable;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.TextArea;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.service.completion.DynamicValues;
import org.talend.sdk.component.api.service.completion.Values;

import static java.util.Arrays.asList;

@DataSet("CustomDataset")
@GridLayout({
    @GridLayout.Row({ "datastore" }),
    @GridLayout.Row({"projectId"}),
    @GridLayout.Row({"status"})
})
@Documentation("")
public class SearchQuery implements Serializable {
    @Option
    @Documentation("")
    private BasicAuth datastore;

    @Option
    @TextArea
    @Documentation("")
    private String projectId;

    @Option
    @Proposable("valuesProvider")
    @Documentation("")
    private String status;


    public SearchQuery(BasicAuth datastore, String projectId, String status) {
        this.datastore = datastore;
        this.projectId = projectId;
        this.status = status;
    }

    public SearchQuery(){}

    public BasicAuth getDatastore() {
        return datastore;
    }

    public SearchQuery setDatastore(BasicAuth datastore) {
        this.datastore = datastore;
        return this;
    }

    public String getProjectId() { return projectId; }

    public void setProjectId(String projectId) { this.projectId = projectId; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}