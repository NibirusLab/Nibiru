package nibiruslab.org.nibiru.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by David on 14/01/2015.
 */
public class Database extends SQLiteOpenHelper {
    // Nombre de la base de datos
    private static final String BASEDATOS_NOMBRE = "nibirus.db";
    // Versión de la base de datos
    private static final int BASEDATOS_VERSION = 1;

    private static String[] FROM_CURSOR = {"id", "hashtag"};
    private static String ORDER_BY = "id" + " DESC";

    public Database(Context contexto) {
        super(contexto, BASEDATOS_NOMBRE, null, BASEDATOS_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE hashtags (id INTEGER PRIMARY KEY AUTOINCREMENT, hashtag VARCHAR(150));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Sería más conveniente lanzar un ALTER TABLE en vez de eliminar la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS hashtags");
        onCreate(db);
    }

    public void newHashtag(String hashtag){
        SQLiteDatabase db = this.getWritableDatabase();
        hashtag.replace("#", "");

        if (existsHashtag(hashtag))
            return;

        ContentValues values = new ContentValues();
        values.put("hashtag", hashtag);

        db.insertOrThrow("hashtags", null, values);
    }

    public Cursor getAll(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("hashtags", FROM_CURSOR, null, null, null, null, ORDER_BY);

        return cursor;
    }

    public boolean existsHashtag(String hashtag){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        String sql = "SELECT id FROM hashtags WHERE hashtag = '"+ hashtag+"'";
        cursor = db.rawQuery(sql, null);

        if (cursor.getCount() == 0){
            return false;
        }else {
            return true;
        }
    }
}
