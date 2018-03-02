package captechventures.com.techchallenge4.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import captechventures.com.techchallenge4.model.Recipe;

/**
 * Created by mluansing on 10/24/17.
 */

@Dao
public interface RecipeDao {

    // might not use in final application
    @Query("SELECT * FROM Recipes")
    List<Recipe> getAll();

    @Query("SELECT * FROM Recipes WHERE category LIKE :category")
    List<Recipe> getByCategory(int category);

    @Query("SELECT * FROM Recipes WHERE _id LIKE :id LIMIT 1")
    Recipe getById(int id);

    @Insert
    long add(Recipe recipe);

    @Update
    int edit(Recipe recipe);

    @Delete
    int delete(Recipe recipe);

}