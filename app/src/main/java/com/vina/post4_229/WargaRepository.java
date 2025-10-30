package com.vina.post4_229;

import android.app.Application;
import java.util.List;

public class WargaRepository {

    private WargaDao wargaDao;

    public WargaRepository(Application application) {
        AppDatabase database = AppDatabase.getDatabase(application);
        wargaDao = database.wargaDao();
    }

    public long insert(Warga warga) {
        return wargaDao.insertWarga(warga);
    }

    public List<Warga> getAllWarga() {
        return wargaDao.getAllWarga();
    }

    public void deleteAll() {
        wargaDao.deleteAllWarga();
    }

    public int getCount() {
        return wargaDao.getWargaCount();
    }
}