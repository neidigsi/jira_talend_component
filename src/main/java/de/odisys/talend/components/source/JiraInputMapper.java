package de.odisys.talend.components.source;

import static java.util.Collections.singletonList;
import static org.talend.sdk.component.api.component.Icon.IconType.CUSTOM;

import java.io.Serializable;
import java.util.List;

import de.odisys.talend.components.dataset.SearchQuery;
import org.talend.sdk.component.api.component.Icon;
import org.talend.sdk.component.api.component.Version;
import org.talend.sdk.component.api.configuration.Option;
import org.talend.sdk.component.api.input.Assessor;
import org.talend.sdk.component.api.input.Emitter;
import org.talend.sdk.component.api.input.PartitionMapper;
import org.talend.sdk.component.api.input.PartitionSize;
import org.talend.sdk.component.api.input.Split;
import org.talend.sdk.component.api.meta.Documentation;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

import de.odisys.talend.components.service.OdiSysService;

//
// this class role is to enable the work to be distributed in environments supporting it.
//
@Version(1) // default version is 1, if some configuration changes happen between 2 versions you can add a migrationHandler
@Icon(value=CUSTOM, custom="OdiSys") // you can use a custom one using @Icon(value=CUSTOM, custom="filename") and adding icons/filename.svg in resources
@PartitionMapper(name = "JiraInput")
@Documentation("")
public class JiraInputMapper implements Serializable {
    private final SearchQuery configuration;
    private final OdiSysService service;
    private final RecordBuilderFactory recordBuilderFactory;

    public JiraInputMapper(@Option("configuration") final SearchQuery configuration,
                        final OdiSysService service,
                        final RecordBuilderFactory recordBuilderFactory) {
        this.configuration = configuration;
        this.service = service;
        this.recordBuilderFactory = recordBuilderFactory;
    }

    @Assessor
    public long estimateSize() {
        return 1L;
    }

    @Split
    public List<JiraInputMapper> split(@PartitionSize final long bundles) {
        return singletonList(this);
    }

    @Emitter
    public JiraInputSource createWorker() {
        return new JiraInputSource(configuration, recordBuilderFactory);
    }
}