package fit.bstu.project_wallet.ui.history;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import fit.bstu.project_wallet.API.APIClient;
import fit.bstu.project_wallet.API.APIIterface;
import fit.bstu.project_wallet.DataBase.DataBaseManager;
import fit.bstu.project_wallet.Json.JSONSerialize;
import fit.bstu.project_wallet.units.DB.DBTransaction;
import fit.bstu.project_wallet.units.Transaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryViewModel extends AndroidViewModel {

    private SharedPreferences mSettings;
    private LiveData<List<Transaction>> aAllTransaction;
    private DataBaseManager dataBaseManager;
    private static final String APP_PREFERENCES = "WALLET_KEY";
    private static final String APP_PREFERENCES_NAME = "KEY";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public HistoryViewModel(Application application) {
        super(application);
        mSettings = getApplication().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        dataBaseManager = new DataBaseManager(getApplication());
        dataBaseManager.connectionOpen();
        update();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void update(){
        aAllTransaction = dataBaseManager.getAllTransac(mSettings.getString(APP_PREFERENCES_NAME, ""));
        JSONSerialize.Serialize(getApplication(),aAllTransaction);
    }
    public LiveData<List<Transaction>> getAllTransactions(){
        return aAllTransaction;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteTransac(Transaction a){
        deleteFromServer(a.getIdTransaction());
        dataBaseManager.deleteTrans(a);
        update();
    }
    public void deleteFromServer(String dbTransaction){
        APIClient.getClient().create(APIIterface.class).deleteTransact(dbTransaction).enqueue(new Callback<DBTransaction>() {
            @Override
            public void onResponse(Call<DBTransaction> call, Response<DBTransaction> response) {
            }
            @Override
            public void onFailure(Call<DBTransaction> call, Throwable t) {
            }
        });
    }
}