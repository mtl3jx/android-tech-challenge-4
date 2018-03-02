package captechventures.com.techchallenge4.cookbook.categories;

import android.support.v7.util.DiffUtil;

import java.util.List;

import captechventures.com.techchallenge4.model.Category;

/**
 * Created by mluansing on 10/31/17.
 */

class CategoryDiffCallback extends DiffUtil.Callback {

    public List<Category> oldCategories, newCategories;

    public CategoryDiffCallback(List<Category> newCategories, List<Category> oldCategories) {
        this.newCategories = newCategories;
        this.oldCategories = oldCategories;
    }

    @Override
    public int getOldListSize() {
        return oldCategories.size();
    }

    @Override
    public int getNewListSize() {
        return newCategories.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldCategories.get(oldItemPosition).getId() == newCategories.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldCategories.get(oldItemPosition).equals(newCategories.get(newItemPosition));
    }

}
