package captechventures.com.techchallenge4.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import captechventures.com.techchallenge4.model.Category;

/**
 * Created by mluansing on 10/24/17.
 */

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM Categories")
    public List<Category> getAll();

    @Query("SELECT * FROM Categories WHERE _id LIKE :id LIMIT 1")
    public Category getCategory(int id);

//    @Insert
//    public void add(Recipe recipe);
//
//    @Update
//    public void edit(Recipe recipe);
//
//    @Delete
//    public void delete(Recipe recipe);

}