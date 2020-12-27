package fit.bstu.project_wallet.Json;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import fit.bstu.project_wallet.units.Transaction;

public class JSONSerialize {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void Serialize(Context a, LiveData<List<Transaction>> list) {
        String path = a.getFilesDir()+"/HistoryPerMonth";
       if(!existBase(path)){CreateFile(path);}
        Gson gsonWorker = new Gson();
        FileWriter fw = null;
        try {
            fw = new FileWriter(path);
            gsonWorker.toJson(list, fw);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void CreateFile(String path) {
        File f = new File(path);
        try {
            FileWriter fw = new FileWriter(f, true);
        } catch (IOException e) {
        }
    }
    public static boolean existBase(String filePath) {
        boolean rc = false;
        File f = new File(filePath);
        rc = f.exists();
        return rc;
    }
}
