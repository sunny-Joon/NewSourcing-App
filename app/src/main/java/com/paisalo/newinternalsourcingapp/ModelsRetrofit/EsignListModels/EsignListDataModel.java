package com.paisalo.newinternalsourcingapp.ModelsRetrofit.EsignListModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class EsignListDataModel {

    @SerializedName("guarantors")
    @Expose
    private List<Guarantor> guarantors;
    @SerializedName("pendingESignFI")
    @Expose
    private List<PendingESignFI> pendingESignFI;

    public List<Guarantor> getGuarantors() {
        return guarantors;
    }

    public void setGuarantors(List<Guarantor> guarantors) {
        this.guarantors = guarantors;
    }

    public List<PendingESignFI> getPendingESignFI() {
        return pendingESignFI;
    }

    public void setPendingESignFI(List<PendingESignFI> pendingESignFI) {
        this.pendingESignFI = pendingESignFI;
    }
}
