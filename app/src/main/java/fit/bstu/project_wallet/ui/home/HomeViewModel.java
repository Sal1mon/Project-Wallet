package fit.bstu.project_wallet.ui.home;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import fit.bstu.project_wallet.API.APIClient;
import fit.bstu.project_wallet.API.APIIterface;
import fit.bstu.project_wallet.DataBase.DataBaseManager;
import fit.bstu.project_wallet.units.Category;
import fit.bstu.project_wallet.units.DB.DBTransaction;
import fit.bstu.project_wallet.units.Wallet;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {
    private static final String APP_PREFERENCES = "WALLET_KEY";
    private static final String APP_PREFERENCES_NAME = "KEY";
    private final SharedPreferences mSettings;
    private final DataBaseManager dataBaseManager;
    private final MutableLiveData<Integer> CASH_VALUE;
    private final MutableLiveData<String> KEY_VALUE;
    private int cash = 0;

    public HomeViewModel(Application application) {
        super(application);
        dataBaseManager = new DataBaseManager(getApplication());
        mSettings = getApplication().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        dataBaseManager.connectionOpen();
        CASH_VALUE = new MutableLiveData<>();
        KEY_VALUE = new MutableLiveData<>();
        cash = dataBaseManager.getCashValue(getSharedPreferenseKey());
        CASH_VALUE.setValue(cash);
        KEY_VALUE.setValue(getSharedPreferenseKey());
    }

    public boolean keyCointais() {
        return mSettings.contains(APP_PREFERENCES_NAME);
    }

    public void removeSharedPrefs() {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.remove(APP_PREFERENCES_NAME);
        CASH_VALUE.setValue(dataBaseManager.getCashValue(getSharedPreferenseKey()));

    }

    public void createNewKey() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        dataBaseManager.createNewWallet(uuid);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_NAME, uuid);
        editor.apply();
        //TODO
        Call<Wallet> wallet = APIClient.getClient().create(APIIterface.class).createWallet(new Wallet(uuid, 0));
        wallet.enqueue(new Callback<Wallet>() {
            @Override
            public void onResponse(Call<Wallet> call, Response<Wallet> response) {
                Toast.makeText(getApplication(), "Server: WalletCreated", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Wallet> call, Throwable t) {
                Toast.makeText(getApplication(), "Check your internet connection", Toast.LENGTH_LONG).show();
            }
        });
        CASH_VALUE.setValue(dataBaseManager.getCashValue(getSharedPreferenseKey()));
        KEY_VALUE.setValue(getSharedPreferenseKey());
    }

    public void syncronizeWallet(String key) {
        APIClient.getClient().create(APIIterface.class).getAllTransactions(key).enqueue(new Callback<List<DBTransaction>>() {
            @Override
            public void onResponse(Call<List<DBTransaction>> call, Response<List<DBTransaction>> response) {

                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_NAME, key);
                editor.apply();
                dataBaseManager.createNewWallet(key);

                for (DBTransaction i : response.body()) {
                    //TODO out this
                    ContentValues values = new ContentValues();
                    values.put("idTransaction", i.getTransactionId());
                    values.put("keyWallet", i.getWalletKey());
                    values.put("idCategory", i.getCategoryId());
                    values.put("trasactionValue", i.getTransactionValue());
                    values.put("dateTransaction", i.getDateTransaction());
                    dataBaseManager.insertTransaction(values);
                }
                CASH_VALUE.setValue(dataBaseManager.getCashValue(key));
                KEY_VALUE.setValue(getSharedPreferenseKey());
            }

            @Override
            public void onFailure(Call<List<DBTransaction>> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                Toast.makeText(getApplication(), "Check your internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    public List<Category> returnCategoryList(int a) {
        return dataBaseManager.getallCategory(a);
    }

    public void trasactionCreate(int categoryID, int value) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        //TODO out this
        ContentValues values = new ContentValues();
        values.put("idTransaction", uuid);
        values.put("keyWallet", getSharedPreferenseKey());
        values.put("idCategory", categoryID);
        values.put("trasactionValue", value);
        values.put("dateTransaction", dateFormat.format(date));
        dataBaseManager.insertTransaction(values);
        InsertIntoServer(new DBTransaction(uuid, getSharedPreferenseKey(), categoryID, value, dateFormat.format(date)));
        CASH_VALUE.postValue(cash += value);
    }

    public LiveData<Integer> getText() {
        return CASH_VALUE;
    }

    public LiveData<String> getKey() {
        return KEY_VALUE;
    }

    public void InsertIntoServer(DBTransaction dbTransaction) {
        APIClient.getClient().create(APIIterface.class).createTransact(dbTransaction).enqueue(new Callback<DBTransaction>() {
            @Override
            public void onResponse(Call<DBTransaction> call, Response<DBTransaction> response) {
                Toast.makeText(getApplication(), "Server: Insert Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<DBTransaction> call, Throwable t) {
                Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public String getSharedPreferenseKey() {
        return mSettings.getString(APP_PREFERENCES_NAME, "");
    }
}