
package de.odisys.talend.components.source;

import java.io.Serializable;

import de.odisys.talend.components.dataset.CustomDataset;

import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.configuration.ui.layout.GridLayout;
import org.talend.sdk.component.api.meta.Documentation;

@GridLayout({
        @GridLayout.Row({"dataset"})
})
@Documentation("")
public class OdiSysInputMapperConfiguration implements Serializable {
    @Option
    @Documentation("")
    private CustomDataset dataset;

    public CustomDataset getDataset() {
        return dataset;
    }
    public void setDataset(CustomDataset dataset) { this.dataset = dataset; }
}