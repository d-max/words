package dmax.words.persist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Iterator;

import dmax.words.domain.Persistable;

/**
 * Created by Maxim Dybarsky | maxim.dybarskyy@gmail.com
 * on 12.12.14 at 17:46
 */
public class DataBaseManager {

    private SQLiteDatabase database;
    private DataBaseHelper helper;

    public DataBaseManager(Context context) {
        helper = new DataBaseHelper(context);
    }

    public void open() {
        database = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }


    public <T extends Persistable> T save(Dao<T> dao) {
        if (database == null || !database.isOpen()) throw new IllegalStateException("DBManager not initialized");
        T t = dao.save(database);
        dao.reset();
        return t;
    }

    public <T extends Persistable> void delete(Dao<T> dao) {
        if (database == null || !database.isOpen()) throw new IllegalStateException("DBManager not initialized");
        dao.delete(database);
        dao.reset();
    }

    public <T extends Persistable> T retrieve(Dao<T> dao) {
        if (database == null || !database.isOpen()) throw new IllegalStateException("DBManager not initialized");
        T t = dao.retrieve(database);
        dao.reset();
        return t;
    }

    public <T extends Persistable> Iterator<T> retrieveIterator(Dao<T> dao) {
        if (database == null || !database.isOpen()) throw new IllegalStateException("DBManager not initialized");
        Iterator<T> it = dao.retrieveIterator(database);
        dao.reset();
        return it;
    }
}
