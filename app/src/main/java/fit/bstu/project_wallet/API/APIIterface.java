package fit.bstu.project_wallet.API;

import java.util.List;

import fit.bstu.project_wallet.units.DB.DBTransaction;
import fit.bstu.project_wallet.units.Transaction;
import fit.bstu.project_wallet.units.Wallet;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIIterface {
    @GET("/transaction")
    Call<List<DBTransaction>> getAllTransactions(@Query("walletKey") String walletKey);

    @POST("/transaction")
    Call<DBTransaction> createTransact(@Body DBTransaction transaction);

    @DELETE("/transaction")
    Call<DBTransaction> deleteTransact(@Query("trasactID") String transactionID);

    @POST("/wallet")
    Call<Wallet> createWallet(@Body Wallet wallet);
}
