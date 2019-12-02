package de.odisys.talend.components.dataset;

import java.io.Serializable;

import de.odisys.talend.components.datastore.CustomDatastore;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.action.Proposable;
import org.talend.sdk.component.api.configuration.type.DataSet;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.configuration.ui.widget.TextArea;
import org.talend.sdk.component.api.meta.Documentation;

@DataSet("CustomDataset")
@GridLayout({
        @GridLayout.Row({"datastore"}),
        @GridLayout.Row({"projectId"}),
        @GridLayout.Row({"status"})
})
@Documentation("")
public class CustomDataset implements Serializable {
    @Option
    @Documentation("")
    private CustomDatastore datastore;

    @Option
    @TextArea
    @Documentation("")
    private String projectId;

    @Option
    @Proposable("valuesProvider")
    @Documentation("")
    private String status;

    public CustomDataset(CustomDatastore datastore, String projectId, String status) {
        this.datastore = datastore;
        this.projectId = projectId;
        this.status = status;
    }

    public CustomDataset() {
    }

    public CustomDatastore getDatastore() {
        return datastore;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDatastore(CustomDatastore datastore) {
        this.datastore = datastore;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}