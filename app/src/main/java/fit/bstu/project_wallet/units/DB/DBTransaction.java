package fit.bstu.project_wallet.units.DB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DBTransaction {
    public DBTransaction(String a, String walletKey, int categoryId, double transactionValue, String dateTransaction){
        transactionId = a;
        this.walletKey = walletKey;
        this.categoryId = categoryId;
        this.transactionValue = transactionValue;
        this.dateTransaction = dateTransaction;
    }

    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("walletKey")
    @Expose
    private String walletKey;
    @SerializedName("categoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("transactionValue")
    @Expose
    private Double transactionValue;
    @SerializedName("dateTransaction")
    @Expose
    private String dateTransaction;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getWalletKey() {
        return walletKey;
    }

    public void setWalletKey(String walletKey) {
        this.walletKey = walletKey;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Double getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(Double transactionValue) {
        this.transactionValue = transactionValue;
    }

    public String getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

}
