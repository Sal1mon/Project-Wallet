package fit.bstu.project_wallet.units;

public class Transaction {
    private String  idTransaction;
    private String keyWallet;
    private String nameCategory;
    private String trasactionValue;
    private String dateTransaction;

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    private int idCategory;


    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String  getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getKeyWallet() {
        return keyWallet;
    }

    public void setKeyWallet(String keyWallet) {
        this.keyWallet = keyWallet;
    }

    public String getTrasactionValue() {
        return trasactionValue;
    }

    public void setTrasactionValue(String trasactionValue) {
        this.trasactionValue = trasactionValue;
    }

    public String getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(String dateTransaction) {
        this.dateTransaction = dateTransaction;
    }
}
