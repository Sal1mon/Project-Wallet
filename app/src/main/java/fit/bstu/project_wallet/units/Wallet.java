package fit.bstu.project_wallet.units;

public class Wallet {
    private String walletKey;
    private float cashValue;
    public Wallet(String walletKey, float cashValue){
        this.walletKey = walletKey;
        this.cashValue = cashValue;
    }
    public String getWalletKey() {
        return walletKey;
    }

    public void setWalletKey(String walletKey) {
        this.walletKey = walletKey;
    }

    public float getCashValue() {
        return cashValue;
    }

    public void setCashValue(float cashValue) {
        this.cashValue = cashValue;
    }
}
