package com.example.iksandecade.jadwalsholat.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by iksandecade on 21/02/17.
 */

public class DaoHandler {

    public static DaoSession getInstance(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "Jadwal-Solat-deb", null);
        SQLiteDatabase db = helper.getWritableDatabase();

        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession mDaoSession = daoMaster.newSession();
        return mDaoSession;
    }
}
