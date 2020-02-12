package com.aws.samples.djlspringboot.model;

import javax.validation.constraints.NotNull;
import java.util.List;

public class InferenceResponse {

    private String outputReference;

    private List<InferredObject> inferredObjects;

    public InferenceResponse(@NotNull  List<InferredObject> inferredObjects,
                             String outputReference) {
        this.inferredObjects = inferredObjects;
        this.outputReference = outputReference;
    }

    public List<InferredObject> getInferredObjects() {
        return inferredObjects;
    }

    public void setInferredObjects(List<InferredObject> inferredObjects) {
        this.inferredObjects = inferredObjects;
    }

    public String getOutputReference() {
        return outputReference;
    }

    public void setOutputReference(String outputReference) {
        this.outputReference = outputReference;
    }
}
