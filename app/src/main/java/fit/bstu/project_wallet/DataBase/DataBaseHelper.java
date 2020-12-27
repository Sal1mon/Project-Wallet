package fit.bstu.project_wallet.DataBase;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private final String DATABASE_NAME = "WalletOrganizer";
    public DataBaseHelper(@Nullable Context context) {
        super(context, "WalletOrganizer", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table Wallet(walletKey text primary key, cashValue integer)");
        db.execSQL("Create table WalletTransctions(idTransaction text primary key, keyWallet text, idCategory integer," +
                " trasactionValue integer, dateTransaction text, foreign key(keyWallet) references Wallet(walletKey), foreign key(idCategory) references Category(categoryID))");
        db.execSQL("Create table Category(categoryID integer primary key, categoryType integer, categoryName text)");
        fillWithMockData(db);
         createTrigger(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO Upgrade DB
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys = ON");
        db.setForeignKeyConstraintsEnabled(true);
        super.onConfigure(db);
    }

    private void createTrigger(SQLiteDatabase db){
        db.execSQL("CREATE TRIGGER IF NOT EXISTS UPDATECASH" +
                " AFTER INSERT ON WalletTransctions" +
                " BEGIN " +
                " UPDATE Wallet SET cashValue = cashValue + new.trasactionValue WHERE Wallet.walletKey = new.keyWallet;" +
                " END");
        db.execSQL("CREATE TRIGGER IF NOT EXISTS UNUPDATECASH" +
                " AFTER DELETE ON WalletTransctions" +
                " BEGIN " +
                " UPDATE Wallet SET cashValue = cashValue - old.trasactionValue WHERE Wallet.walletKey = old.keyWallet;" +
                " END");
    }

    private void fillWithMockData(SQLiteDatabase db){
        db.beginTransaction();
        db.execSQL("INSERT INTO Category (categoryID,categoryType,categoryName)VALUES(1,1,'Зарплата')");
        db.execSQL("INSERT INTO Category (categoryID,categoryType,categoryName)VALUES(2,1,'Вклад')");
        db.execSQL("INSERT INTO Category (categoryID,categoryType,categoryName)VALUES(3,1,'Пассивный доход')");
        db.execSQL("INSERT INTO Category (categoryID,categoryType,categoryName)VALUES(4,1,'Подарок')");
        db.execSQL("INSERT INTO Category (categoryID,categoryType,categoryName)VALUES(5,2,'Еда и Напитки')");
        db.execSQL("INSERT INTO Category (categoryID,categoryType,categoryName)VALUES(6,2,'Одежда')");
        db.execSQL("INSERT INTO Category (categoryID,categoryType,categoryName)VALUES(7,2,'Дом')");
        db.execSQL("INSERT INTO Category (categoryID,categoryType,categoryName)VALUES(8,2,'Дети')");
        db.execSQL("INSERT INTO Category (categoryID,categoryType,categoryName)VALUES(9,2,'Ремонт')");
        db.execSQL("INSERT INTO Category (categoryID,categoryType,categoryName)VALUES(10,2,'Развлечения')");
        db.execSQL("INSERT INTO Category (categoryID,categoryType,categoryName)VALUES(11,2,'Авто')");
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
