package captechventures.com.techchallenge4.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import captechventures.com.techchallenge4.database.dao.CategoryDao;
import captechventures.com.techchallenge4.database.dao.RecipeDao;
import captechventures.com.techchallenge4.database.sqlAsset.AssetSQLiteOpenHelperFactory;
import captechventures.com.techchallenge4.model.Category;
import captechventures.com.techchallenge4.model.Recipe;

/**
 * Created by mluansing on 10/24/17.
 */

@Database(entities = {Recipe.class, Category.class}, version = 1)
public abstract class CookbookDatabase extends RoomDatabase {
    // constants
    private static final String DATABASE_NAME = "assignment4.db";

    // instances
    private static CookbookDatabase INSTANCE;
    private static final Object sLock = new Object();

    // methods
    public abstract RecipeDao recipeDao();
    public abstract CategoryDao categoryDao();

    public static CookbookDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        CookbookDatabase.class, DATABASE_NAME)
                        .openHelperFactory(new AssetSQLiteOpenHelperFactory())
                        .allowMainThreadQueries()
                        .build();
            }
            return INSTANCE;
        }
    }

}
