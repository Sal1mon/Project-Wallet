package fit.bstu.project_wallet.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import fit.bstu.project_wallet.units.AnalistUnit;
import fit.bstu.project_wallet.units.Category;
import fit.bstu.project_wallet.units.Transaction;

public class DataBaseManager {
    DataBaseHelper help;
    SQLiteDatabase db;
    Context context;

    public DataBaseManager(Context ct) {
        this.context = ct;
    }

    public void deleteTrans(Transaction a) {
        String[] args = new String[]{String.valueOf(a.getIdTransaction())};
        db.delete("WalletTransctions", "idTransaction = ?", args);
    }

    public void connectionOpen() {
        help = new DataBaseHelper(context);
        db = help.getWritableDatabase();
    }

    public void createNewWallet(String value) {
        ContentValues values = new ContentValues();
        values.put("walletKey", value);
        values.put("cashValue", 0);
        db.insert("Wallet", null, values);
    }

    public void insertTransaction(ContentValues contentValues) {
        db.insert("WalletTransctions", null, contentValues);
    }

    public List<Category> getallCategory(int type) {
        String[] args = new String[]{Integer.toString(type)};
        Category prototype = new Category();
        List<Category> he = new ArrayList<>();
        Cursor mCursor = db.rawQuery("select * from Category where categoryType = ?1", args);
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            prototype.setId(mCursor.getInt(0));
            prototype.setCategoryType(mCursor.getInt(1));
            prototype.setCategoryName(mCursor.getString(2));
            he.add(prototype);
            prototype = new Category();
        }
        return he;
    }

    public List<AnalistUnit> getAnalistUnits(String key) {
        List<AnalistUnit> analistUnits = new ArrayList<>();
        String[] args = new String[]{key};
        Cursor mCursor = db.rawQuery("select categoryName, sum(trasactionValue) " +
                "from WalletTransctions transactions " +
                "join Category category " + "on category.categoryID = transactions.idCategory " +
                "where transactions.keyWallet = ?1 and category.categoryType = 2 " +
                "GROUP BY categoryName", args);
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            analistUnits.add(new AnalistUnit() {
                {
                    setValue(mCursor.getFloat(1));
                    setCategoryName(mCursor.getString(0));
                }
            });
        }
        return analistUnits;
    }

    public int getCashValue(String key) {
        String[] args = new String[]{key};
        int value = 0;
        Cursor mCursor = db.rawQuery("select * from Wallet where walletKey = ?1", args);
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            value = mCursor.getInt(1);
        }
        return value;
    }

    public LiveData<List<Transaction>> getAllTransac(String key) {
        List<Transaction> mAllContacts = new ArrayList<>();
        String[] args = new String[]{key};
        Transaction prototype;
        Cursor mCursor = db.rawQuery("select idTransaction, keyWallet,trasactionValue,dateTransaction, category.categoryName, idCategory from \n" +
                "WalletTransctions wallet\n" +
                "Join Category category on wallet.idCategory = category.categoryID\n" +
                "where keyWallet = ?1", args);
        for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
            prototype = new Transaction();
            prototype.setIdTransaction(mCursor.getString(0));
            prototype.setKeyWallet(mCursor.getString(1));
            prototype.setTrasactionValue(mCursor.getString(2));
            prototype.setDateTransaction(mCursor.getString(3));
            prototype.setNameCategory(mCursor.getString(4));
            prototype.setIdCategory(mCursor.getInt(5));
            mAllContacts.add(prototype);
        }
        return new MutableLiveData<>(mAllContacts);
    }
}
