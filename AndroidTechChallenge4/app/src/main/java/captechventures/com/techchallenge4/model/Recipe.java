package captechventures.com.techchallenge4.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import org.parceler.Parcel;

/**
 * Created by mluansing on 10/19/17.
 */

@Parcel
@Entity(tableName = "Recipes",
        foreignKeys = @ForeignKey(entity = Category.class,
            parentColumns = "_id",
            childColumns = "category"),
        indices = {@Index(value = "category")}
)
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    Integer id;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "minutes")
    Integer minutes;

    @ColumnInfo(name = "category")
    Integer category;

    @ColumnInfo(name = "ingredients")
    String ingredients;

    @ColumnInfo(name = "instructions")
    String instructions;

    public Recipe() {} // default constructor

    public Recipe(int id, String name, int minutes, int category, String ingredients, String instructions) {
        this.id = id;
        this.name = name;
        this.minutes = minutes;
        this.category = category;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", minutes=" + minutes +
                ", category=" + category +
                ", ingredients='" + ingredients + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipe recipe = (Recipe) o;

        if (id != recipe.id) return false;
        if (minutes != recipe.minutes) return false;
        if (category != recipe.category) return false;
        if (!name.equals(recipe.name)) return false;
        if (!ingredients.equals(recipe.ingredients)) return false;
        return instructions.equals(recipe.instructions);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + minutes;
        result = 31 * result + category;
        result = 31 * result + ingredients.hashCode();
        result = 31 * result + instructions.hashCode();
        return result;
    }
}
