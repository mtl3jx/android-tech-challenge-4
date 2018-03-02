package captechventures.com.techchallenge4.cookbook.recipes;

import android.support.v7.util.DiffUtil;

import java.util.List;

import captechventures.com.techchallenge4.model.Recipe;

/**
 * Created by mluansing on 11/2/17.
 */

class RecipeDiffCallback extends DiffUtil.Callback {

    public List<Recipe> oldRecipes, newRecipes;

    public RecipeDiffCallback(List<Recipe> newRecipes, List<Recipe> oldRecipes) {
        this.newRecipes = newRecipes;
        this.oldRecipes = oldRecipes;
    }

    @Override
    public int getOldListSize() {
        return oldRecipes.size();
    }

    @Override
    public int getNewListSize() {
        return newRecipes.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRecipes.get(oldItemPosition).getId() == newRecipes.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRecipes.get(oldItemPosition).equals(newRecipes.get(newItemPosition));
    }
}
