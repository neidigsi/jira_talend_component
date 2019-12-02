package de.odisys.talend.components.service;

import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.completion.DynamicValues;
import org.talend.sdk.component.api.service.completion.Values;

import static java.util.Arrays.asList;

@Service
public class OdiSysService {

    @DynamicValues("valuesProvider")
    public Values vendors() {
        return new Values(asList(new Values.Item("1", "All"),
                new Values.Item("2", "Open"),
                new Values.Item("3", "In Progress"),
                new Values.Item("4", "Done"),
                new Values.Item("5", "To Do"),
                new Values.Item("6", "In Review"),
                new Values.Item("7", "Under Review"),
                new Values.Item("8", "Approved"),
                new Values.Item("9", "Cancelled"),
                new Values.Item("10", "Rejected"),
                new Values.Item("11", "Draft"),
                new Values.Item("12", "Published")));
    }
}