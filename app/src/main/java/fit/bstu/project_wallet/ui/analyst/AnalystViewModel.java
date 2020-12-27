package fit.bstu.project_wallet.ui.analyst;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import fit.bstu.project_wallet.DataBase.DataBaseManager;
import fit.bstu.project_wallet.units.AnalistUnit;
import fit.bstu.project_wallet.units.Transaction;

public class AnalystViewModel extends AndroidViewModel {


    private List<Float> transactValue = new ArrayList<>() ;
    private List<String> categoryName = new ArrayList<>();
    
    private MutableLiveData<String> mText;
    private SharedPreferences mSettings;
    private LiveData<List<Transaction>> aAllTransaction;
    private DataBaseManager dataBaseManager;
    private static final String APP_PREFERENCES = "WALLET_KEY";
    private static final String APP_PREFERENCES_NAME = "KEY";

    public AnalystViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mSettings = getApplication().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        dataBaseManager = new DataBaseManager(getApplication());
        dataBaseManager.connectionOpen();
    }
    public void getAnalyst(){
        for (AnalistUnit a :dataBaseManager.getAnalistUnits(mSettings.getString(APP_PREFERENCES_NAME, ""))) {
            transactValue.add(a.getValue());
            categoryName.add(a.getCategoryName());
        }
    }
    public List<Float> getTransactValue() {
        return transactValue;
    }
    public List<String> getCategoryName() {
        return categoryName;
    }

}