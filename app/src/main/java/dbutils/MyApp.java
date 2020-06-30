package dbutils;

import android.app.Application;

import com.example.gaojichonci01_3.db.DaoMaster;
import com.example.gaojichonci01_3.db.DaoSession;

public class MyApp extends Application {

    public static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initDb();
    }

    private void initDb() {
        final DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "my01.db");
        final DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }
}
